package com.example.goldfenrir.hs;

/**
 * Created by Alexis on 22/01/2016.
 */
public class User {
    public int id;
    public String name;
    public String lastname;
    public String username;
    public String password;
    public String email;
    public String address;
    public Double latitude;
    public Double longitude;
    public User(String name, String lastname, String username, String password, String email, String address, Double latitude, Double longitude){
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
