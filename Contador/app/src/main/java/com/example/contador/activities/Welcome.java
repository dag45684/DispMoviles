package com.example.contador.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
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

    public void popSettings(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Settings activity and dark theme configuration are still under development.")
                .setIcon(R.drawable.settings)
                .setNeutralButton("My Github", (_x, _y) -> {
                    Intent b = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cmgsk"));
                    startActivity(b);
                })
                .setNegativeButton("Quit", (_x, _y) -> finish())
                .setPositiveButton("Swap mode", (_x, _y) -> {
                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                })
                .show();
    }
    public void gotoSettings(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}