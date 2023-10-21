package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.contador.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
    }

    public void gotoQuit(View v) {
        finish();
    }
    public void gotoMainActivity(View v){
        Intent i = new Intent(this, Game.class);
        startActivity(i);
    }
    public void gotoInfo(View v){
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }
    public void gotoSettings(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}