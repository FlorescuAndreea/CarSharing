package com.carsharing.antisergiu.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.carsharing.antisergiu.model.MatchingPoolItem;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.HashMap;

// LOGIN LOGIC
public class LoginDialog extends DialogFragment {

    private EditText mEditText;
    private LoginDialog mdialog = this;
    SharedPreferences prefs;
    public LoginDialog() {
        Activity activity = mdialog.getActivity();
        if (activity instanceof CreatePoolActivity) {
            prefs = CreatePoolActivity.prefs;
        } else {
            prefs = MatchingPoolsActivity.prefs;
        }

    }

    public void registerOnDismissDialog(DialogInterface.OnDismissListener listener) {
        this.getDialog().setOnDismissListener(listener);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = mdialog.getActivity();
        Log.v("CARSHARING", "Din activity: " + activity.getClass().toString());
        if (activity instanceof CreatePoolActivity) {
            ((CreatePoolActivity)activity).savePool();
        } else {
            ((MatchingPoolsActivity)activity).joinPool();
        }
    }

    private void setUserRegistred() {
        Activity activity = mdialog.getActivity();
        if (activity instanceof CreatePoolActivity) {
            CreatePoolActivity.setmIsRegistered(true);
        } else {
            MatchingPoolsActivity.setmIsRegistered(true);
        }
    }

    private boolean getUserRegistrationStatus() {
        Activity activity = mdialog.getActivity();
        if (activity instanceof CreatePoolActivity) {
            return CreatePoolActivity.getRegistrationStatus();
        } else {
            return MatchingPoolsActivity.getRegistrationStatus();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;

        if (getUserRegistrationStatus() == true) {

            view = inflater.inflate(R.layout.fragment_login, container);
            mEditText = (EditText) view.findViewById(R.id.login_et_password);

            ((Button)view.findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = mEditText.getText().toString();
                    if (prefs.getString("password", "").equals(pass)) {
                        Toast.makeText(view.getContext(), "Created car pool!", Toast.LENGTH_LONG);
                        setUserRegistred();
                        MainActivity.loginSuccessful = true;
                        mdialog.dismiss();
                    } else {
                        Toast.makeText(view.getContext(), "Error, password incorrect!", Toast.LENGTH_LONG);
                    }
                }
            });
            getDialog().setTitle("Please confirm password!");


        } else {
            view = inflater.inflate(R.layout.fragment_register, container);
            getDialog().setTitle("Please register to use the App!");
            Button btnRegister = (Button)view.findViewById(R.id.register_btn);
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = ((EditText) (view.findViewById(R.id.register_et_name))).getText().toString();
                    String password = ((EditText) (view.findViewById(R.id.register_et_password))).getText().toString();
                    String telephone = ((EditText) (view.findViewById(R.id.register_et_telephone))).getText().toString();

                    Log.v("CARSHARING", "USER: " + username + " PASS: " + password + " TELEPHONE: " + telephone);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.commit();
                    setUserRegistred();
                    // TODO check if data is correct, save to database and then dismiss
                    registerUser(username, password, telephone);

                    //mdialog.dismiss();
                }
            });

        }

        return view;
    }

    // register new user to server
    public void registerUser(String usn, String pass, String phone) {
        HashMap<String, Object> params = new HashMap <String, Object> ();
        params.put("username", usn);
        params.put("password", pass);
        params.put("telephone", phone);

        ParseCloud.callFunctionInBackground("registerUser", params, new FunctionCallback<String>() {
            public void done(String res, ParseException e) {
                if (e == null) {
                    // 'res' are valoarea: User registered successfully!
                    MainActivity.loginSuccessful = true;
                    mdialog.dismiss();
                } else {
                    // 'res' are valoarea: User already taken!
                    // TODO: trb sa inrosesc ceva
                    Dialog dialogView = mdialog.getDialog();
                    EditText usernameEt = (EditText) dialogView.findViewById(R.id.register_et_name);
                    usernameEt.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

}