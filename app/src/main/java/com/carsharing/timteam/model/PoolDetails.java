package com.carsharing.timteam.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Andrei on 12/3/2014.
 */
public class PoolDetails {

    HashMap <String, String> passengersTelephones;
    LatLng source, destination;
    String driverUsername;
    String driverTelephone;

    public String getDriverTelephone() {
        return driverTelephone;
    }

    public void setDriverTelephone(String driverTelephone) {
        this.driverTelephone = driverTelephone;
    }

    Boolean weekly;

    public PoolDetails() {
        passengersTelephones = new HashMap<String, String>();
    }

    public void addPassenger(String name, String telephone) {
        passengersTelephones.put(name, telephone);
    }

    public HashMap<String, String> getPassengersTelephones() {
        return passengersTelephones;
    }

    public LatLng getSource() {
        return source;
    }

    public void setSource(Double lat, Double lng) {
        this.source = new LatLng(lat, lng);
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(Double lat, Double lng) {
        this.destination = new LatLng(lat, lng);
    }

    public String getDriverUsername() {
        return driverUsername;
    }

    public void setDriverUsername(String driverUsername) {
        this.driverUsername = driverUsername;
    }

    public Boolean getWeekly() {
        return weekly;
    }

    public void setWeekly(Boolean weekly) {
        this.weekly = weekly;
    }
}
