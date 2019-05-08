package com.example.migreeni;

import android.content.Context;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    public static final String tag = "tagaus";
    public static final String TAG = "Ilmanpaine";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //loadData(); // Load data from Shared Preferences
        lataaPaiviaValissa();

        paivita_ilmanpaine();
        //hae_vanhaip();

    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d(tag, "onresume called");

        paivita_ilmanpaine();
    }

    /**
     * Returns the difference between two days given to it
     * Calculates the difference and divides that by the amount of milliseconds in a day
     *
     * @param one is the first given day
     * @param two is the second given day
     * @return returns the amount of days between the given days
     */
    public long paiviaValissa(Date one, Date two){

        long erotus = ((one.getTime()-two.getTime())/86400000);
        return Math.abs(erotus);
    }

    /**
     *
     */
    private void paivita_ilmanpaine() {

        SharedPreferences ilmanpaine_psharedpreferences = getSharedPreferences("ilmanpaine_sharedpreferences", MODE_PRIVATE);
        String ilpaine = ilmanpaine_psharedpreferences.getString("ilmanpaine", "0");
        Log.d(TAG, ilpaine);

        if (ilpaine != null) {
            TextView ipaine_main = findViewById(R.id.ilmanpaine_main);
            ipaine_main.setText(ilpaine);
            Log.d(TAG, ilpaine);

            //SharedPreferences ipsharedpreferences = getSharedPreferences("ipsharedpreferences", MODE_PRIVATE);
            //SharedPreferences.Editor ip_editor = ipsharedpreferences.edit();

            //ip_editor.putString("ipaine", ilpaine);
            //ip_editor.commit();
        }
        //else {
            //hae_vanhaip();
        //}
    }

    /**
     *
     */
    /*private void hae_vanhaip() {

        SharedPreferences ipsharedpreferences = getSharedPreferences("ipsharedpreferences", MODE_PRIVATE);
        String ipaine = ipsharedpreferences.getString("ipaine", "");

        TextView ipaine_main = findViewById(R.id.ilmanpaine_main);
        ipaine_main.setText(ipaine);
    }*/


    public void kalenteri_icon(View view) {
        Intent intent = new Intent(this, kalenteri.class);
        startActivity(intent);
    }

    public void saa_icon(View view) {
        Intent intent = new Intent(this, koordinaatit.class);
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

    /**
     *  Finds the day of the last entry and the current day
     *  Takes the days to long paiviaValissa to calculate the difference
     *  Returns the result to be shown in a textView
     *
     */
    public void lataaPaiviaValissa(){

        Calendar kal = Calendar.getInstance();
        String paivamaara = "";
        kal.set(2019, 4, 10);

        int index = Merkinta_lista.getInstance().getMerkinnat().size();
        index--;
        if(index >= 0){
            paivamaara = Merkinta_lista.getInstance().getMerkinnat().get(index).getPaivamaara();
        }
        Date viime_merkinta_paiva = kal.getTime();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
            viime_merkinta_paiva = formatter.parse(paivamaara);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date tanaan = new Date();
        MainActivity obj = new MainActivity();

        long paivat = obj.paiviaValissa(tanaan, viime_merkinta_paiva);

        TextView tvPaivia = findViewById(R.id.viime_merkinta_arvo);
        tvPaivia.setText(String.valueOf(paivat));
    }

    /**
     *  Finds the current date and time
     *  Creates an object with those and adds it to the listView
     *
     */
    public void pikaMerkinta(View view){
        Date date = new Date();
        DateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String tanaan = format1.format(date);

        Date time = Calendar.getInstance().getTime();
        DateFormat format2 = new SimpleDateFormat("HH:mm:ss");
        String aika = format2.format(time);

        Uusi_merkinta pika = new Uusi_merkinta(tanaan, aika, "","","Pikamerkintä");
        Merkinta_lista.getInstance().getMerkinnat().add(pika);

        Toast.makeText(this,"Merkintä tallennettu",Toast.LENGTH_LONG).show();
    }

}