package com.carsharing.antisergiu.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.AndroidCharacter;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.carsharing.antisergiu.controller.RoutesAdapter;
import com.carsharing.antisergiu.model.MatchingPoolItem;
import com.google.android.gms.maps.model.LatLng;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class MatchingPoolsActivity extends Activity implements DialogInterface.OnDismissListener {
    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;
    public static boolean mIsRegistered = false;

    private static MatchingPoolItem matchingItem = new MatchingPoolItem();
    private static int counter = 0;
    private LatLng origin, destination;
    private String day, hour;
    private long time = 0;

    public static boolean getRegistrationStatus() {
        return mIsRegistered;
    }

    public static void setmIsRegistered(boolean value) {
        mIsRegistered = value;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("registered", true);
        editor.commit();
    }


    public void joinPool() {
        // TODO add login logic
        // TODO add user to pool in db

        joinPool(matchingItem.getObjectID(), prefs.getString("username", ""));
    }

    // join pool
    public void joinPool(String id, String passenger) {
        HashMap <String, Object> params = new HashMap <String, Object> ();
        params.put("id", id);
        params.put("passenger", passenger);

        ParseCloud.callFunctionInBackground("joinPool", params, new FunctionCallback<String>() {
            public void done(String res, ParseException e) {
                if (e == null) {
                    // 'res' are valoarea: Joined successfully!
                }
                else {
                    // 'res' are valoarea: Passenger already joined!
                }
            }
        });
    }

    public void onDismiss(DialogInterface dialogInterface) {
        joinPool();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_pools);

        prefs = getSharedPreferences(sharedPrefsName, MODE_MULTI_PROCESS);
        mIsRegistered = prefs.getBoolean("registered", false);



        Log.d("MAPS", "creando MatchingPoolsActivity");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = getIntent();
        origin = new LatLng(intent.getDoubleExtra("SOURCE_LAT", 0), intent.getDoubleExtra("SOURCE_LONG", 0));
        destination = new LatLng(intent.getDoubleExtra("DEST_LAT", 0), intent.getDoubleExtra("DEST_LONG", 0));

        day = intent.getStringExtra("DATE");
        hour = intent.getStringExtra("HOUR");

        day += (" " + hour);
        java.util.Date d = null;

        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm");
            isoFormat.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
            d = isoFormat.parse(day);
            time = d.getTime();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        PlaceholderFragment.matchingPools(new ParseGeoPoint(origin.latitude, origin.longitude),
                new ParseGeoPoint(destination.latitude, destination.longitude), new Date(time));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_my_profile) {
            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {

        }
        static RoutesAdapter adapter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_matching_pools, container, false);

            adapter = new RoutesAdapter();
            ListView listView = (ListView) rootView.findViewById(R.id.matching_pools_listview);
            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showRoute(view);
//
//                }
//            });

          return rootView;
        }

        @Override
        public void onStop() {
            super.onStop();
            adapter = new RoutesAdapter();
        }


        // user's matching pools
        public static void matchingPools(ParseGeoPoint source, ParseGeoPoint dest, Date date) {
            HashMap<String, Object> params = new HashMap <String, Object> ();
            params.put("source", source);
            params.put("destination", dest);
            params.put("date", date);

            ParseCloud.callFunctionInBackground("matchingPools", params, new FunctionCallback<ArrayList<ParseObject>>() {
                public void done(ArrayList<ParseObject> res, ParseException e) {
                    if (e == null) {
                        String usn = null;

                        for (int i = 0; i < res.size(); i++) {
                            usn = res.get(i).get("driver").toString();

                            showInMatchingPoolsView(res.get(i).getObjectId(), usn);
                        }
                    } else {
                        // 'res' are valoarea: There are no pools!
                    }
                }
            });
        }

        public static void showInMatchingPoolsView(String poolId, String driver) {

            matchingItem.setObjectID(poolId);
            matchingItem.setDriver(driver);
            matchingItem.setId(counter++);
            getRating(driver);
        }

        // get user's rating
        public static void getRating(String username) {
            HashMap <String, Object> params = new HashMap <String, Object> ();
            params.put("username", username);

            ParseCloud.callFunctionInBackground("getRating", params, new FunctionCallback<String>() {
                public void done(String res, ParseException e) {
                    if (e == null) {
                        showRatingInMatchingPoolsView(res);

                    }
                    else {
                        // 'res' are valoarea: Driver not found!
                    }
                }
            });
        }

        public static void showRatingInMatchingPoolsView(String rating) {
            matchingItem.setRating(rating);
            adapter.addItem(matchingItem);
        }

    }

    public void showLoginDialog(View view) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(this.getFragmentManager(), "fragment_login");
    }
}
