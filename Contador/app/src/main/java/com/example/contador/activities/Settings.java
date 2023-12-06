package com.example.contador.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    DB_Handler db;
    int idPlayer;
    Bundle bundle;
    TextView t;
    Button s1, s2, s3, s4, s5;
    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.CYAN : Color.parseColor("#f2f2f2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        View rootUpgrade = findViewById(R.id.settings);
        rootUpgrade.setBackgroundColor(bgcolor);

        t = (TextView) findViewById(R.id.setView) ;
        t.setTextColor(txtcolor);

        bundle = getIntent().getExtras();
        idPlayer = bundle.getInt("idPlayer");

        s1 = (Button) findViewById(R.id.set1);
        s1.setBackgroundColor(assetscolor);
        s2 = (Button) findViewById(R.id.set2);
        s2.setBackgroundColor(assetscolor);
        s3 = (Button) findViewById(R.id.set3);
        s3.setBackgroundColor(assetscolor);
        s4 = (Button) findViewById(R.id.set4);
        s4.setBackgroundColor(assetscolor);
        s5 = (Button) findViewById(R.id.set5);
        s5.setBackgroundColor(assetscolor);
    }

    public void logout(View v){
        if(idPlayer != 0){
            Intent i = new Intent(this, Welcome.class);
            i.putExtra("idPlayer", "0");
            startActivity(i);
            finish();
        }else t.setText("No account detected");
    }

    public void deletedb(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete the entire database?")
                .setPositiveButton("Yes", (_x, _y) -> {
                    db = new DB_Handler(this);
                    db.delal();
                    t.setText("Database has been wiped and restored");
                    idPlayer = 0;
                })
                .setNegativeButton("No", (_x, _y) -> {return;} )
                .show();
    }

    public void deleteacc(View v){
        if (idPlayer != 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you want to delete the current account and all its progress?")
                    .setPositiveButton("Yes", (_x, _y) -> {
                        db = new DB_Handler(this);
                        db.rawUpdate(String.format("DELETE FROM Jugadores WHERE id = '%s'", idPlayer));
                        Intent i = new Intent(this, Welcome.class);
                        i.putExtra("idPlayer", "0");
                        startActivity(i);
                        finish();
                    })
                    .setNegativeButton("No", (_x, _y) -> {return;} )
                    .show();
        }else t.setText("No account detected");
    }

    public void getacc(View v){
        if(idPlayer!=0){
            db = new DB_Handler(this);
            String[] infos = {"Id: ", "Name: ", "Score: ", "Value click: ", "Value AutoClick: ", "Tick Timer Oven: ", "Click level: ", "AutoClick Level: ", "Oven Level: "};
            ArrayList<String> l = db.readFromDB("id="+idPlayer);
            String[] data = l.get(0).split("\\s\\|\\s");
            StringBuilder sb = new StringBuilder();
            for (int i=0; i<infos.length; i++){
                sb.append(infos[i]+data[i]+'\n');
            }
            t.setText(sb.toString());
        } else t.setText("No account detected");
    }

    public void getn(View v){
        db = new DB_Handler(this);
        t.setText(db.bitFromDB("SELECT COUNT(id) FROM Jugadores") == null ? "No users registered yet" : db.bitFromDB("SELECT Count(*) FROM Jugadores"));
    }

    public void back(View v){
        Intent i = new Intent(this, Welcome.class);
        i.putExtra("idPlayer", ""+idPlayer);
        startActivity(i);
        finish();
    }

}