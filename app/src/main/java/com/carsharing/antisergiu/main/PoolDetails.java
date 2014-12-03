package com.carsharing.antisergiu.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class PoolDetails extends Activity {
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

        viewPool(poolID, 1);
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

                    if (userType == 1) {
                        final String usn = res.get(0).get("driver").toString();

                        tmp.put("username", usn);

                        ParseCloud.callFunctionInBackground("getPhone", tmp, new FunctionCallback<String>() {
                            public void done(String phone, ParseException e) {
                                if (e == null) {
                                    addDetailsToPool(Double.parseDouble(source_lat), Double.parseDouble(source_long),
                                            Double.parseDouble(dest_lat), Double.parseDouble(dest_long), usn, phone);
                                } else {
                                    // 'res' are valoarea: User not found!
                                }
                            }
                        });
                    }
                } else {
                    // 'res' are valoarea: Pool not found!
                }
            }
        });
    }

    // rate driver


    public void addDetailsToPool(Double source_lat, Double source_long, Double dest_lat, Double dest_long,
                                 String driver, String telephone) {

        TextView driverView = (TextView) findViewById(R.id.driver_name);
        driverView.setText(driver);

        TextView telephoneView = (TextView) findViewById(R.id.telephone_number);
        telephoneView.setText(telephone);
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

        //noinspection SimplifiableIfStatement
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
            View rootView;
            rootView = inflater.inflate(R.layout.fragment_pool_details, container, false);

            Button submitRating = (Button)rootView.findViewById(R.id.submit_btn);
            final Spinner rateSpinner = (Spinner) rootView.findViewById(R.id.spinner);
            final TextView driverUsername = (TextView) rootView.findViewById(R.id.driver_name);
            submitRating.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rateDriver(driverUsername.getText().toString(), Integer.parseInt(rateSpinner.getSelectedItem().toString()));
                }
            });
            return rootView;
        }

        public void rateDriver(String username, Integer rating) {
            HashMap <String, Object> params = new HashMap <String, Object> ();
            params.put("username", username);
            params.put("rating", rating);

            ParseCloud.callFunctionInBackground("rateDriver", params, new FunctionCallback<String>() {
                public void done(String res, ParseException e) {
                    if (e == null) {
                        // 'res' are valoarea: Driver rated successfully!
                    }
                    else {
                        // 'res' are valoarea: Driver not found!
                    }
                }
            });
        }
    }
}
