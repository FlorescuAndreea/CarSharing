package com.carsharing.timteam.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by Andreea on 12/3/2014.
 */
public class CreatePoolDialogListener implements DialogInterface.OnClickListener{

    Context context;
    Class redirectClass;

    public CreatePoolDialogListener(Context context, Class redirectClass) {
        this.context = context;
        this.redirectClass = redirectClass;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
        Intent intent = new Intent(context, redirectClass);
        context.startActivity(intent);
    }
}
