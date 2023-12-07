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

/*
DB DATA LOADING:
Returns: ArrayList of each row separated by " | "
on readFromDB(filter) or data corresponds to:
id: .get(n).split(" | ")[0]
name: .get(n).split(" | ")[1]
pass: .get(n).split(" | ")[2]
score: .get(n).split(" | ")[3]
suma: .get(n).split(" | ")[4]
autosuma: .get(n).split(" | ")[5]
oven: .get(n).split(" | ")[6]
cliclvl: .get(n).split(" | ")[7]
autolvl: .get(n).split(" | ")[8]
ovenlvl: .get(n).split(" | ")[9]
costeclick: .get(n).split(" | ")[10]
costeauto: .get(n).split(" | ")[11]
 */

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

        night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
        bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
        txtcolor = night ? Color.CYAN : Color.BLACK;
        assetscolor = night ? Color.CYAN : Color.parseColor("#f2f2f2");

        View rootUpgrade = findViewById(R.id.mainscr);
        rootUpgrade.setBackgroundColor(bgcolor);

        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setBackgroundColor(assetscolor);
        btnInfo = (Button) findViewById(R.id.btnInfo);
        btnInfo.setBackgroundColor(assetscolor);
        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setBackgroundColor(assetscolor);

        devmode = false;

        b = getIntent().getExtras();
        if (b!=null){
            idPlayer = Integer.parseInt(b.getString("idPlayer"));
            devmode = b.getBoolean("dev");
        }
    }

    public void gotoQuit(View v) {
        finish();
        System.exit(0);
    }

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

    public void gotoInfo(View v){
        Intent i = new Intent(this, About.class);
        startActivity(i);
    }

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