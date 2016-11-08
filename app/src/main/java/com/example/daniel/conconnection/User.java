package com.example.daniel.conconnection;

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

    void setEmail(String email);
    void setPassword(String password);
    void setName(String name);
    void setAge(int age);
    void setBodyType(String bodyType);
    void setBiography(String biography);
    void setAutoLogin(boolean autoLogin);
}