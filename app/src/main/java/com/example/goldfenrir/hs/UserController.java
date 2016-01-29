package com.example.goldfenrir.hs;

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
public class UserController {
    public static void Register(String name, String lastname, String username, String password, String email,
                         String address, Double latitude, Double longitude){
        String url = "http://192.168.0.5:8888/register/" + name + "/" + lastname + "/" + username +
                "/" + password + "/" + email + "/" + address + "/" + latitude + "/" + longitude;
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

    public static HandMeUser UserById(int idUser){
        List<HandMeUser> users = new ArrayList<>();
        try {
            String url = "http://192.168.0.5:8888/userbyid/" + idUser;
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
                    String name = json.getString("name");
                    String lastname = json.getString("lastname");
                    String username = json.getString("username");
                    String password = json.getString("password");
                    String email = json.getString("email");
                    String address = json.getString("address");
                    Double latitude = json.getDouble("latitude");
                    Double longitude = json.getDouble("longitude");

                    HandMeUser user = new HandMeUser(name,lastname,username,password,email,address,latitude,longitude);
                    users.add(user);
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

        return users.get(0);
    }
}
