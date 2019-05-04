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

public class saa extends AppCompatActivity implements LocationListener {

    private static final String TAG = "MainActivity";

    private TextView tv_kaupunki, tv_lampotila, tv_ilmanpaine, tv_kosteus, tv_yksityiskohta;
    private ImageView tv_ikoni;
    private RequestQueue mQueue;
    public String ikoniUrl = "";
    private Context context;

    private LocationManager location_manager;

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

        location_manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        String provider = location_manager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = location_manager.getLastKnownLocation(provider);

        //location_manager.requestLocationUpdates(location_manager.GPS_PROVIDER, 0, 0, this);

        //Location location = location_manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        onLocationChanged(location);

        //lataa_paikka();

        //hae_saa();
    }

    @Override
    public void onLocationChanged(Location location) {
        //String latitude = String.valueOf(location.getLatitude());
        //String longtitude = String.valueOf(location.getLongitude());
        double pitka = location.getLongitude();
        double poikki = location.getLatitude();

        Log.d(TAG, "pitka: " + pitka);
        Log.d(TAG, "poikki: " + poikki);

        String latitude = Double.toString(pitka);
        String longtitude = Double.toString(poikki);

        Log.d(TAG,"lat: " + latitude);
        Log.d(TAG, "long: " + longtitude);

        hae_saa(latitude, longtitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

   /* public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
            ,10);
            }
            return;
        } else {
            //noinspection MissingPermission
            location_manager.requestLocationUpdates("gps", 5000, 0, location_listener);
        }
    }*/


    public void hae_saa(String lat, String longt) {

        String latitude = lat;
        String longtitude = longt;

        String url = "https:/api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longtitude + "&appid=e629dbb8cc92982ffed615b4524532b6&units=metric";

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
