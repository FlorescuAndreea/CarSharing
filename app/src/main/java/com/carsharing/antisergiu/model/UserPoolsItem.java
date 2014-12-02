package com.carsharing.antisergiu.model;

import java.util.Date;

/**
 * Created by Andreea on 11/27/2014.
 */
public class UserPoolsItem {
    private Date day;
    private String hour;
    private Integer people;
    private Integer id;
    private String driver;
    private String objectID;


    // STANDARD: if the user is the driver, the driver parameter will be empty
    // else it contains the drivers name
    // Integer id will be the initial position in the ArrayList when UserPoolsItem is created
    public UserPoolsItem(String driver, Date day, String hour, Integer people, Integer id, String objectID) {
        this.day = day;
        this.hour = hour;
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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }
}
