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

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MatchingPoolsActivity extends Activity implements DialogInterface.OnDismissListener {
    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;
    public static boolean mIsRegistered = false;

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



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_matching_pools, container, false);

            RoutesAdapter adapter = new RoutesAdapter();
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


//        public void showRoute(View view) {
//            Intent createShowRootIntent = new Intent(this.getActivity(), ShowRouteActivity.class);
//
//            createShowRootIntent.putExtra("SOURCE_LAT", 50);
//            createShowRootIntent.putExtra("SOURCE_LONG", 51);
//            createShowRootIntent.putExtra("DEST_LAT", 50.05);
//            createShowRootIntent.putExtra("DEST_LONG", 51.07);
//
//            startActivity(createShowRootIntent);
//        }

        public void showLoginDialog(View view) {
            LoginDialog loginDialog = new LoginDialog();
            loginDialog.show(this.getFragmentManager(), "fragment_login");
        }

        private class RoutesAdapter extends BaseAdapter {

            private ArrayList<Route> routes;

            public RoutesAdapter() {
                routes = new ArrayList<Route>();
                Time t = new Time();
                t.setToNow();
                    routes.add(new Route("Farcasanu",3,10, 1, 3, 3, 12, 23, new LatLng(1,1), new LatLng(2,2)));
                t.set(0,1,2,3,4,5);
                routes.add(new Route("Antoche",2,9, 3, 2, 1, 23, 12, new LatLng(1,1), new LatLng(2,2)));
                routes.add(new Route("Grecule",2,5, 3, 2, 1, 23, 12, new LatLng(1,1), new LatLng(2,2)));
            }

            public int getCount() {
                return routes.size();
            }

            public Route getItem(int position) {
                return routes.get(position);
            }

            public long getItemId(int position) {
                return routes.get(position).getId();
            }

            public View getView(int position, View convertView, ViewGroup parent) {

                LinearLayout layout;
                Route currRoute = routes.get(position);

                if (convertView == null) {
                    layout = new LinearLayout(parent.getContext());
                    layout.setOrientation(LinearLayout.VERTICAL);

                    TextView timeView = new TextView(parent.getContext());
                    timeView.setText(String.format("%02d", currRoute.getDepatureDate()) + "/" + String.format("%02d", currRoute.getDepatureMonth()) + "/" + currRoute.getDepatureYear() + " " + String.format("%02d", currRoute.getDepatureHour()) + ":" + String.format("%02d", currRoute.getDepatureMinute()));
                    timeView.setTextSize(18);
                    timeView.setTypeface(null, Typeface.BOLD);
                    timeView.setTextColor(Color.DKGRAY);
                    layout.addView(timeView);

                    TextView driverView = new TextView(parent.getContext());
                    driverView.setText("Driver: " + currRoute.getDriver());
                    driverView.setTextSize(18);
                    driverView.setTextColor(Color.DKGRAY);
                    layout.addView(driverView);

                    TextView ratingView = new TextView(parent.getContext());
                    ratingView.setText("Rating: " + currRoute.getRating());
                    ratingView.setTextSize(18);
                    ratingView.setTextColor(Color.DKGRAY);
                    layout.addView(ratingView);

                    Button joinBtn = new Button(parent.getContext());
                    joinBtn.setText("JOIN");
                    joinBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    joinBtn.setBackgroundResource(android.R.color.holo_green_dark);
                    joinBtn.setTextColor(Color.WHITE);
                    joinBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showLoginDialog(view);
                        }
                    });
                    layout.addView(joinBtn);

                }
                else {
                    layout = (LinearLayout) convertView;
                }

                return layout;
            }

        }

    }
}
