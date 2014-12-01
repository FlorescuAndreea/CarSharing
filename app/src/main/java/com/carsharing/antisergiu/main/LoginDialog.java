package com.carsharing.antisergiu.main;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// LOGIN LOGIC
public class LoginDialog extends DialogFragment {

    private EditText mEditText;
    private LoginDialog mdialog = this;
    SharedPreferences prefs;
    public LoginDialog() {
        prefs = CreatePoolActivity.prefs;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view;
        if (CreatePoolActivity.getRegistrationStatus() == true) {

            view = inflater.inflate(R.layout.fragment_login, container);
            mEditText = (EditText) view.findViewById(R.id.login_et_password);
            Log.v("MAAAAAAAAAAAAAAAATA", view.toString());
            ((Button)view.findViewById(R.id.login_btn)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = mEditText.getText().toString();
                    Log.v("CARSHARING", "pass din edittext: " + pass);
                    Log.v("CARSHARING", "pass din prefs: " + prefs.contains("password") + " " + prefs.getString("password", " "));
                    if (prefs.getString("password", "").equals(pass)) {
                        Toast.makeText(view.getContext(), "Created car pool!", Toast.LENGTH_LONG);
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
                    CreatePoolActivity.setmIsRegistered(true);

                    // TODO check if data is correct, save to database and then dismiss

                    mdialog.dismiss();
                }
            });

        }

        return view;
    }

}