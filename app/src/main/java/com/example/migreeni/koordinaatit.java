package com.example.migreeni;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class koordinaatit extends AppCompatActivity {

    private static final String TAG = "Koordinaatit";

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(koordinaatit.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG,"hyvaks");
            return;
        }

        client.getLastLocation().addOnSuccessListener(koordinaatit.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    String  latitude = String.valueOf(location.getLatitude());
                    String longitude = String.valueOf(location.getLongitude());

                    Log.d(TAG,"lat: "+ latitude);
                    Log.d(TAG, "long " + longitude);

                    laheta_koordinaatit(latitude, longitude);
                }

            }
        });

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }

    public void laheta_koordinaatit(String lati, String longi) {
        Intent nextActivity = new Intent(koordinaatit.this, saa.class);
        Bundle extrat = new Bundle();
        extrat.putString("latitude", lati);
        extrat.putString("longtitude", longi);
        nextActivity.putExtras(extrat);

        startActivity(nextActivity);
        finish();
    }
}