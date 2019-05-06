package com.example.migreeni;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public static final String tag = "tag";

    public long paiviaValissa(Date one, Date two){

        // lasketaan päivämäärien välinen erotus, jaetaan tulos millisekunneilla/päivä
        long erotus = ((one.getTime()-two.getTime())/86400000);
        return Math.abs(erotus);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData(); // Load data from Shared Preferences
        loadPaivia();

    }

    public void kalenteri_icon(View view) {
        Intent intent = new Intent(this, kalenteri.class);
        startActivity(intent);
    }

    public void saa_icon(View view) {
        Intent intent = new Intent(this, saa.class);
        startActivity(intent);
    }

    public void merkinta_icon(View view) {
        Intent intent = new Intent(this, merkinta.class);
        startActivity(intent);
    }

    public void viime_merkinta_icon(View view) {
        Intent intent = new Intent(this, vanhat_merkinnat.class);
        startActivity(intent);
    }

    // Load list of entries from shared preferences
    public void loadData() {
        SharedPreferences sharedPref = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("list", null);
        Type type = new TypeToken<ArrayList<Uusi_merkinta>>() {
        }.getType();
        ArrayList listanen;
        listanen = gson.fromJson(json, type);
        Merkinta_lista.getInstance().setMerkinnat(listanen);
    }

    public void loadPaivia(){

        Date tanaan = new Date();
        Calendar kal = Calendar.getInstance();

        //kal.set(haluttu päivämäärä); !!!
        kal.set(2019, 4, 10);
        Date merkintapaiva = kal.getTime();

        MainActivity obj = new MainActivity();
        long paivat = obj.paiviaValissa(tanaan, merkintapaiva);

        Log.d(tag, "Päiviä välissä testi: " + paivat);

        TextView tvPaivia = findViewById(R.id.viime_merkinta_arvo);
        tvPaivia.setText(String.valueOf(paivat));
    }


}