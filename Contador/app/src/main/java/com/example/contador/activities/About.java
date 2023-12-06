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
import com.example.contador.utils.Info;
import com.example.contador.utils.Info_Adapter;

import java.util.Arrays;
import java.util.List;

public class About extends AppCompatActivity {

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

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_info);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<Info> l = Arrays.asList(
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
        rv.setAdapter(new Info_Adapter(l));
    }

    void back(View v){
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
        finish();
    }
}