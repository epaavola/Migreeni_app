package com.example.migreeni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class vanhat_merkinnat_avattuinfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vanhat_merkinnat_avattuinfo);

        Bundle b = getIntent().getExtras();
        int position = b.getInt(vanhat_merkinnat.EXTRA, 0);

        //esim. String nimi = juttuLista.getInstance().getJutut().get(position).getNimi();
        String muuttuja = "muuttuja";

        TextView tv = findViewById(R.id.vanhat_merkinnat_info);
        tv.setText(muuttuja);

    }
}
