package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView mikusais;
    LinearLayout lup;
    Button b;
    TextView t3;
    int addition = 100000;
    int level = 1;
    int costemejora = 100;
    BigInteger coins = new BigInteger("9999");
    BigInteger autoSumValue = new BigInteger("1");
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
        autoSum();
    }

    public void sum(View v){
        lup.startAnimation(fade_in);
        counter.setTextColor(Color.rgb(0,0,0));
        coins.add(new BigInteger(Integer.toString(addition)));
        if(coins.compareTo(new BigInteger("1000")) == 1 && coins.compareTo(new BigInteger("1000000")) != 1){
//            counter.setText(Double.toString(coins/1000d).substring(0,3) + "Tho");
            //TODO: THIS DOESNT WORK. JUST IMPLEMENT A FUCKING STRING ADDITION FOR THE COMMA THEN TRIM THE UNDESIRED SHIT.
//            counter.setText(new BigDecimal(coins.divide(new BigInteger("1000"))).toString());
            counter.setText(coins.toString().substring(0,1) + "," + coins.toString().substring(2,4) + "Tho");
        } else if (coins.compareTo(new BigInteger("1000000")) == 1 && coins.compareTo(new BigInteger("1000000000")) != 1) {
//            counter.setText(Double.toString(coins/1000000d).substring(0,4) + "M");
//            counter.setText(new BigDecimal(coins.divide(new BigInteger("1000000"))).toString());
            counter.setText(coins.toString().substring(0,2) + "," + coins.toString().substring(3,4) + "M");
        } else if (coins.compareTo(new BigInteger("1000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000")) != 1) {
//            counter.setText(Double.toString(coins/1000000000d).substring(0,4) + "B");
//            counter.setText(new BigDecimal(coins.divide(new BigInteger("1000000000"))).toString());
        } else if (coins.compareTo(new BigInteger("1000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000")) != 1) {
//            counter.setText(Double.toString(coins/1000000000d).substring(0,4) + "T");
//            counter.setText(new BigDecimal(coins.divide(new BigInteger("1000000000000"))).toString());
        }else{
            counter.setText(coins.toString());
        }
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora))) == -1){
            lup.setEnabled(false);
            lup.setBackgroundColor(Color.rgb(220,220,220));
        }else{
            lup.setEnabled(true);
//            lup.setBackgroundColor(Color.rgb(255,200,180));
//            for (int i=2; i<=((int)coins/2)+1; i++){
//                if (coins%i == 0 && coins!=2){
//                    counter.setTextColor(Color.rgb(0,0,0));
//                    break;
//                }else counter.setTextColor(Color.rgb(255,0,0));
//            }
        }
    }

    public void autoSum (){
        new Thread(() -> {
            while(true){
                coins = coins.add(autoSumValue);
                runOnUiThread(() -> counter.setText(coins.toString()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
            }
        }).start();
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