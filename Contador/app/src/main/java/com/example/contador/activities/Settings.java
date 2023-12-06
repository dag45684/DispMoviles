package com.example.contador.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

public class Settings extends AppCompatActivity {

    int idPlayer;
    Bundle bundle;
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        t = (TextView) findViewById(R.id.setView) ;

        bundle = getIntent().getExtras();
        idPlayer = bundle.getInt("idPlayer");
    }

    public void logout(View v){
        if(idPlayer != 0){
            Intent i = new Intent(this, Welcome.class);
            i.putExtra("playerId", 0);
            startActivity(i);
            finish();
        }else t.setText("No account detected");
    }

    public void deletedb(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete the entire database?")
                .setPositiveButton("Yes", (_x, _y) -> {
                    DB_Handler db = new DB_Handler(this);
                    db.delal();
                    Intent i = new Intent(this, Welcome.class);
                    i.putExtra("playerId", 0);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("No", (_x, _y) -> {return;} )
                .show();
    }

    public void deleteacc(View v){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to delete the entire database?")
                .setPositiveButton("Yes", (_x, _y) -> {
                    DB_Handler db = new DB_Handler(this);
                    db.rawUpdate(String.format("DELETE FROM Jugadores WHERE id = '%s'", idPlayer));
                    Intent i = new Intent(this, Welcome.class);
                    i.putExtra("playerId", 0);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("No", (_x, _y) -> {return;} )
                .show();
    }

    public void getacc(View v){

    }

}