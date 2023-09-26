package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView mikusais;
    Button b;
    int addition = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        b = (Button) findViewById(R.id.countersum);
        mikusais = (TextView) findViewById(R.id.mikusay);
    }

    public void sum(View v){
        counter.setTextColor(Color.rgb(0,0,0));
        int n = Integer.parseInt(counter.getText().toString()) + addition;
        counter.setText(Integer.toString(n));
        if(n >= 120){
//           b.setEnabled(false);
//           counter.setText("Hurry and level up!");
//           counter.setTextSize(20);
        }else{
            for (int i=2; i<=((int)n/2)+1; i++){
                if (n%i == 0 && n!=2){
                    counter.setTextColor(Color.rgb(0,0,0));
                    break;
                }else counter.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

    public void levelup(View v){
        if(Integer.parseInt(counter.getText().toString()) >= 100){
            b.setEnabled(true);
            counter.setTextColor(Color.rgb(0,0,0));
            counter.setText("" + (Integer.parseInt(counter.getText().toString()) - 100));
            addition += 1;
            mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(addition-1));
            b.setText("+" + Integer.toString(addition));
        }
        else{

        }


    }

}