package com.carsharing.antisergiu.model;

/**
 * Created by Andreea on 11/27/2014.
 */
public class MatchingPoolItem {
    private String driver;
    private Integer rating;

    public MatchingPoolItem() {

    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }
}
