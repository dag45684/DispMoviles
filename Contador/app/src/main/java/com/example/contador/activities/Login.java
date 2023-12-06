package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    Switch isnew;
    EditText name;
    EditText pass;
    EditText confirm;
    TextView errormsg;
    boolean newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        confirm = (EditText) findViewById(R.id.confirm);
        confirm.setVisibility(View.INVISIBLE);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        errormsg = (TextView) findViewById(R.id.err);

        isnew = (Switch) findViewById(R.id.isnew);
        isnew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                confirm.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
                newuser = isChecked;
            }

        });
    }

    public void gotoBack (){
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
        finish();
    }

    public void LogIn_SignUp(){

        DB_Handler db = new DB_Handler(this);

        if (!newuser && (db.readFromDB(String.format("nombre = '%s'", name.getText().toString())).size() == 0)){
            errormsg.setText("El usuario no existe");
            return;
        }
        if ((db.readFromDB(String.format("nombre = '%s'", name.getText().toString())).size() > 0)){
            if(newuser){
                errormsg.setText("El nombre de usuario ya existe.");
                return;
            }else {
                ArrayList<String> l = db.readFromDB(String.format("nombre = '%s'", name.getText().toString()));
                if (l.get(0).split(" | ")[0].equals(name.getText().toString()) && l.get(0).split(" | ")[1].equals(pass.getText().toString())){
                    Intent i = new Intent(this, Welcome.class);
                    i.putExtra("idPlayer", l.get(0).split(" | ")[3]);
                    startActivity(i);
                    finish();
                }else{
                    errormsg.setText("Password incorrecta");
                    return;
                }
            }
        }
        if(!confirm.getText().toString().equals(pass.getText().toString()) && newuser){
            errormsg.setText("La password no coincide");
        } else {
            db.addNewToDB(name.getText().toString(), pass.getText().toString());
        }
    }



}