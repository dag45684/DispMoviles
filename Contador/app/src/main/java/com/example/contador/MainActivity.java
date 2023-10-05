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

public class MainActivity extends AppCompatActivity {

    TextView counter;
    TextView mikusais;
    LinearLayout lup;
    Button b;
    TextView t3;
    long addition = 100000;
    long autoSumValue = 1;
    int click_level = 1;
    int auto_level = 1;
    int costemejora = 100;
    BigInteger coins = new BigInteger("9999");
    ScaleAnimation boing = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        b = (Button) findViewById(R.id.countersum);
        mikusais = (TextView) findViewById(R.id.mikusay);
        t3 = (TextView) findViewById(R.id.t3);
        lup = (LinearLayout) findViewById(R.id.lup);
        boing.setDuration(100);
        autoSum();
    }

    public void sum(View v) {
        lup.startAnimation(boing);
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
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public void click_levelup() {
        long improvement = 10;
        if (click_level <= 10) {
            costemejora = 100;
        } else if (click_level <= 20) {
            costemejora = 1000;
            improvement += improvement+ click_level;
        } else if (click_level <= 30) {
            costemejora = 5000;
            improvement *= click_level;
        } else {
            costemejora = 100000;
            improvement = click_level * click_level;
        }
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora))) == 1){
            click_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora)));
            addition = improvement;
        }
    }

    public void auto_levelup () {
        long improvement = 1;
        if (auto_level <= 10) {
            costemejora = 100;
            improvement += auto_level;
        } else if (auto_level <= 20) {
            costemejora = 1000;
            improvement += auto_level * 2;
        } else if (auto_level <= 30) {
            costemejora = 5000;
            improvement *= auto_level;
        } else {
            costemejora = 100000;
            improvement = auto_level * 10;
        }
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora))) == 1){
            auto_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora)));
            autoSumValue = improvement;
        }
    }
}