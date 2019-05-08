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

    //public String latitude, longtitude;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saa);

        requestPermission();

        //LocationManager location_manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(koordinaatit.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
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


        /*LocationListener location_listener = new

                LocationListener() {

                    // gets called when the location is updated
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude = String.valueOf(location.getLatitude());
                        longtitude = String.valueOf(location.getLongitude());
                        //Log.d(TAG,"lat" + latitude);
                        //Log.d(TAG,"long" + longtitude);

                        // Find the weather based on your coordinates
                        hae_saatiedot(latitude, longtitude);

                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    // If gps is setted off > open settings
                    @Override
                    public void onProviderDisabled(String provider) {
                        Intent asetukset = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(asetukset);
                    }

                };

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

                location_manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, location_listener);*/

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},1);
    }


    public void laheta_koordinaatit(String lati, String longi) {
        /*Intent nextActivity = new Intent(koordinaatit.this, saa.class);
        Bundle extrat = new Bundle();
        extrat.putString("latitude", lati);
        extrat.putString("longtitude", longti);
        nextActivity.putExtras(extrat);

        startActivity(nextActivity);*/

        saa saahaku = new saa();


        SharedPreferences koordinaatit_sharedpreferences = getSharedPreferences("koordinaatit_sharedpreferences", MODE_PRIVATE);
        SharedPreferences.Editor koordinaatit_editor = koordinaatit_sharedpreferences.edit();

        koordinaatit_editor.putString("latitude", lati);
        koordinaatit_editor.putString("longitude", longi);
        koordinaatit_editor.commit();

        saahaku.haetaan_saaobjektit();
    }
}