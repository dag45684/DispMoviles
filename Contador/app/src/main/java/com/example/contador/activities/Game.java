package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contador.R;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Game extends AppCompatActivity {

    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    long valorSumaClick = 100000;
    long valorSumaAutoclick = 1;
    int valorOven = 2000;
    int nivelClick = 1;
    int nivelAutoclick = 1;
    int nivelOven = 1;
    int costemejoraClick = 100;
    int costemejoraOven = 1000000;
    int costemejoraAutoclick = 100;
    TextView counter, says, clicklevel, autoclicklevel, ovenlevel, assetfeed;
    ImageView miku;
    boolean boost = false;
    BigInteger coins;
    ScaleAnimation boing = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.BLACK : Color.parseColor("#f2f2f2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View root = findViewById(R.id.game);
        root.setBackgroundColor(bgcolor);

        assetfeed = (TextView) findViewById(R.id.asset);
        assetfeed.setTextColor(txtcolor);
        assetfeed.setBackgroundColor(assetscolor);

        counter = (TextView) findViewById(R.id.countertext);
        clicklevel = (TextView) findViewById(R.id.cliclevel);
        autoclicklevel = (TextView) findViewById(R.id.autocliclevel);
        ovenlevel = (TextView) findViewById(R.id.ovenlevel);
        says = (TextView) findViewById(R.id.mikusay);
        miku = (ImageView) findViewById(R.id.miku);
        counter.setTextColor(txtcolor);
        clicklevel.setTextColor(txtcolor);
        autoclicklevel.setTextColor(txtcolor);
        ovenlevel.setTextColor(txtcolor);
        says.setTextColor(txtcolor);
        miku = (ImageView) findViewById(R.id.miku);
        coins = new BigInteger("0");
        boing.setDuration(100);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            valorSumaClick = bundle.getLong("valorSumaClick");
            valorSumaAutoclick = bundle.getLong("valorSumaAutoclick");
            valorOven = bundle.getInt("valorOven");
            nivelClick = bundle.getInt("nivelClick");
            nivelAutoclick = bundle.getInt("nivelAutoclick");
            nivelOven = bundle.getInt("nivelOven");

            clicklevel.setText((clicklevel.getText().toString().replaceAll(" \\d+ ", "<-->")).replaceAll("<-->", ""+ nivelClick));
            autoclicklevel.setText((autoclicklevel.getText().toString().replaceAll("\\d+ ", "<-->")).replaceAll("<-->", ""+ nivelAutoclick));
            ovenlevel.setText((ovenlevel.getText().toString().replaceAll(" \\d+ ", "<-->")).replaceAll("<-->", ""+ nivelOven));
            clicklevel.setText((clicklevel.getText().toString().replaceAll("\\+\\d+", "<-->")).replaceAll("<-->", "+"+ valorSumaClick));
            autoclicklevel.setText((autoclicklevel.getText().toString().replaceAll("\\+\\d+", "<-->")).replaceAll("<-->", "+"+ valorSumaAutoclick));

            costemejoraAutoclick = bundle.getInt("costemejoraAutoclick");
            costemejoraClick = bundle.getInt("costemejoraClick");
            costemejoraOven = bundle.getInt("costemejoraOven");
            boost = bundle.getBoolean("boost");
            coins = new BigInteger(bundle.getString("coins"));
        }

        coinDisplayer();
        autoSum();
    }

    public void sum(View v) {
        counter.setTextColor(txtcolor);
        coins = coins.add(new BigInteger(Long.toString(valorSumaClick)));
        miku.startAnimation(boing);
        coinDisplayer();

    }

    public void backbutton(){
        Intent i = new Intent(this, Welcome.class);
        startActivity(i);
        finish();
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
        } else if (coins.compareTo(new BigInteger("1000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000"), 2, RoundingMode.FLOOR)).toString() + "C");
        } else if (coins.compareTo(new BigInteger("1000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "Q");
        } else if (coins.compareTo(new BigInteger("1000000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "S");
        } else if (coins.compareTo(new BigInteger("1000000000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "H");
        } else {
            counter.setText(coins.toString());
        }
    }

    public void autoSum() {
        new Thread(() -> {
            while (true) {
                coins = coins.add(new BigInteger(Long.toString(valorSumaAutoclick)));
                runOnUiThread(() -> coinDisplayer());
                try {
                    Thread.sleep(valorOven);
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }

    public void boost() {
        new Thread(() -> {
            valorSumaClick *= 2;
            valorSumaAutoclick *= 2;
            valorOven /= 2;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {}
            valorSumaClick /= 2;
            valorSumaAutoclick /= 2;
            valorOven *= 2;
        }).start();
    }

    public void gotoStore (View v){
        Intent i = new Intent(this, Upgrades.class);
        i.putExtra("valorSumaClick", valorSumaClick);
        i.putExtra("valorSumaAutoclick", valorSumaAutoclick);
        i.putExtra("valorOven", valorOven);
        i.putExtra("nivelClick", nivelClick);
        i.putExtra("nivelAutoclick", nivelAutoclick);
        i.putExtra("nivelOven", nivelOven);
        i.putExtra("costemejoraAutoclick", costemejoraAutoclick);
        i.putExtra("costemejoraClick", costemejoraClick);
        i.putExtra("costemejoraOven", costemejoraOven);
        i.putExtra("boost", boost);
        i.putExtra("coins", coins.toString());
        startActivity(i);
        finish();
    }

}