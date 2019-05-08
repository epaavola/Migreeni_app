package com.example.migreeni;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Merkinta class and Merkinta activity creates the 'Lisää Uusi Merkintä' functionality.
 * Main function of the Merkinta class is to pick the all the data user fills to the form and
 * then save that data as an object to the arraylist located in 'Merkinta_lista' singleton.
 * Save is triggered when the user clicks the 'tallenna' button in Merkinta activity.
 */
public class merkinta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkinta);

        // Creating date and time picking objects
         int resID = getResources().getIdentifier("pick_date",
                "id", getPackageName());
        PVM_picker set_date = new PVM_picker(this, resID);
        EditText editTextFromTime1 = findViewById(R.id.time_alku);
        EditText editTextFromTime2 = findViewById(R.id.time_loppu);
        time_picker fromTime1 = new time_picker(editTextFromTime1, this);
        time_picker fromTime2 = new time_picker(editTextFromTime2, this);
    }
    /**
     * Tallenna merkinta method saves all the user inputs from different EditTexts and checkboxes and SeekBar
     * We use PVM_picker and time_picker classes to create visual calendar and clock view for the user.
     *
     */
    public void tallenna_merkinta(View view){
        String laake = "Ei lääkitystä";
        String kipu = "Ei kipuja";
        String lisatiedot = "Ei lisätietoja";

        // Pick the date user has entered. PVM_picker creates the calendar view for user.
        final EditText text =  findViewById(R.id.pick_date);
        String pvm = text.getText().toString();

        // Pick the start time of attack
        final EditText text2 =  findViewById(R.id.time_alku);
        String time_alku = text2.getText().toString();

        // Pick the end time of attack
        final EditText text3 =  findViewById(R.id.time_loppu);
        String time_loppu = text3.getText().toString();

        // Unify the start and end time
        String aika = time_alku + " - " + time_loppu;


        // Get the medicine info from the checkboxes and give the right verbal output
        boolean laake1 = ((CheckBox) findViewById(R.id.ibuprofeeni)).isChecked();
        boolean laake2 = ((CheckBox) findViewById(R.id.parasetamoli)).isChecked();
        boolean laake3 = ((CheckBox) findViewById(R.id.tasmalaake)).isChecked();

        if(laake1 && !laake2 && !laake3) {
            laake = "Ibuprofeeni";
        }
        if(laake1 && laake2 && !laake3) {
            laake = "Ibuprofeeni, Parasetamoli";
        }
        if(laake1 && laake2 && laake3) {
            laake = "Ibuprofeeni, Parasetamoli, Täsmälääke";
        }
        if(!laake1 && laake2 && laake3) {
            laake = "Parasetamoli, Täsmälääke";
        }
        if(!laake1 && !laake2 && laake3) {
            laake = "Täsmälääke";
        }
        if(!laake1 && laake2 && !laake3) {
            laake = "Parasetamoli";
        }
        if(laake1 && !laake2 && laake3) {
            laake = "Ibuprofeeni, Täsmälääke";
        }

        // Get the value from 'pain meter' and give verbal value for each level
        SeekBar seek = findViewById(R.id.kipumittari);
        int seekValue = seek.getProgress();

        if(seekValue == 0){
            kipu = "Ei kipuja";
        }
        if(seekValue == 1){
            kipu = "Lievä";
        }
        if(seekValue == 2){
            kipu = "Kohtalainen";
        }
        if(seekValue == 3){
            kipu = "Voimakas";
        }
        if(seekValue == 4){
            kipu = "Sietämätön";
        }

        // Pick the text from the additional information box
        EditText edit = findViewById(R.id.set_lisatietoja);
        lisatiedot = edit.getText().toString();

        // Create new note object of migraine attack with the values from user input ( Date, time, medicine, pain, info)
        Uusi_merkinta merkinta = new Uusi_merkinta(pvm,aika,laake,kipu,lisatiedot);

        // Add the note object to the list so we can easily access data and also hash it
        Merkinta_lista.getInstance().getMerkinnat().add(merkinta);
        saveData();

        Toast.makeText(this,"Merkintä tallennettu",Toast.LENGTH_LONG).show();
        finish();
    }
    // Save list of entries to the shared preferences
    public void saveData() {
        SharedPreferences sharedPref = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Merkinta_lista.getInstance().getMerkinnat());
        editor.putString("list", json);
        editor.apply();
    }
}
