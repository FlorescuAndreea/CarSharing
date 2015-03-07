package com.carsharing.timteam.main;

import android.content.Context;
import android.content.DialogInterface;
/**
 * Created by Andreea on 12/3/2014.
 */
public class CustomAlertDialog {

    Context context;


    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    public void createDialog(String name, String message, DialogInterface.OnClickListener listener) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setTitle(name);
        if (listener == null) {
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        } else {
            builder.setPositiveButton("OK", listener);
        }

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
