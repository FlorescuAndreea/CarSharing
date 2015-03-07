package com.symbidrive.timteam.controller;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andreea on 12/2/2014.
 */
// Fetches data from url passed
public class DownloadTask extends AsyncTask<String, Void, String> {
    // Downloading data in non-ui thread

    private GoogleMap map;
    private View timeView;
    private View distanceView;
    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @Override
    public String doInBackground(String... url) {

        // For storing data from web service
        String data = "";

        try {
            // Fetching the data from web service
            data = downloadUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }
    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setTimeView(View view) {
        timeView = view;
    }

    public void setDistanceView(View view) {
        distanceView = view;
    }
    // Executes in UI thread, after the execution of
    // doInBackground()
    @Override
    public void onPostExecute(String result) {
        super.onPostExecute(result);

        ParserTask parserTask = new ParserTask();

        // Invokes the thread for parsing the JSON data
        parserTask.setMap(this.map);
        parserTask.setViews(distanceView, timeView);

        parserTask.execute(result);
        Log.v("DDD_MAPS","ceeeeva" + parserTask.getDistance() + "       " + parserTask.getDuration());

//        ((EditText)timeView).setText(parserTask.getDuration());
//        ((EditText)distanceView).setText(parserTask.getDistance());

    }
}
