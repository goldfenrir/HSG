package com.example.goldfenrir.hs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Alexis on 22/01/2016.
 */
public class UserController {
    public static void Register(String name, String lastname, String username, String password, String email,
                         String address, Double latitude, Double longitude){
        String url = "http://192.168.0.5:8080/register/" + name + "/" + lastname + "/" + username +
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
}
