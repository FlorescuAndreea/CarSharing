package com.carsharing.antisergiu.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carsharing.antisergiu.controller.DownloadTask;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by demouser on 8/1/14.
 */
public class ShowRouteActivity extends Activity  implements DialogInterface.OnDismissListener {
    protected static LatLng locA;
    protected static LatLng locB;
    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;
    public static boolean mIsRegistered = false;

    protected static DownloadTask downloadTask = new DownloadTask();

    public ShowRouteActivity() {

    }

    public static Context getContext() {
        return CreatePoolActivity.getContext();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        // TODO get info frorm db or previous intent
        Intent intent = getIntent();

        locA = new LatLng(intent.getDoubleExtra("SOURCE_LAT", 0),intent.getDoubleExtra("SOURCE_LONG", 0));
        locB = new LatLng(intent.getDoubleExtra("DEST_LAT", 0), intent.getDoubleExtra("DEST_LONG", 0));

        prefs = getSharedPreferences(sharedPrefsName, MODE_MULTI_PROCESS);
        mIsRegistered = prefs.getBoolean("registered", false);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        joinPool();
    }

    public void joinPool() {

    }

    public void showLoginDialog(View view) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(this.getFragmentManager(), "fragment_login");
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        private String getDirectionsUrl(LatLng origin, LatLng dest) {
            // Origin of route
            String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

            // Destination of route
            String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

            // Sensor enabled
            String sensor = "sensor=false";

            // Building the parameters to the web service
            String parameters = str_origin + "&" + str_dest + "&" + sensor;

            // Output format
            String output = "json";

            // Building the url to the web service
            String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

            Log.d("maps",url);

            return url;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_show_route, container, false);

            GoogleMap map;
            map = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            LatLng loc=new LatLng(44.435791, 26.047450);

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(locA, 8));

            map.addMarker(new MarkerOptions().position(locA).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).draggable(true));

            map.addMarker(new MarkerOptions().position(locB).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).draggable(true));


            Log.d("MAPS", "enter GetDirections");
            String url = getDirectionsUrl(locA, locB);
            Log.d("MAPS", "enter DownloadTask");

            Log.d("MAPS", "enter DownloadTaskExecute");

            // create context for download task
            downloadTask.setMap(map);
            downloadTask.setDistanceView(getActivity().findViewById(R.id.show_route_distance));
            downloadTask.setTimeView(getActivity().findViewById(R.id.show_route_duration));

            // Start downloading json data from Google Directions API
            downloadTask.execute(url);

            Log.d("MAPS", "beforeReturn");

            return rootView;
        }

    }
}


