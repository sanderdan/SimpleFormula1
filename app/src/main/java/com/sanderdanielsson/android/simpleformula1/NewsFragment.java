package com.sanderdanielsson.android.simpleformula1;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Sander on 2015-04-10.
 */

public class NewsFragment extends android.support.v4.app.Fragment {

    private TextView mRssFeed;
    private ProgressBar progressBar;
    ArrayList<News> news;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            news = new FetchNews().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }

        //Now we need to dispay that in a listview
        NewsAdapter adapter = new NewsAdapter(
                getActivity(),
                R.layout.news_row,
                news
        );

        View newsView = inflater.inflate(R.layout.news_feed, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) newsView.findViewById(R.id.listView_news);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                News n = (News) parent.getAdapter().getItem(i);
                Intent openUrl = new Intent();
                openUrl.setAction(Intent.ACTION_VIEW);
                openUrl.setData(Uri.parse(n.getLink()));
//                openUrl.setPackage("org.wikipedia");
                startActivity(openUrl);
            }
        });

        return newsView;

    }


    public class FetchNews extends AsyncTask<Void, Void, ArrayList<News>> {
        private final String LOG_TAG = FetchNews.class.getSimpleName();

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            super.onPostExecute(news);
            progressDialog.dismiss();
        }


        @Override
        protected ArrayList<News> doInBackground(Void... params) {

            String myurl = "http://au.eurosport.com/formula-1/rss.xml";

            InputStream is = null;

            ArrayList<News> newsList = new ArrayList<>();

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int statusCode = conn.getResponseCode();
                Log.d("BULLE", "The response is: " + statusCode);
                is = conn.getInputStream();

                JSONObject jsonObject;

                if (statusCode == 200 && is != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = bufferedReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }

                    jsonObject = XML.toJSONObject(responseStrBuilder.toString());
                    jsonObject = jsonObject.getJSONObject("rss").getJSONObject("channel");
                    JSONArray jsonArray = jsonObject.getJSONArray("item");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        News news = new News();
                        news.setTitle(jsonArray.getJSONObject(i).getString("title"));
                        news.setDescription(jsonArray.getJSONObject(i).getString("description"));
                        news.setLink(jsonArray.getJSONObject(i).getString("link"));
                        news.setPubDate(jsonArray.getJSONObject(i).getString("pubDate"));
                        news.setImgUrl(jsonArray.getJSONObject(i).getJSONObject("image").getString("url"));

                        newsList.add(news);

                    }
                }


                return newsList;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch (MalformedURLException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }



    }

}


