package com.example.daniel.conconnection;

import java.io.Serializable;

/**
 * Created by Daniel on 4/7/2016.
 */
public interface User{
    String getEmail();
    String getPassword();
    String getName();
    short getAge();
    String getBodyType();
    String getBiography();
    boolean getAutoLogin();

    void setEmail(String email);
    void setPassword(String password);
    void setName(String name);
    void setAge(short age);
    void setBodyType(String bodyType);
    void setBiography(String biography);
    void setAutoLogin(boolean autoLogin);
}