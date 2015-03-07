package com.carsharing.timteam.model;

/**
 * Created by Andreea on 11/27/2014.
 */
public class MatchingPoolItem {
    private String driver;
    private String rating;
    private Integer id;
    private String objectID;

    public MatchingPoolItem() {
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
