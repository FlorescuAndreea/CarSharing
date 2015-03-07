package com.symbidrive.timteam.main;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.Parse;

public class MainActivity extends Activity {

    public static boolean loginSuccessful = false;
    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;
    public static boolean mIsRegistered = false;

    private int my_profile = 0, my_pools= 1, login = 2;
    private Menu menu;

    private MenuItem loginMenuItem;
    private MenuItem myPoolsMenuItem;
    private MenuItem myProfileMenuItem;

    public void showLoginDialog(View view) {
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.show(this.getFragmentManager(), "fragment_login");
    }

    public static boolean getRegistrationStatus() {
        return mIsRegistered;
    }

    public static void setmIsRegistered(boolean value) {
        mIsRegistered = value;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("registered", true);
        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "Xknvjn986noGgWkwzPS3xRARe4LtrTXRjHvCorSR", "NCuy5aRvDhuGCM5YphuV5EYtMNQLn4nh67vOGc8L");

        prefs = getSharedPreferences(sharedPrefsName, MODE_MULTI_PROCESS);
        mIsRegistered = prefs.getBoolean("registered", false);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
   //     new GcmRegistrationAsyncTask().execute(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (menu != null) {
            if (loginSuccessful) {
                menu.getItem(login).setVisible(false);
                menu.getItem(my_pools).setVisible(true);
                menu.getItem(my_profile).setVisible(true);
            } else {
                menu.getItem(login).setVisible(true);
                menu.getItem(my_pools).setVisible(false);
                menu.getItem(my_profile).setVisible(false);
            }
            this.invalidateOptionsMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        this.menu = menu;
        this.loginMenuItem = menu.getItem(login);
        this.myPoolsMenuItem = menu.getItem(my_pools);
        this.myProfileMenuItem = menu.getItem(my_profile);

        if (loginSuccessful) {
//            menu.getItem(login).setVisible(false);
            loginMenuItem.setVisible(false);
        } else {
//            menu.getItem(my_pools).setVisible(false);
//            menu.getItem(my_profile).setVisible(false);
            myProfileMenuItem.setVisible(false);
            myPoolsMenuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        if (loginSuccessful) {
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
        } else {
            if (id == R.id.action_login) {
                showLoginDialog(getCurrentFocus());
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){

        if (loginSuccessful) {
            myPoolsMenuItem.setVisible(true);
            myProfileMenuItem.setVisible(true);
            loginMenuItem.setVisible(false);
        } else {
            myPoolsMenuItem.setVisible(false);
            myProfileMenuItem.setVisible(false);
            loginMenuItem.setVisible(true);
        }

        return super.onPrepareOptionsMenu(menu);
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
