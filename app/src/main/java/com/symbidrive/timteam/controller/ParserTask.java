package com.symbidrive.timteam.controller;

/**
 * Created by Andreea on 12/2/2014.
 */

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.symbidrive.timteam.main.DirectionsJSONParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class to parse the Google Places in JSON format
 */
public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    private PolylineOptions lineOptions = null;
    private String distance = "";
    private String duration = "";
    private GoogleMap map;
    private View tvDistance;
    private View tvDuration;

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setViews(View tvDistance, View tvDuration) {
        this.tvDistance = tvDistance;
        this.tvDuration = tvDuration;
    }
    // Parsing the data in non-ui thread
    @Override
    protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;

        try {
            jObject = new JSONObject(jsonData[0]);
            DirectionsJSONParser parser = new DirectionsJSONParser();

            // Starts parsing data
            routes = parser.parse(jObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Executes in UI thread, after the parsing process
    @Override
    protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        ArrayList<LatLng> points = null;

        MarkerOptions markerOptions = new MarkerOptions();

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                if (j == 0) { // Get distance from the list
                    distance = point.get("distance");
                    continue;
                } else if (j == 1) { // Get duration from the list
                    duration = point.get("duration");
                    continue;
                }
                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);
                points.add(position);
            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.RED);
            Log.v("GMAPS", "------------------------" + duration + "   " + distance);
            ((EditText)tvDuration).setText(duration);
            ((EditText)tvDistance).setText(distance);
            map.addPolyline(lineOptions);
        }

    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }
}