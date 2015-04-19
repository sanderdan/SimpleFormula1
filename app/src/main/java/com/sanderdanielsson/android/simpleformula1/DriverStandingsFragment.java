package com.sanderdanielsson.android.simpleformula1;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
 * Created by Sander on 2015-04-06.
 */
public class DriverStandingsFragment extends android.support.v4.app.Fragment {

    ListView mListView = null;

//    private CustomAdapter mDriverStandingsAdapter;

    public DriverStandingsFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<Driver> driverStandings = new ArrayList<>();
        try {
            driverStandings = new FetchStandings().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Now we need to dispay that in a listview
        DriverStandingsAdapter adapter = new DriverStandingsAdapter(
                        getActivity(),
                        R.layout.stanidngs_row,
                        driverStandings
                );

        View driverStandingsView = inflater.inflate(R.layout.standings_list, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) driverStandingsView.findViewById(R.id.listView_driverStandings);
        listView.setAdapter(adapter);

        return driverStandingsView;
    }

    public class FetchStandings extends AsyncTask<Void, Void, ArrayList<Driver>> {

        private final String LOG_TAG = FetchStandings.class.getSimpleName();

        private ArrayList<Driver> getStandingsDataFromJson(String standingsStr)
                throws JSONException {

            final String STANDINGS_FIRST_NAME = "givenName";
            final String STANDINGS_LAST_NAME = "familyName";
            final String STANDINGS_NUMBER = "permanentNumber";
            final String STANDINGS_WINS = "wins";
            final String STANDINGS_POINTS = "points";
            final String STANDINGS_NATIONALITY = "nationality";
            final String STANDINGS_DRIVER_CODE = "code";
            final String STANDINGS_TEAM_NAME = "name";

            JSONObject mrData = new JSONObject(standingsStr);

            JSONArray driverStandings = mrData.getJSONObject("MRData")
                    .getJSONObject("StandingsTable")
                    .getJSONArray("StandingsLists")
                    .getJSONObject(0)
                    .getJSONArray("DriverStandings");

            ArrayList<Driver> drivers = new ArrayList<>();
            for (int i = 0; i < driverStandings.length(); i++) {
             // Get the JSON object representing the Driver
                JSONObject jsonDriver = driverStandings.getJSONObject(i).getJSONObject("Driver");
                JSONObject jsonTeam = driverStandings.getJSONObject(i).getJSONArray("Constructors").getJSONObject(0);
//
                Driver driver = new Driver(
                        jsonDriver.getString(STANDINGS_FIRST_NAME),
                        jsonDriver.getString(STANDINGS_LAST_NAME),
                        jsonDriver.getString(STANDINGS_NUMBER),
                        jsonDriver.getString(STANDINGS_NATIONALITY),
                        driverStandings.getJSONObject(i).getString(STANDINGS_WINS),
                        driverStandings.getJSONObject(i).getString(STANDINGS_POINTS),
                        jsonDriver.getString(STANDINGS_DRIVER_CODE),
                        jsonTeam.getString(STANDINGS_TEAM_NAME));

                drivers.add(driver);
            }



            return drivers;
        }

        @Override
        protected ArrayList<Driver> doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String standingsJsonStr = null;
            ArrayList<Driver> standings = null;


            try {
                // Construct the URL for the Standings query
                final String STANDINGS_URL = "http://ergast.com/api/f1/current/driverStandings.json";

                URL url = new URL(STANDINGS_URL);

                Log.v(LOG_TAG, "URL: " + url.toString());

                // Create the request to URL, and open the connection
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

                standingsJsonStr = buffer.toString();
//                Log.v("Json data: ", standingsJsonStr);

                try {
                    standings = getStandingsDataFromJson(standingsJsonStr);
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

            return standings;
        }
    }
}
