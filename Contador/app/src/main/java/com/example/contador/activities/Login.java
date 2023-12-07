package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

import java.util.ArrayList;
import java.util.Arrays;

public class Login extends AppCompatActivity {

    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.BLACK : Color.parseColor("#f2f2f2");
    Switch isnew;
    EditText name;
    EditText pass;
    EditText confirm;
    TextView errormsg;
    Button go;
    boolean newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View root = findViewById(R.id.logscreen);
        root.setBackgroundColor(bgcolor);

        confirm = (EditText) findViewById(R.id.confirm);
        confirm.setVisibility(View.INVISIBLE);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        errormsg = (TextView) findViewById(R.id.err);
        go = (Button) findViewById(R.id.okbutton);
        isnew = (Switch) findViewById(R.id.isnew);

        confirm.setBackgroundColor(txtcolor);
        pass.setBackgroundColor(txtcolor);
        name.setBackgroundColor(txtcolor);
        confirm.setTextColor(assetscolor);
        pass.setTextColor(assetscolor);
        name.setTextColor(assetscolor);
        isnew.setTextColor(Color.BLACK);
        go.setBackgroundColor(assetscolor);
        go.setTextColor(txtcolor);
        errormsg.setTextColor(txtcolor);

        isnew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                confirm.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                newuser = isChecked;
            }
        });
    }

    public void gotoBack (View v){
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
        finish();
    }

    public void LogIn_SignUp(View v){
        DB_Handler db = new DB_Handler(this);

        //try login -> no user
        if (!newuser && (db.readFromDB(String.format("nombre = '%s'", name.getText().toString())).size() == 0)){
            errormsg.setText("El usuario no existe");
            return;
        }
        //try login or register
        if ((db.readFromDB(String.format("nombre = '%s'", name.getText().toString())).size() > 0)){
            //try register -> name taken
            if(newuser){
                errormsg.setText("El nombre de usuario ya existe.");
                return;
            //try login -> name available
            }else {
                ArrayList<String> l = db.readFromDB(String.format("nombre = '%s'", name.getText().toString()));
                Log.i("semen", l.get(0));
                if (l.get(0).split("\\s\\|\\s")[1].equals(name.getText().toString()) && l.get(0).split("\\s\\|\\s")[2].equals(pass.getText().toString())){
                    Intent i = new Intent(this, Welcome.class);
                    i.putExtra("idPlayer", l.get(0).split("\\s\\|\\s")[0]);
                    startActivity(i);
                    finish();
                //try login -> wrong password
                }else{
                    errormsg.setText("Password incorrecta");
                    Log.i("semen", Arrays.toString(l.get(0).split("\\s\\|\\s")));
                    return;
                }
            }
        }
        //try register -> password doesnt match
        if(!confirm.getText().toString().equals(pass.getText().toString()) && newuser){
            errormsg.setText("La password no coincide");
        //try register -> success
        } else {
            db.newEntry(name.getText().toString(), pass.getText().toString());
            Intent i = new Intent(this, Welcome.class);
            ArrayList<String> l = db.readFromDB(String.format("nombre = '%s'", name.getText().toString()));
            if (l.size() > 0){
                String temp = l.get(0).split("\\s\\|\\s")[0];
                i.putExtra("idPlayer", temp);
                startActivity(i);
                finish();
            }
        }
    }
}