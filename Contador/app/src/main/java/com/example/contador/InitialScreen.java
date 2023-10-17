package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InitialScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);
    }

    public void gotoQuit(View v) {
        finish();
    }
    public void gotoMainActivity(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void gotoInfo(View v){
        Intent i = new Intent(this, info.class);
        startActivity(i);
    }
    public void gotoSettings(View v){
        Intent i = new Intent(this, settings.class);
        startActivity(i);
    }
}