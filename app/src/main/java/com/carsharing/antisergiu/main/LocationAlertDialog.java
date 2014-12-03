package com.carsharing.antisergiu.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
/**
 * Created by Andreea on 12/3/2014.
 */
public class LocationAlertDialog {

    Context context;

    public LocationAlertDialog(Context context) {
        this.context = context;
    }

    public void createDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
