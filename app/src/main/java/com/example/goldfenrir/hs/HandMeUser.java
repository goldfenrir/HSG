package com.example.goldfenrir.hs;

/**
 * Created by Alexis on 22/01/2016.
 */
public class HandMeUser {
    public int id;
    public String _name;
    public String _lastname;
    public String _username;
    public String _password;
    public String _email;
    public String _address;
    public Double _latitude;
    public Double _longitude;
    public HandMeUser(String name, String lastname, String username, String password, String email, String address, Double latitude, Double longitude){
        this._name = name;
        this._lastname = lastname;
        this._username = username;
        this._password = password;
        this._email = email;
        this._address = address;
        this._latitude = latitude;
        this._longitude = longitude;
    }
}
