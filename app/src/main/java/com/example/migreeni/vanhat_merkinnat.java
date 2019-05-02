package com.example.migreeni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class vanhat_merkinnat extends AppCompatActivity {

    private static final String tagi = "Merkkaus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanhat_merkinnat);

        Log.d(tagi, "aktiviteetissa: vanhat merkinnat");
    }

}
