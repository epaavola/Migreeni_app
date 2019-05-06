package com.example.migreeni;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class koordinaatit extends AppCompatActivity {

    private static final String TAG = "Koordinaatit";

    private LocationManager location_manager;
    private LocationListener location_listener;

    public String latitude, longtitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        location_manager =(LocationManager) getSystemService(LOCATION_SERVICE);

        location_listener = new

        LocationListener() {

            // gets called when the location is updated
            @Override
            public void onLocationChanged (Location location){
                latitude = String.valueOf(location.getLatitude());
                longtitude = String.valueOf(location.getLongitude());
                //Log.d(TAG,"lat" + latitude);
                //Log.d(TAG,"long" + longtitude);

                // Find the weather based on your coordinates
                hae_saatiedot(latitude, longtitude);

            }

            @Override
            public void onStatusChanged (String s,int i, Bundle bundle){

            }

            @Override
            public void onProviderEnabled (String s){

            }

            // If gps is setted off > open settings
            @Override
            public void onProviderDisabled (String provider){
                Intent asetukset = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(asetukset);
            }

        };

        tarkista_luvat();
    }

    public void tarkista_luvat() {
        // first check for permissions
        // this code won't execute IF permissions are not allowed
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
            // permissions ok
            //Make a new location request when user moves 500m
            location_manager.requestLocationUpdates("gps", 0, 500, location_listener);
        }
    }

    // Stored the results from permissions
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 10) {
            tarkista_luvat();
        }
    }

    public void hae_saatiedot(String lati, String longti) {
        Intent nextActivity = new Intent(koordinaatit.this, saa.class);
        Bundle extrat = new Bundle();
        extrat.putString("latitude", lati);
        extrat.putString("longtitude", longti);
        nextActivity.putExtras(extrat);

        startActivity(nextActivity);
    }
}

