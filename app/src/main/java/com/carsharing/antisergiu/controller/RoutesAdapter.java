package com.carsharing.antisergiu.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carsharing.antisergiu.main.LoginDialog;
import com.carsharing.antisergiu.main.MatchingPoolsActivity;
import com.carsharing.antisergiu.main.MyPools;
import com.carsharing.antisergiu.main.Route;
import com.carsharing.antisergiu.model.MatchingPoolItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class RoutesAdapter extends BaseAdapter {

    private ArrayList<MatchingPoolItem> routes;

    public RoutesAdapter() {
        routes = new ArrayList<MatchingPoolItem>();
    }

    public void addItem(MatchingPoolItem item) {
        routes.add(item);
        notifyDataSetChanged();
    }

    public int getCount() {
        return routes.size();
    }

    public MatchingPoolItem getItem(int position) {
        return routes.get(position);
    }

    public long getItemId(int position) {
        return routes.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout layout;
        MatchingPoolItem currRoute = routes.get(position);

        if (convertView == null) {
            layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView driverView = new TextView(parent.getContext());
            driverView.setText("Driver: " + currRoute.getDriver());
            driverView.setTextSize(18);
            driverView.setTextColor(Color.DKGRAY);
            layout.addView(driverView);

            TextView ratingView = new TextView(parent.getContext());
            ratingView.setText("Rating: " + currRoute.getRating());
            ratingView.setTextSize(18);
            ratingView.setTextColor(Color.DKGRAY);
            layout.addView(ratingView);

            Button joinBtn = new Button(parent.getContext());
            joinBtn.setText("JOIN");
            joinBtn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            joinBtn.setBackgroundResource(android.R.color.holo_green_dark);
            joinBtn.setTextColor(Color.WHITE);
            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Activity host = (Activity) view.getContext();
                    LoginDialogListener loginDialogListener = new LoginDialogListener(host, view);


                }
            });
            layout.addView(joinBtn);

        }
        else {
            layout = (LinearLayout) convertView;
        }

        return layout;
    }


}