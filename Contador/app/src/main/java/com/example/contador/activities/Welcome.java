package com.example.contador.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

import java.util.ArrayList;


public class Welcome extends AppCompatActivity {

    boolean night;
    boolean devmode;
    int idPlayer = 0;
    TextView testdb;
    Bundle b;
    int bgcolor, txtcolor, assetscolor;
    Button btnExit, btnInfo, btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        DB_Handler db = new DB_Handler(this);

        //define nightmode
        night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
        txtcolor = night ? Color.CYAN : Color.BLACK;
        assetscolor = night ? Color.CYAN : Color.parseColor("#f2f2f2");

        //BG color
        View rootUpgrade = findViewById(R.id.mainscr);
        rootUpgrade.setBackgroundColor(bgcolor);

        //define elements and set them properly
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setBackgroundColor(assetscolor);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnInfo.setBackgroundColor(assetscolor);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setBackgroundColor(assetscolor);

        devmode = false;

        //unpack the bundle
        b = getIntent().getExtras();
        if (b!=null){
            idPlayer = Integer.parseInt(b.getString("idPlayer"));
            devmode = b.getBoolean("dev");
        }
    }

    //exit app
    public void gotoQuit(View v) {
        finish();
        System.exit(0);
    }

    //Start the game if youre logged. Go to login page if you're not logged
    public void gotoMainActivity(View v){
        if (idPlayer != 0) {
            Intent i = new Intent(this, Game.class);
            i.putExtra("idPlayer", idPlayer);
            i.putExtra("dev", devmode);
            i.putExtra("From", "Main");
            startActivity(i);
        }else{
            Intent i = new Intent(this, Login.class);
            startActivity(i);
            finish();
        }
    }

    //Go to info
    public void gotoInfo(View v){
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

    //pop the window for settings, github and night mode
    public void popSettings(View v) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Dark theme configuration is still under development. Sometimes it will deactivate, and usually takes two trys to activate it.")
                .setIcon(R.drawable.settings)
                .setNeutralButton("My Github", (_x, _y) -> {
                    Intent b = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cmgsk"));
                    startActivity(b);
                })
                .setNegativeButton("Settings", (_x, _y) -> {
                    Intent i = new Intent(this, Settings.class);
                    i.putExtra("idPlayer", idPlayer);
                    startActivity(i);
                    finish();
                })
                .setPositiveButton("Swap mode", (_x, _y) -> {
                    if (night){
                        night=!night;
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        night=!night;
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                })
                .show();
    }
}