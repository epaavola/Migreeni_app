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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadData();
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


    //    Save and get ArrayList in SharedPreference


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




}