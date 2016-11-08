package com.example.daniel.conconnection;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class UserProfile implements User, Serializable{
    private String email;
    private String password;
    private String name;
    private int age;
    private String bodyType;
    private String biography;
    private ArrayList<Integer> events;
    private ArrayList<Integer> matches;
    private boolean autoLogin;


    public UserProfile(){
        email = null;
        password = null;
        name = null;
        age = 0;
        bodyType = null;
        biography = null;
        events = null;
        autoLogin = false;
    }

    public UserProfile(String email, String password, String name, int age, String bodyType, String biography, ArrayList<Integer> myEvents){
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return email + " " + password + " " + name + " "+ age + " "+ bodyType + " "+ biography;
    }

    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getName(){return name;}
    public int getAge(){return age;}
    public String getBodyType(){return bodyType;}
    public String getBiography(){return biography;}
    public ArrayList<Integer> getEvents(){return events;}
    public ArrayList<Integer> getMatches(){return matches;}
    public boolean getAutoLogin(){return autoLogin;}

    public void setEmail(String email){this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setName(String name){this.name = name;}
    public void setAge(int age){this.age = age;}
    public void setBodyType(String bodyType){this.bodyType = bodyType;}
    public void setBiography(String biography){this.biography = biography;}
    public void setAutoLogin(boolean autoLogin){this.autoLogin = autoLogin;}
}
