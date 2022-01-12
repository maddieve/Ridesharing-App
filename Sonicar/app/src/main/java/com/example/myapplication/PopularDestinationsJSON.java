package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PopularDestinationsJSON extends AsyncTask<URL, Void, String> {

    public static String stringJSON = "";
    JSONArray cities;
    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) urls[0].openConnection();
            conn.setRequestMethod("GET");
            InputStream ist = conn.getInputStream();

            InputStreamReader isr = new InputStreamReader(ist);
            BufferedReader br = new BufferedReader(isr);
            String linie = "";
            String sbuf = "";
            while ((linie = br.readLine()) != null) {
                sbuf += linie + "\n";
            }

            loadJSONObject(sbuf);

            return sbuf;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        return null;
    }

    public void loadJSONObject(String jsonStr) {
        if (jsonStr != null) {
            JSONObject jsonObj = null;
            try {
                jsonObj = new JSONObject(jsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                cities = jsonObj.getJSONArray("Destinations");

                for(int i=0;i<cities.length();i++)
                {

                    JSONObject city=cities.getJSONObject(i);
                    String name=city.getString("Name");

                    stringJSON+="Orasul "+name+": \n";

                    JSONObject landamrks=city.getJSONObject("City Landmarks");

                    String city1=landamrks.getString("city1");
                    String city2=landamrks.getString("city2");
                    String city3=landamrks.getString("city3");

                    stringJSON+="Top destinatii turistice: "+city1+", "+city2+", "+city3+"\n";

                    JSONObject restaurants=city.getJSONObject("Top Restaurants");

                    String rest1=restaurants.getString("restaurant1");
                    String rest2=restaurants.getString("restaurant2");
                    String rest3=restaurants.getString("restaurant3");

                    stringJSON+="Top restaurante: "+rest1+", "+rest2+", "+rest3+"\n";


                    JSONObject hotels=city.getJSONObject("Top Hotels");

                    String h1=hotels.getString("hotel1");
                    String h2=hotels.getString("hotel2");
                    String h3=hotels.getString("hotel3");

                    stringJSON+="Top hoteluri: "+h1+", "+h2+", "+h3+"\n"+"\n";


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
