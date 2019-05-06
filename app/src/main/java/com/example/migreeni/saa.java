package com.example.migreeni;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class saa extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public TextView tv_kaupunki, tv_lampotila, tv_ilmanpaine, tv_kosteus, tv_yksityiskohta;
    private ImageView tv_ikoni;
    private RequestQueue mQueue;
    public String ikoniUrl = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        Bundle extras = getIntent().getExtras();
        String lat = extras.getString("latitude");
        String longt = extras.getString("longtitude");

        //Log.d(TAG, "latitude: " + lat);
        //Log.d(TAG, "longtitude: " + longt);

        // Find the widgets of saa-layout using ids
        tv_kaupunki = findViewById(R.id.kaupunki_textview);
        tv_ikoni = findViewById(R.id.saaikoni_imageview);
        tv_ilmanpaine = findViewById(R.id.ilmanpaine_textview);
        tv_kosteus = findViewById(R.id.kosteus_textview);
        tv_lampotila = findViewById(R.id.lampotila_textview);
        tv_yksityiskohta = findViewById(R.id.yksityiskohta_textview);

        //RequestQueue manages worker threads for running the network operations, reading from and writing to the cache, and parsing responses.
        mQueue = Volley.newRequestQueue(this);

        hae_saa(lat,longt);
    }

    // Gets the weather info and shows the values in the view
    public void hae_saa(String lat, String longt) {

        //Log.d(TAG,"lat" + lat);       //tested the coordinates
        //Log.d(TAG,"long" + longt);

        String url = "https:/api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + longt + "&appid=e629dbb8cc92982ffed615b4524532b6&units=metric";

        //get the JSON-Object based on  your location, using Openweathermap API
        JsonObjectRequest haku = new JsonObjectRequest (Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override //if we get the response, set the values from the API to the saa-layout
            public void onResponse(JSONObject response) {

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    String lampo = String.valueOf(main_object.getInt("temp"));
                    String kuvaus = object.getString("description");
                    String kaupunki = response.getString("name");
                    String ipaine = String.valueOf(main_object.getInt("pressure"));
                    String kosteus = String.valueOf(main_object.getInt("humidity"));

                    String ikoni = object.getString("icon");
                    ikoniUrl = "http://openweathermap.org/img/w/" + ikoni + ".png";

                    tv_lampotila.setText(lampo);
                    tv_yksityiskohta.setText(kuvaus);
                    tv_kaupunki.setText(kaupunki);
                    tv_ilmanpaine.setText(ipaine);
                    tv_kosteus.setText(kosteus);

                    setIkoni(ikoniUrl);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // If there is no response, print an error-log
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse (VolleyError error) {
                    Log.d(TAG, "something went wrong");
                    error.printStackTrace();
                }
            }
        );

        // Add the request to the RequestQueue.
        mQueue.add(haku);

    }


    // Set the weather icon to the imageview, icon we get from the API
    public void setIkoni(String ikoniUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(ikoniUrl).apply(options).into(tv_ikoni);

    }

}
