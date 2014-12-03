package com.carsharing.antisergiu.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/**
 * Created by Andreea on 12/3/2014.
 */
public class AlertDialog {

    Context context;

    public AlertDialog(Context context) {
        this.context = context;
    }

    public void createDialog(String name, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setTitle(name);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
