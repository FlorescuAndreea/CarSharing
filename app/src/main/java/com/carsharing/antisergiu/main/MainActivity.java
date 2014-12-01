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

import com.parse.Parse;

public class MainActivity extends Activity {

    public static boolean loginSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "Xknvjn986noGgWkwzPS3xRARe4LtrTXRjHvCorSR", "NCuy5aRvDhuGCM5YphuV5EYtMNQLn4nh67vOGc8L");

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
   //     new GcmRegistrationAsyncTask().execute(this);

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
        if (id == R.id.action_my_pools) {
            Intent intent = new Intent(this, MyPools.class);
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void createPool(View view) {
        Intent createPoolIntent = new Intent(this, CreatePoolActivity.class);
        startActivity(createPoolIntent);
    }

    public void searchRoutes(View view) {
        Intent searchPoolIntent = new Intent(this, SearchPoolActivity.class);
        startActivity(searchPoolIntent);
    }

}
