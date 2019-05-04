package com.example.migreeni;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
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

    private TextView tv_kaupunki, tv_lampotila, tv_ilmanpaine, tv_kosteus, tv_yksityiskohta;
    private ImageView tv_ikoni;
    private RequestQueue mQueue;
    public String ikoniUrl = "", latitude, longtitude;
    private Context context;

    private LocationManager location_manager;
    private LocationListener location_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        tv_kaupunki = (TextView) findViewById(R.id.kaupunki_textview);
        tv_ikoni = (ImageView) findViewById(R.id.saaikoni_imageview);
        tv_ilmanpaine = (TextView) findViewById(R.id.ilmanpaine_textview);
        tv_kosteus = (TextView) findViewById(R.id.kosteus_textview);
        tv_lampotila = (TextView) findViewById(R.id.lampotila_textview);
        tv_yksityiskohta = (TextView) findViewById(R.id.yksityiskohta_textview);

        mQueue = Volley.newRequestQueue(this);

        location_manager = (LocationManager) getSystemService(LOCATION_SERVICE);

        location_listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = String.valueOf(location.getLatitude());
                longtitude = String.valueOf(location.getLongitude());
                Log.d(TAG,"lat" + latitude);
                Log.d(TAG,"long" + longtitude);

                hae_saa(latitude, longtitude);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }


            @Override
            public void onProviderDisabled(String provider) {
                Intent asetukset = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(asetukset);
            }


        };

        lataa_paikka();

    }


   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                lataa_paikka();
                break;
            default:
                break;
        }
    }


    public void lataa_paikka() {
        // first check for permissions // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
            ,10);
            }
            return;
        } else {
            //noinspection MissingPermission
            location_manager.requestLocationUpdates("gps", 0, 500, location_listener);
        }
    }


    public void hae_saa(String lat, String longt) {

        //String latitude = lat;
        //String longtitude = longt;
        Log.d(TAG,"latii" + lat);
        Log.d(TAG,"longii" + longt);

        String url = "https:/api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + longt + "&appid=e629dbb8cc92982ffed615b4524532b6&units=metric";

        JsonObjectRequest haku = new JsonObjectRequest (Request.Method.GET, url,null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    String lampo = String.valueOf(main_object.getDouble("temp"));
                    String kuvaus = object.getString("description");
                    String kaupunki = response.getString("name");
                    String ipaine = String.valueOf(main_object.getDouble("pressure"));
                    String kosteus = String.valueOf(main_object.getDouble("humidity"));

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

            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse (VolleyError error) {
                    Log.d(TAG, "something went wrong");
                    error.printStackTrace();
                }
            }
        );

        mQueue.add(haku);

    }

    public void setIkoni(String ikoniUrl) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);

        Glide.with(this).load(ikoniUrl).apply(options).into(tv_ikoni);

    }


}
