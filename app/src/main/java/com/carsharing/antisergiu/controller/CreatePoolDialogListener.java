package com.carsharing.antisergiu.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.carsharing.antisergiu.main.CreatePoolActivity;
import com.carsharing.antisergiu.main.MainActivity;

/**
 * Created by Andreea on 12/3/2014.
 */
public class CreatePoolDialogListener implements DialogInterface.OnClickListener{

    Context context;

    CreatePoolDialogListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}
