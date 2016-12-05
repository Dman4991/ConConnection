package com.example.daniel.conconnection;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Daniel on 12/4/2016.
 */

public class Event {

    private String name;
    private ArrayList<Double> location;
    private Date startDate;
    private Date endDate;

    public Event(){

    }

    public Event(String name, double longitude, double latitude, Date startDate, Date endDate){
        this.name = name;
        location.add(0, longitude);
        location.add(1, latitude);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ArrayList<Double> getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
