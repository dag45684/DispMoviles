package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView mikusais;
    LinearLayout lup;
    Button b;
    TextView t3;
    int addition = 1;
    int level = 1;
    int costemejora = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        b = (Button) findViewById(R.id.countersum);
        mikusais = (TextView) findViewById(R.id.mikusay);
        t3 = (TextView) findViewById(R.id.t3);
        lup = (LinearLayout) findViewById(R.id.lup);
    }

    public void sum(View v){
        counter.setTextColor(Color.rgb(0,0,0));
        int n = Integer.parseInt(counter.getText().toString()) + addition;
        counter.setText(Integer.toString(n));
        if(n < costemejora){
            lup.setEnabled(false);
            lup.setBackgroundColor(Color.rgb(220,220,220));
        }else{
            lup.setEnabled(true);
            lup.setBackgroundColor(Color.rgb(255,200,180));
            for (int i=2; i<=((int)n/2)+1; i++){
                if (n%i == 0 && n!=2){
                    counter.setTextColor(Color.rgb(0,0,0));
                    break;
                }else counter.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

    public void levelup(View v){
        if(level <= 3){
            if(Integer.parseInt(counter.getText().toString()) >= costemejora){
                level++;
                b.setEnabled(true);
                counter.setTextColor(Color.rgb(0,0,0));
                counter.setText("" + (Integer.parseInt(counter.getText().toString()) - costemejora));
                addition += 1;
                mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(addition-1));
                b.setText("+" + Integer.toString(addition));
            }
        }else if (level <= 5){
            costemejora=200;
            if(Integer.parseInt(counter.getText().toString()) >= costemejora) {
                level++;
                b.setEnabled(true);
                counter.setTextColor(Color.rgb(0, 0, 0));
                counter.setText("" + (Integer.parseInt(counter.getText().toString()) - costemejora));
                addition *= 2;
                mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(level));
                b.setText("+" + Integer.toString(addition));
                t3.setText("200 puntos");
            }else if (level <= 9) {
                costemejora = 500;
                if (Integer.parseInt(counter.getText().toString()) >= costemejora) {
                    level++;
                    b.setEnabled(true);
                    counter.setTextColor(Color.rgb(0, 0, 0));
                    counter.setText("" + (Integer.parseInt(counter.getText().toString()) - costemejora));
                    addition *= addition;
                    mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(level));
                    b.setText("+" + Integer.toString(addition));
                    t3.setText("500 puntos");
                }
            }
        }
    }
}