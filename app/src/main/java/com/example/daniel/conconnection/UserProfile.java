package com.example.daniel.conconnection;

/**
 * Created by Daniel on 4/7/2016.
 */
public class UserProfile implements User{
    private String name;
    private short age;
    private String bodyType;
    private String biography;

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }

    public String getBodyType(){
        return bodyType;
    }

    public String getBiography(){
        return biography;
    }
}
