package com.carsharing.antisergiu.model;

import java.util.Date;

/**
 * Created by Andreea on 11/27/2014.
 */
public class UserPoolsItem {
    private Date day;
    private String hour;
    private Integer people;

    public UserPoolsItem() {

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
