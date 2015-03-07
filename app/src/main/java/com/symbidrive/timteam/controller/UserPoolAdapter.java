package com.symbidrive.timteam.controller;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.symbidrive.timteam.model.UserPoolsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by radu992 on 28.11.2014.
 */
public class UserPoolAdapter extends BaseAdapter {
    private List<UserPoolsItem> listElements;

    public UserPoolAdapter() {
        listElements = new ArrayList<UserPoolsItem>();
    }

    public UserPoolAdapter(List<UserPoolsItem> poolItems) {
       this.listElements = poolItems;
    }

    @Override
    public int getCount() {
        return listElements.size();
    }

    @Override
    public Object getItem(int i) {
        return listElements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listElements.get(i).getId();
    }

    public void addItem(UserPoolsItem item) {
        listElements.add(item);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;
        UserPoolsItem currentPool = listElements.get(position);

        if (convertView == null) {
            layout = new LinearLayout(parent.getContext());
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView timeView = new TextView(parent.getContext());
            timeView.setText("Day: " + currentPool.getDate());
            timeView.setTextSize(18);
            timeView.setTypeface(null, Typeface.BOLD);
            timeView.setTextColor(Color.DKGRAY);
            layout.addView(timeView);

            String driverName = currentPool.getDriver();
            if (!driverName.isEmpty()) {
                TextView driverView = new TextView(parent.getContext());
                driverView.setText("Driver: " + driverName);
                driverView.setTextSize(18);
                driverView.setTextColor(Color.DKGRAY);
                layout.addView(driverView);
            }


            TextView peopleView = new TextView(parent.getContext());
            peopleView.setText("People: " + currentPool.getPeople());
            peopleView.setTextSize(18);
            peopleView.setTextColor(Color.DKGRAY);
            layout.addView(peopleView);

        }
        else {
            layout = (LinearLayout) convertView;
        }
        return layout;
    }
}
