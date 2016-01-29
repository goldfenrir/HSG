package com.example.goldfenrir.hs;

import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexis on 22/01/2016.
 */
public class ServiceController {

    public static List<HandMeService> Refresh(){
        List<HandMeService> services = new ArrayList<>();
        try {
            String url = "http://192.168.0.5:8888/services/";
            HttpURLConnection con;
            InputStream is;

            try {
                con = (HttpURLConnection) (new URL(url)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();

                StringBuffer sb = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String linea;

                while ((linea = br.readLine()) != null) {
                    sb.append(linea);
                }
                is.close();
                con.disconnect();

                JSONArray jsonArray = new JSONArray(sb.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    int id = json.getInt("id");
                    String title = json.getString("title");
                    String description = json.getString("description");
                    Double price = json.getDouble("price");
                    Double latitude = json.getDouble("latitude");
                    Double longitude = json.getDouble("longitude");

                    HandMeService service = new HandMeService(title, description, price, latitude, longitude);
                    services.add(service);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //private static final LatLng ORIGEN = new LatLng(-12.06955, -77.07978);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return services;
    }

    public static HandMeService ServiceById(int idService){
        List<HandMeService> services = new ArrayList<>();
        try {
            String url = "http://192.168.0.5:8888/services/" + idService;
            HttpURLConnection con;
            InputStream is;

            try {
                con = (HttpURLConnection) (new URL(url)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.connect();

                StringBuffer sb = new StringBuffer();
                is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String linea;

                while ((linea = br.readLine()) != null) {
                    sb.append(linea);
                }
                is.close();
                con.disconnect();

                JSONArray jsonArray = new JSONArray(sb.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    int id = json.getInt("id");
                    String title = json.getString("title");
                    String description = json.getString("description");
                    Double price = json.getDouble("price");
                    Double latitude = json.getDouble("latitude");
                    Double longitude = json.getDouble("longitude");

                    HandMeService service = new HandMeService(title, description, price, latitude, longitude);
                    services.add(service);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //private static final LatLng ORIGEN = new LatLng(-12.06955, -77.07978);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return services.get(0);
    }

    public static void Request(int idRequest, String title, String description, Double price, Double latitude, Double longitude){
        String url = "http://192.168.0.5:8888/request/" + idRequest + "/" + title + "/" + description + "/" + price
                + "/" + latitude + "/" + longitude;
        HttpURLConnection con;
        InputStream is;
        try {
            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();

            StringBuffer sb = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String linea;

            while ((linea = br.readLine()) != null) {
                sb.append(linea);
            }
            is.close();
            con.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
