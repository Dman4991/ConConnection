package com.example.daniel.conconnection;

import java.util.ArrayList;

/**
 * Created by Daniel on 4/7/2016.
 */
public interface User{
    String getEmail();
    String getPassword();
    String getName();
    int getAge();
    String getBodyType();
    String getBiography();
    boolean getAutoLogin();
    ArrayList<Event> getEvents();
    boolean hasEvent(Event event);

    void setEmail(String email);
    void setPassword(String password);
    void setName(String name);
    void setAge(int age);
    void setBodyType(String bodyType);
    void setBiography(String biography);
    void setAutoLogin(boolean autoLogin);
    boolean addEvent(Event event);
    void removeEvent(Event event);
}