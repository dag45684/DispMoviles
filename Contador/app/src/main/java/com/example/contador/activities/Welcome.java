package com.example.contador.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO;
    int idPlayer = 0;
    TextView testdb;
    Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_screen);

        DB_Handler db = new DB_Handler(this);

        b = getIntent().getExtras();
        if (b!=null){
            idPlayer = Integer.parseInt(b.getString("idPlayer"));
        }


    }

    public void gotoQuit(View v) {
        finish();
    }
    public void gotoMainActivity(View v){
        if (idPlayer != 0) {
            Intent i = new Intent(this, Game.class);
            i.putExtra("idPlayer", idPlayer);
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
        dialog.setMessage("Settings activity and dark theme configuration are still under development.")
                .setIcon(R.drawable.settings)
                .setNeutralButton("My Github", (_x, _y) -> {
                    Intent b = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cmgsk"));
                    startActivity(b);
                })
                .setNegativeButton("Quit", (_x, _y) -> finish())
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
    public void gotoSettings(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }
}