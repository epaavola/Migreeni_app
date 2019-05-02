package com.example.migreeni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class merkinta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkinta);

         int resID = getResources().getIdentifier("pick_date",
                "id", getPackageName());

        PVM_picker set_date = new PVM_picker(this, resID);
        EditText editTextFromTime1 = findViewById(R.id.time_alku);
        EditText editTextFromTime2 = findViewById(R.id.time_loppu);
        time_picker fromTime1 = new time_picker(editTextFromTime1, this);
        time_picker fromTime2 = new time_picker(editTextFromTime2, this);
    }

    public void tallenna_merkinta(View view){
        String laake = "Ei lääkitystä";
        final EditText text =  findViewById(R.id.pick_date);
        String pvm = text.getText().toString();

        final EditText text2 =  findViewById(R.id.time_alku);
        String time_alku = text2.getText().toString();

        final EditText text3 =  findViewById(R.id.time_loppu);
        String time_loppu = text3.getText().toString();

        String aika = time_alku + " - " + time_loppu;

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

        Uusi_merkinta merkinta = new Uusi_merkinta(pvm,aika,laake,1,"lisätiedot");

        Merkinta_lista.getInstance().getMerkinnat().add(merkinta);


        Log.d("MSG", Merkinta_lista.getInstance().getMerkinnat().get(0).paivamaara);
        Log.d("MSG", Merkinta_lista.getInstance().getMerkinnat().get(0).aika);
        Log.d("MSG", Merkinta_lista.getInstance().getMerkinnat().get(0).laake);
    }
}
