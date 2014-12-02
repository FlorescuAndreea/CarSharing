package com.carsharing.antisergiu.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MyProfile extends Activity {

    public static String sharedPrefsName = "AppPrefs";
    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        prefs = getSharedPreferences(sharedPrefsName, MODE_MULTI_PROCESS);
        Button saveProfile = (Button)findViewById(R.id.saveProfile);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        getUserInfo(prefs.getString("username", ""));
        getRating(prefs.getString("username", ""));
    }

    private void save() {
        String profileUsername = prefs.getString("username", "");
        String profileName = ((EditText)findViewById(R.id.profileName)).getText().toString();
        String profileTelephone = ((EditText)findViewById(R.id.profileTelephone)).getText().toString();
        Switch musicSwitch = (Switch)findViewById(R.id.profileMusicSwitch);
        Boolean music = false;

        if (musicSwitch.isChecked()) {
            music = true;
        }

        Switch smokingSwitch = (Switch)findViewById(R.id.profileSmokingSwitch);
        Boolean smoking = false;

        if (smokingSwitch.isChecked()) {
            smoking = true;
        }


        Log.v("SAVAA", music + " " + smoking);
        String profileCarType = ((EditText)findViewById(R.id.profileCarType)).getText().toString();

        updateProfile(profileUsername, profileName, profileTelephone, smoking, music, profileCarType);
    }

    // get user's info
    public void getUserInfo(String usn) {
        HashMap <String, Object> params = new HashMap <String, Object> ();
        params.put("username", usn);

        ParseCloud.callFunctionInBackground("getUserInfo", params, new FunctionCallback<ParseObject>() {
            public void done(ParseObject res, ParseException e) {
                if (e == null) {
                    String name;
                    if(res.get("name") == null) {
                        name = "";
                    } else {
                        name = res.get("name").toString();
                    }

                    String telephone;
                    if(res.get("telephone") == null) {
                        telephone = "";
                    } else {
                        telephone = res.get("telephone").toString();
                    }

                    String music;
                    if(res.get("music") == null) {
                        music = "false";
                    } else {
                        music = res.get("music").toString();
                    }

                    String smoking;
                    if(res.get("smoking") == null) {
                        smoking = "false";
                    } else {
                        smoking = res.get("smoking").toString();
                    }

                    String car;
                    if(res.get("car") == null) {
                        car = "";
                    } else {
                        car = res.get("car").toString();
                    }

                    showMyProfileInfo(name, telephone, music, smoking, car);
                }
                else {

                }
            }
        });
    }

    // update user's profile
    public void updateProfile(String usn, String name, String phone, Boolean smoking, Boolean music, String car) {
        HashMap<String, Object> params = new HashMap <String, Object> ();
        params.put("username", usn);
        params.put("name", name);
        params.put("telephone", phone);
        params.put("smoking", smoking);
        params.put("music", music);
        params.put("car", car);

        ParseCloud.callFunctionInBackground("updateProfile", params, new FunctionCallback<String>() {
            public void done(String res, ParseException e) {
                if (e == null) {
                    // 'res' are valoarea: Profile updated successfully!
                } else {
                    // 'res' are valoarea: User not found!
                }
            }
        });
    }

    // get user's rating
    public void getRating(String username) {
        HashMap <String, Object> params = new HashMap <String, Object> ();
        params.put("username", username);

        ParseCloud.callFunctionInBackground("getRating", params, new FunctionCallback<String>() {
            public void done(String res, ParseException e) {
                if (e == null) {
                    showMyProfileRating(res);
                }
                else {
                    // 'res' are valoarea: Driver not found!
                }
            }
        });
    }

    public void showMyProfileInfo(String name, String telephone, String music, String smoking, String car) {
        EditText profileName = ((EditText)findViewById(R.id.profileName));
        profileName.setText(name);
        EditText profileTelephone = ((EditText)findViewById(R.id.profileTelephone));
        profileTelephone.setText(telephone);
        Switch musicSwitch = (Switch)findViewById(R.id.profileMusicSwitch);
        musicSwitch.setChecked(Boolean.parseBoolean(music));
        Switch smokingSwitch = (Switch)findViewById(R.id.profileSmokingSwitch);
        smokingSwitch.setChecked(Boolean.parseBoolean(smoking));
        EditText profileCarType = ((EditText)findViewById(R.id.profileCarType));
        profileCarType.setText(car);
    }

    public void showMyProfileRating(String rating) {
        TextView profileRating = ((TextView)findViewById(R.id.profileRating));
        profileRating.setText(rating);
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
}
