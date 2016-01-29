package com.example.goldfenrir.hs;

import java.util.Date;

/**
 * Created by Alexis on 22/01/2016.
 */
public class HandMeService {
    @com.google.gson.annotations.SerializedName("id")
    public int id;

    @com.google.gson.annotations.SerializedName("_title")
    public String _title;

    @com.google.gson.annotations.SerializedName("_description")
    public String _description;

    @com.google.gson.annotations.SerializedName("_date")
    public Date _date;

    @com.google.gson.annotations.SerializedName("_price")
    public Double _price;

    @com.google.gson.annotations.SerializedName("_radius")
    public Double _radius;

    @com.google.gson.annotations.SerializedName("creditCardAllowed")
    public boolean creditCardAllowed;

    @com.google.gson.annotations.SerializedName("idUserRequest")
    public int idUserRequest;

    @com.google.gson.annotations.SerializedName("_latitude")
    public Double _latitude;

    @com.google.gson.annotations.SerializedName("_longitude")
    public Double _longitude;

    public HandMeService(){

    }

    public HandMeService(String title, String description, Double price, Double latitude, Double longitude){
        this._title=title;
        this._description=description;
        this._price=price;
        this._latitude=latitude;
        this._longitude=longitude;
    }
}
