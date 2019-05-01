package com.example.migreeni;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class merkinta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merkinta);

         int resID = getResources().getIdentifier("pick_date",
                "id", getPackageName());

        PVM_picker set_date = new PVM_picker(this, resID);
    }

    public void tallenna_merkinta(View view){

        final EditText text =  findViewById(R.id.pick_date);
        String pvm = text.getText().toString();

        Uusi_merkinta merkinta = new Uusi_merkinta(pvm,"12:00 - 15:00","Parasetamoli",1,"lisätiedot");

        Merkinta_lista.getInstance().getMerkinnat().add(merkinta);

        for (int i = 0; i< Merkinta_lista.getInstance().getMerkinnat().size(); i++){
            Log.d("MSG", Merkinta_lista.getInstance().getMerkinnat().get(i).paivamaara);
        }

    }
}
