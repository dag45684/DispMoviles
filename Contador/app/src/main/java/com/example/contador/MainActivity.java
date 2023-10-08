package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    long addition = 100000;
    long autoSumValue = 1;
    int ovenspeed = 2000;
    int click_level = 1;
    int auto_level = 1;
    int oven_level = 1;
    int costemejora_click = 100;
    int costemejora_oven = 1000000;
    int costemejora_auto = 100;
    TextView counter, lvac, lvc, lvo, says, clicklevel, autoclicklevel, ovenlevel;
    ImageView miku;
    boolean boost = false;
    BigInteger coins = new BigInteger("9999");
    ScaleAnimation boing = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        lvac = (TextView) findViewById(R.id.lvac);
        lvc = (TextView) findViewById(R.id.lvc);
        lvo = (TextView) findViewById(R.id.lvo);
        clicklevel = (TextView) findViewById(R.id.cliclevel);
        autoclicklevel = (TextView) findViewById(R.id.autocliclevel);
        ovenlevel = (TextView) findViewById(R.id.ovenlevel);
        says = (TextView) findViewById(R.id.mikusay);
        miku = (ImageView) findViewById(R.id.miku);
        boing.setDuration(100);
        autoSum();
    }

    public void sum(View v) {
        counter.setTextColor(Color.rgb(0, 0, 0));
        coins = coins.add(new BigInteger(Long.toString(addition)));
        //yummy();
        miku.startAnimation(boing);
        coinDisplayer();

    }

    public void coinDisplayer() {
        if (coins.compareTo(new BigInteger("1000")) == 1 && coins.compareTo(new BigInteger("1000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000"), 2, RoundingMode.FLOOR)).toString() + "k");
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

    public void boost() {
        new Thread(() -> {
            addition *= 2;
            autoSumValue *= 2;
            ovenspeed /= 2;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}
            addition /= 2;
            autoSumValue /= 2;
            ovenspeed *= 2;
        }).start();
    }

    //idk how to do this
    public void yummy(){
        Random rnd = new Random();
        float rndx = rnd.nextFloat();
        float rndy = rnd.nextFloat();
        new Thread(() -> {
            runOnUiThread(() -> {
                says.setX(rndx); //check how to implement this
                says.setY(rndy);
                says.setAlpha(1);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {}
                says.setAlpha(0);
            });
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
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_click))) == 1){
            click_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_click)));
            if (click_level <= 10) {
                costemejora_click = 100;
            } else if (click_level <= 20) {
                costemejora_click = 1000;
                improvement += improvement+ click_level;
            } else if (click_level <= 30) {
                costemejora_click = 500000;
                improvement *= click_level;
            } else {
                costemejora_click = 10000000;
                improvement = click_level * click_level;
            }
            addition += improvement;
            lvc.setText(lvc.getText().toString().replaceAll("\\d+", Integer.toString(costemejora_click)));
            clicklevel.setText(clicklevel.getText().toString().replaceAll("[+]\\d+", "+"+addition));
            clicklevel.setText(clicklevel.getText().toString().replaceAll("level: \\d+", "level: "+click_level));
        }
    }

    public void auto_levelup () {
        long improvement = 1;
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_auto))) == 1){
            auto_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_auto)));
            if (auto_level <= 10) {
                costemejora_auto = 100;
                improvement += auto_level;
            } else if (auto_level <= 20) {
                costemejora_auto = 1000;
                improvement += auto_level * 2;
            } else if (auto_level <= 30) {
                costemejora_auto = 50000;
                improvement *= auto_level;
            } else {
                costemejora_auto = 1000000;
                improvement = auto_level * 12;
            }
            autoSumValue += improvement;
            lvac.setText(lvac.getText().toString().replaceAll("\\d+", Integer.toString(costemejora_auto)));
            autoclicklevel.setText(autoclicklevel.getText().toString().replaceAll("[+]\\d+", "+"+autoSumValue));
            autoclicklevel.setText(autoclicklevel.getText().toString().replaceAll("level: \\d+", "level: "+auto_level));
        }
    }

    public void oven_levelup (){
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_oven))) == 1){
            if (ovenspeed > 200){
                coins = coins.subtract(new BigInteger(Integer.toString(costemejora_oven)));
                ovenspeed -= 200;
                oven_level++;
                ovenlevel.setText(ovenlevel.getText().toString().replaceAll("\\d+", Integer.toString(oven_level)));
            }
            else {
               //gray button here and set text to max
                ovenlevel.setText("Oven  level: Max.");
            }
        }
    }

}