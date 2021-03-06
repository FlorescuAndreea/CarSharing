package com.symbidrive.timteam.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;


public class PoolDetailsDriver extends Activity {
    protected static String type;
    private String poolID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_details);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        poolID = intent.getStringExtra("objectID");

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewPool(poolID, 0);


    }

    // user's view pool (type: 0 - driver, 1 - passenger)
    public void viewPool(String id, final Integer userType) {
        HashMap<String, Object> params = new HashMap <String, Object> ();
        params.put("id", id);

        ParseCloud.callFunctionInBackground("viewPool", params, new FunctionCallback<ArrayList<ParseObject>>() {
            public void done(ArrayList<ParseObject> res, ParseException e) {
                if (e == null) {
                    final String source_lat = String.valueOf(res.get(0).getParseGeoPoint("source").getLatitude());
                    final String source_long = String.valueOf(res.get(0).getParseGeoPoint("source").getLongitude());
                    final String dest_lat = String.valueOf(res.get(0).getParseGeoPoint("destination").getLatitude());
                    final String dest_long = String.valueOf(res.get(0).getParseGeoPoint("destination").getLongitude());

                    HashMap<String, Object> tmp = new HashMap<String, Object>();
                    final String weekly = res.get(0).get("weekly").toString();

                    if (userType == 0) {
                        for (int i = 0; i < res.get(0).getJSONArray("passengers").length(); i++) {
                            String usn = null;

                            try {
                                usn = res.get(0).getJSONArray("passengers").get(i).toString();
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }

                            tmp.put("username", usn);
                            final String finalUsn = usn;

                            ParseCloud.callFunctionInBackground("getPhone", tmp, new FunctionCallback<String>() {
                                public void done(String phone, ParseException e) {
                                    if (e == null) {
                                        addPassengerToPool(finalUsn, phone);
                                    } else {
                                        // 'res' are valoarea: User not found!
                                    }
                                }
                            });
                        }

                        addDetailsToPool(Double.parseDouble(source_lat), Double.parseDouble(source_long),
                                Double.parseDouble(dest_lat), Double.parseDouble(dest_long), Boolean.parseBoolean(weekly));
                    }
                } else {
                    // 'res' are valoarea: Pool not found!
                }
            }
        });
    }

    public void addPassengerToPool(String usn, String phone) {
        LinearLayout listLayout = (LinearLayout)findViewById(R.id.passenger_list);

        LinearLayout detailsLayout = new LinearLayout(listLayout.getContext());

        TextView nameView = new TextView(detailsLayout.getContext());
        nameView.setText(usn);
        nameView.setTextSize(30);
        nameView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));

        TextView telephoneView = new TextView(detailsLayout.getContext());
        LinearLayout.LayoutParams textViewLayout = new  LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewLayout.setMargins(20, 0, 0, 0);
        telephoneView.setLayoutParams(textViewLayout);
        telephoneView.setText(phone);
        telephoneView.setTextSize(20);
        Linkify.addLinks(telephoneView, Linkify.ALL);

        detailsLayout.addView(nameView);
        detailsLayout.addView(telephoneView);

        listLayout.addView(detailsLayout);
    }

    public void addDetailsToPool(Double source_lat, Double source_long, Double dest_lat, Double dest_long,
                                 Boolean weekly) {

        LatLng source = new LatLng(source_lat, source_long);
        LatLng destination = new LatLng(dest_lat, dest_long);
        setMapLocations(source, destination);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        if (id == R.id.action_my_profile) {
//            Intent intent = new Intent(this, MyProfile.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

     void setMapLocations(LatLng source, LatLng dest) {
        // Map Controller
         GoogleMap map;
         map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_view_pool)).getMap();


         map.animateCamera(CameraUpdateFactory.newLatLngZoom(source, 15));

         MapController mapController = new MapController(map);
         mapController.setOrigin(source);
         mapController.setDestination(dest);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            rootView = inflater.inflate(R.layout.fragment_pool_details_driver, container, false);

            return rootView;
        }
    }
}
