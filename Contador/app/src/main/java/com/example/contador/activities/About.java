package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.contador.R;
import com.example.contador.utils.Info;
import com.example.contador.utils.Info_Adapter;

import java.util.Arrays;
import java.util.List;

public class About extends AppCompatActivity {

    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_info);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        List<Info> l = Arrays.asList(
                new Info("Alumno",
                        "Carlos Manso",
                        "663148797 - Oviedo, Asturias",
                        R.drawable.person),
                new Info("Centro",
                        "IES Aramo",
                        "Oviedo, Asturias",
                        R.drawable.house),
                new Info("Asignatura",
                        "PMDM",
                        "Prog. Multimedia y Disp. Moviles",
                        R.drawable.book),
                new Info("Companeros",
                        "Nuria Valdes, Abel Alonso",
                        "Ignacio Fdez.",
                        R.drawable.friends));
        rv.setAdapter(new Info_Adapter(l));

        b = getIntent().getExtras();
    }

    void back(){
        Intent i = new Intent(this, Welcome.class);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}