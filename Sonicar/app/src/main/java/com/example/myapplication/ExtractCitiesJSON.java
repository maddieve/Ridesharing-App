package com.example.myapplication;

import android.os.AsyncTask;

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

public class ExtractCitiesJSON extends AsyncTask<URL, Void, String> {

    public static List<String> cities=new ArrayList<>();

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

                JSONArray jsonArray = jsonObj.getJSONArray("Romania");
                for(int i = 0; i < jsonArray.length(); i++){
                    cities.add(jsonArray.getString(i));
                }


        } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
