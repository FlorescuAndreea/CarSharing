package com.symbidrive.timteam.model;

/**
 * Created by Andreea on 11/27/2014.
 */
public class UserPoolsItem {
    private String date;
    private Integer people;
    private Integer id;
    private String driver;
    private String objectID;



    // STANDARD: if the user is the driver, the driver parameter will be empty
    // else it contains the drivers name
    // Integer id will be the initial position in the ArrayList when UserPoolsItem is created
    public UserPoolsItem(String driver, String date, Integer people, Integer id, String objectID) {
        this.date = date;
        this.people = people;
        this.id = id;
        this.driver = driver;
        this.objectID = objectID;

    }

    public String getObjectID() {
        return objectID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }
}
