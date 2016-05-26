package com.example.daniel.conconnection;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserProfile implements User{
    private String name;
    private short age;
    private String bodyType;
    private String biography;
    private ArrayList<Integer> events;
    private ArrayList<Integer> matches;

    public UserProfile(){
        name = null;
        age = 0;
        bodyType = null;
        biography = null;
        events = null;
    }

    public UserProfile(String name, short age, String bodyType, String biography, ArrayList<Integer> myEvents){
        this.name = name;
        this.age = age;
        this.bodyType = bodyType;
        this.biography = biography;
        if(myEvents == null)
            events = new ArrayList<Integer>();
        else
            events = myEvents;
    }

    public boolean addEvent(int eventID) {
        if (events.contains(eventID)){
            Log.d("User", "User " + name + "already signed into event" + eventID + "\n");
            return false;
        }

        events.add(eventID);
        if(events.contains(eventID)) {
            Log.d("User", "User " + name + " signed into event" + eventID + "\n");
            return true;
        }
        else {
            Log.d("User", "Error adding event: " + name + ", "+ eventID + "\n");
            return false;
        }
    }

    public boolean addMatch(int userID){
        if(matches.contains(userID)) {
            Log.d("User", "User " + name + " already matched with: " + userID + "\n");
            return false;
        }
        events.add(userID);
        if(events.contains(userID)) {
            Log.d("User", "User " + name + " added match: " + userID + "\n");
            return true;
        }
        else {
            Log.d("User", "Error adding match: " + name + ", "+ userID + "\n");
            return false;
        }
    }

    public String getName(){return name;}
    public int getAge(){return age;}
    public String getBodyType(){return bodyType;}
    public String getBiography(){return biography;}
    public ArrayList<Integer> getEvents(){return events;}
    public ArrayList<Integer> getMatches(){return matches;}

    public void setName(String name){this.name = name;}
    public void setAge(short age){this.age = age;}
    public void setBodyType(String bodyType){this.bodyType = bodyType;}
    public void setBiography(String biography){this.biography = biography;}
}
