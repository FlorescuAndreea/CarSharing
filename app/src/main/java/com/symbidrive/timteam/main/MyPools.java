package com.symbidrive.timteam.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.symbidrive.timteam.controller.UserPoolAdapter;
import com.symbidrive.timteam.model.UserPoolsItem;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MyPools extends Activity {

    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;
    protected static ArrayList<UserPoolsItem> poolList;
    UserPoolAdapter userPoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pools);
        poolList = new ArrayList<UserPoolsItem>();

        prefs = getSharedPreferences(sharedPrefsName, MODE_MULTI_PROCESS);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    private void createDriverDetailsActivity(String objectID) {
        Intent intent = new Intent(getBaseContext(), PoolDetailsDriver.class);
        intent.putExtra("type", "driver");
        intent.putExtra("objectID", objectID);
        startActivity(intent);
    }

    private void createPassengerDetailsActivity(String objectID) {
        Intent intent = new Intent(getBaseContext(), PoolDetails.class);
        intent.putExtra("type", "driver");
        intent.putExtra("objectID", objectID);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        displayPools();
    }

    public void displayPools(){

        getMyPools(prefs.getString("username", ""), 0);
        getMyPools(prefs.getString("username", ""), 1);

        userPoolAdapter = new UserPoolAdapter();
        ListView listView = (ListView) findViewById(R.id.my_pools_listview);
        listView.setAdapter(userPoolAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserPoolsItem clickedItem = (UserPoolsItem)userPoolAdapter.getItem(position);
                if (clickedItem.getDriver().equals("")) {
                    createDriverDetailsActivity(clickedItem.getObjectID());
                } else {
                    createPassengerDetailsActivity(clickedItem.getObjectID());
                }
            }
        });
    }

    // get user's pools from server (type: 0 - driver, 1 - passenger)
    public void getMyPools(String usn, final Integer userType) {
        HashMap<String, Object> params = new HashMap <String, Object> ();
        params.put("username", usn);
        params.put("userType", userType);

        ParseCloud.callFunctionInBackground("getMyPools", params, new FunctionCallback<ArrayList<ParseObject>>() {
            public void done(ArrayList<ParseObject> res, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < res.size(); i++) {
                        if (userType == 0)
                            showInMyPoolsView(res.get(i).getObjectId(), res.get(i).get("date").toString(),
                                    String.valueOf(res.get(i).getJSONArray("passengers").length()));
                        else
                            showInMyPoolsView(res.get(i).getObjectId(), res.get(i).get("driver").toString(),res.get(i).get("date").toString(),
                                    String.valueOf(res.get(i).getJSONArray("passengers").length()));
                    }

                } else {
                    // 'res' are valoarea: No pools to show!
                }
            }
        });
    }

    public void showInMyPoolsView(String poolId, String date, String people) {
        // aceste valori se pot pune in view-ul MyPools

        Log.d("POOLID", poolId); // se foloseste la functia viewPool
        Log.d("DATE", date);
        Log.d("PEOPLE", people);

        Integer peopleInt = Integer.parseInt(people);
        Integer index = poolList.size();
        Log.v("PoolsView", index + "");
        String driver = "";
        String objectID = poolId;
        userPoolAdapter.addItem(new UserPoolsItem(driver, date, peopleInt, index, objectID));
    }

    public void showInMyPoolsView(String poolId, String driver, String date, String people) {
        // aceste valori se pot pune in view-ul MyPools

        Log.d("POOLID", poolId); // se foloseste la functia viewPool
        Log.d("DRIVER", driver);
        Log.d("DATE", date);
        Log.d("PEOPLE", people);

        userPoolAdapter.addItem(new UserPoolsItem(driver, date, Integer.parseInt(people), poolList.size(), poolId));
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
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onStop() {
        super.onStop();

        poolList.clear();
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
            View rootView = inflater.inflate(R.layout.fragment_my_pools, container, false);
            // TODO get pools from db and add them to a List<UserPoolsItems>
            // TODO create UserPoolAdapter using contructor with List as a parameter
            // TODO when adding a UserPoolItem, if the driver is the current user, create an object
            // with empty string "" as parameter for driver
            Log.v("AICI", poolList.size() + "");

            for (int i = 0; i < poolList.size(); i++)
                Log.v("BLESS", poolList.get(i).getDriver());



            UserPoolAdapter userPoolAdapter = new UserPoolAdapter(MyPools.poolList);
            ListView listView = (ListView) rootView.findViewById(R.id.my_pools_listview);
            listView.setAdapter(userPoolAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    System.out.println(parent.getClass());
                    Intent intent = new Intent(parent.getContext(), PoolDetails.class);
                    startActivity(intent);
                }
            });

            return rootView;
        }
    }
}
