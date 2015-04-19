package com.sanderdanielsson.android.simpleformula1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sander on 2015-04-10.
 */
public class ScheduleFragment extends android.support.v4.app.Fragment {

    ListView mListView = null;
    TextView mTextView = null;

//    private CustomAdapter mDriverStandingsAdapter;

    public ScheduleFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<Race> schedule = new ArrayList<>();
        try {
            schedule = new FetchSchedule().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Now we need to dispay that in a listview
        ScheduleAdapter adapter = new ScheduleAdapter(
                getActivity(),
                R.layout.schedule_row,
                schedule
        );

        View scheduleView = inflater.inflate(R.layout.schedule_list, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) scheduleView.findViewById(R.id.listView_schedule);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Race r =(Race) parent.getAdapter().getItem(i);
                Intent openUrl = new Intent();
                openUrl.setAction(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(r.getWikiUrl()));
//                openUrl.setPackage("org.wikipedia");
                startActivity(openUrl);
            }
        });


        return scheduleView;
    }

    public class FetchSchedule extends AsyncTask<Void, Void, ArrayList<Race>> {

        private final String LOG_TAG = FetchSchedule.class.getSimpleName();

        private ArrayList<Race> getScheduleDataFromJson(String standingsStr)
                throws JSONException {

            final String SCHEDULE_RACE = "raceName";
            final String SCHEDULE_CIRCUIT_NAME = "circuitName";
            final String SCHEDULE_CIRCUIT_LOCATION = "locality";
            final String SCHEDULE_CIRCUIT_COUNTRY = "country";
            final String SCHEDULE_DATE = "date";
            final String SCHEDULE_TIME = "time";

            JSONObject mrData = new JSONObject(standingsStr);

            JSONArray raceSchedule = mrData.getJSONObject("MRData")
                    .getJSONObject("RaceTable")
                    .getJSONArray("Races");

            ArrayList<Race> races = new ArrayList<>();
            for (int i = 0; i < raceSchedule.length(); i++) {
                // Get the JSON object representing the Race

//
                Race race = new Race(
                        raceSchedule.getJSONObject(i).getString(SCHEDULE_RACE),
                        raceSchedule.getJSONObject(i).getJSONObject("Circuit").getString(SCHEDULE_CIRCUIT_NAME),
                        raceSchedule.getJSONObject(i).getJSONObject("Circuit").getJSONObject("Location").getString(SCHEDULE_CIRCUIT_LOCATION),
                        raceSchedule.getJSONObject(i).getJSONObject("Circuit").getJSONObject("Location").getString(SCHEDULE_CIRCUIT_COUNTRY),
                        raceSchedule.getJSONObject(i).getString(SCHEDULE_DATE),
                        raceSchedule.getJSONObject(i).getString(SCHEDULE_TIME),
                        raceSchedule.getJSONObject(i).getString("url"));

                races.add(race);
            }
            return races;
        }

        @Override
        protected ArrayList<Race> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String scheduleJsonStr = null;
            ArrayList<Race> schedule = null;


            try {
                // Construct the URL for the Standings query
                final String STANDINGS_URL = "http://ergast.com/api/f1/current.json";
                URL url = new URL(STANDINGS_URL);

                Log.v(LOG_TAG, "Fetching standings from" + url.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                scheduleJsonStr = buffer.toString();
//                Log.v("Json data: ", standingsJsonStr);

                try {
                    schedule = getScheduleDataFromJson(scheduleJsonStr);
                    System.out.println("made it");
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e);
                }



            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the standings data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            return schedule;
        }
    }
}

