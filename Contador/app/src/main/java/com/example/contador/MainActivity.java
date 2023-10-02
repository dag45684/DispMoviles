package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView mikusais;
    LinearLayout lup;
    Button b;
    TextView t3;
    int addition = 100000;
    int level = 1;
    int costemejora = 100;
    int coins=9999;
    ScaleAnimation fade_in = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        b = (Button) findViewById(R.id.countersum);
        mikusais = (TextView) findViewById(R.id.mikusay);
        t3 = (TextView) findViewById(R.id.t3);
        lup = (LinearLayout) findViewById(R.id.lup);
        fade_in.setDuration(100);
    }

    public void sum(View v){
        lup.startAnimation(fade_in);
        counter.setTextColor(Color.rgb(0,0,0));
        coins += addition;
        if(coins>=1000 && coins<1000000){
            counter.setText(Double.toString(coins/1000d).substring(0,3) + "Mil");
        } else if (coins >= 1000000 && coins<1000000000) {
            counter.setText(Double.toString(coins/1000000d).substring(0,4) + "M");
        } else if (coins >= 1000000000) {
            counter.setText(Double.toString(coins/1000000000d).substring(0,4) + "B");
        }else{
            counter.setText(Integer.toString(coins));
        }
        if(coins < costemejora){
            lup.setEnabled(false);
            lup.setBackgroundColor(Color.rgb(220,220,220));
        }else{
            lup.setEnabled(true);
            lup.setBackgroundColor(Color.rgb(255,200,180));
            for (int i=2; i<=((int)coins/2)+1; i++){
                if (coins%i == 0 && coins!=2){
                    counter.setTextColor(Color.rgb(0,0,0));
                    break;
                }else counter.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

    public void levelup(View v) {
        if (level <= 3) {
            if (Integer.parseInt(counter.getText().toString()) >= costemejora) {
                level++;
                b.setEnabled(true);
                counter.setTextColor(Color.rgb(0, 0, 0));
                counter.setText("" + (Integer.parseInt(counter.getText().toString()) - costemejora));
                addition += 1;
                mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(addition - 1));
                b.setText("+" + Integer.toString(addition));
            }
        } else if (level <= 5) {
            costemejora = 200;
            if (Integer.parseInt(counter.getText().toString()) >= costemejora) {
                level++;
                b.setEnabled(true);
                counter.setTextColor(Color.rgb(0, 0, 0));
                counter.setText("" + (Integer.parseInt(counter.getText().toString()) - costemejora));
                addition *= 2;
                mikusais.setText("Tap me to upgrade \n Current level: " + Integer.toString(level));
                b.setText("+" + Integer.toString(addition));
                t3.setText("200 puntos");
            } else if (level <= 9) {
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