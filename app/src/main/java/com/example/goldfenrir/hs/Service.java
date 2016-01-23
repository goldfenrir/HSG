package com.example.goldfenrir.hs;

import java.util.Date;

/**
 * Created by Alexis on 22/01/2016.
 */
public class Service {
    public int id;
    public String title;
    public String description;
    public Date date;
    public Double price;
    public Double latitude;
    public Double longitude;
    public Service(int id, String title, String description, Double price, Double latitude, Double longitude){
        this.id=id;
        this.title=title;
        this.description=description;
        this.price=price;
        this.latitude=latitude;
        this.longitude=longitude;
    }
}
