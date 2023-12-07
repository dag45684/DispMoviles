package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;
import com.example.contador.utils.Info;
import com.example.contador.utils.Info_Adapter;
import com.example.contador.utils.Leader;
import com.example.contador.utils.Leader_Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class About extends AppCompatActivity {

    DB_Handler db;
    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.CYAN : Color.parseColor("#f2f2f2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        View rootUpgrade = findViewById(R.id.infos);
        rootUpgrade.setBackgroundColor(bgcolor);

        db = new DB_Handler(this);

        RecyclerView rv_i = (RecyclerView) findViewById(R.id.recycler_info);
        rv_i.setHasFixedSize(true);
        rv_i.setLayoutManager(new LinearLayoutManager(this));
        List<Info> li = Arrays.asList(
                new Info("Alumno",
                        "Carlos Manso",
                        "663148797 - Oviedo, Asturias",
                        R.drawable.person,
                        assetscolor),
                new Info("Centro",
                        "IES Aramo",
                        "Oviedo, Asturias",
                        R.drawable.house,
                        assetscolor),
                new Info("Asignatura",
                        "PMDM",
                        "Prog. Multimedia y Disp. Moviles",
                        R.drawable.book,
                        assetscolor),
                new Info("Companeros",
                        "Nuria Valdes, Abel Alonso",
                        "Ignacio Fdez.",
                        R.drawable.friends,
                        assetscolor));

        RecyclerView rv_l = (RecyclerView) findViewById(R.id.recycler_score);
        rv_l.setHasFixedSize(true);
        rv_l.setLayoutManager(new LinearLayoutManager(this));
        List<Leader> ll = new ArrayList<Leader>();
        ArrayList<String> leads = db.readFromDB("1=1 ORDER BY CAST(Score as INTEGER) DESC");
        int limit = leads.size() < 7 ? leads.size() : 7;
        for(int i=0; i < limit; i++){
            ll.add(new Leader(leads.get(i), i, assetscolor));
        }

        rv_i.setAdapter(new Info_Adapter(li));
        rv_l.setAdapter(new Leader_Adapter(ll));

        rv_i.setBackgroundColor(Color.parseColor("#888888"));
        rv_l.setBackgroundColor(Color.parseColor("#888888"));
    }

    void back(View v){
        finish();
    }
}