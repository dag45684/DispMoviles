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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    long addition = 100000;
    long autoSumValue = 1;
    int ovenspeed = 2000;
    int click_level = 1;
    int auto_level = 1;
    int oven_level = 1;
    int costemejora_click = 100;
    int costemejora_oven = 100;
    int costemejora_auto = 100;
    TextView says;
    BigInteger coins = new BigInteger("9999");
    ScaleAnimation boing = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        says = (TextView) findViewById(R.id.mikusay);
        boing.setDuration(100);
        autoSum();
    }

    public void sum(View v) {
        counter.setTextColor(Color.rgb(0, 0, 0));
        coins = coins.add(new BigInteger(Long.toString(addition)));
        coinDisplayer();
    }

    public void coinDisplayer() {
        if (coins.compareTo(new BigInteger("1000")) == 1 && coins.compareTo(new BigInteger("1000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000"), 2, RoundingMode.FLOOR)).toString() + "Th");
        } else if (coins.compareTo(new BigInteger("1000000")) == 1 && coins.compareTo(new BigInteger("1000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000"), 2, RoundingMode.FLOOR)).toString() + "M");
        } else if (coins.compareTo(new BigInteger("1000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000"), 2, RoundingMode.FLOOR)).toString() + "B");
        } else if (coins.compareTo(new BigInteger("1000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000"), 2, RoundingMode.FLOOR)).toString() + "T");
        } else {
            counter.setText(coins.toString());
        }
    }

    public void autoSum() {
        new Thread(() -> {
            while (true) {
                coins = coins.add(new BigInteger(Long.toString(autoSumValue)));
                runOnUiThread(() -> coinDisplayer());
                try {
                    Thread.sleep(ovenspeed);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public void yummy(){
        Random rnd = new Random();
        int rndx = rnd.nextInt();
        int rndy = rnd.nextInt();
        new Thread(() -> {
            says.setX((float) rndx);
            says.setY((float) rndy);
            while(true){
                says.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
                says.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    public void levelup (View v){
        switch (v.getTag().toString()){
            case "1":
                click_levelup();
                break;
            case "2":
                auto_levelup();
                break;
            case "3":
                oven_levelup();
                break;
        }
    }

    public void click_levelup() {
        long improvement = 10;
        if (click_level <= 10) {
            costemejora_click = 100;
        } else if (click_level <= 20) {
            costemejora_click = 1000;
            improvement += improvement+ click_level;
        } else if (click_level <= 30) {
            costemejora_click = 5000;
            improvement *= click_level;
        } else {
            costemejora_click = 100000;
            improvement = click_level * click_level;
        }
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_click))) == 1){
            click_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_click)));
            addition = improvement;
        }
    }

    public void auto_levelup () {
        long improvement = 1;
        if (auto_level <= 10) {
            costemejora_auto = 100;
            improvement += auto_level;
        } else if (auto_level <= 20) {
            costemejora_auto = 1000;
            improvement += auto_level * 2;
        } else if (auto_level <= 30) {
            costemejora_auto = 5000;
            improvement *= auto_level;
        } else {
            costemejora_auto = 100000;
            improvement = auto_level * 10;
        }
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_auto))) == 1){
            auto_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_auto)));
            autoSumValue = improvement;
        }
    }

    public void oven_levelup (){
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_oven))) == 1){
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_oven)));
            if (ovenspeed > 200){
                ovenspeed -= 200;
            }
        }
    }

}