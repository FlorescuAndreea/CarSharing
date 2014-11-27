package com.carsharing.antisergiu.model;

/**
 * Created by Andreea on 11/27/2014.
 */
public class MatchingPoolItem {
    private String driver;
    private Integer rating;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MatchingPoolItem(String driver, Integer rating, Integer id) {
        this.driver = driver;
        this.rating = rating;

        this.id = id;
    }

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
