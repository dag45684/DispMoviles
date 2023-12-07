package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.DB_Handler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;


public class Game extends AppCompatActivity {

    DB_Handler db;
    boolean devmode;
    int idPlayer;
    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.BLACK : Color.parseColor("#f2f2f2");
    long valorSumaClick = 1;
    long valorSumaAutoclick = 1;
    int valorOven = 2000;
    int nivelClick = 1;
    int nivelAutoclick = 1;
    int nivelOven = 1;
    int costemejoraClick = 100;
    int costemejoraOven = 1000000;
    int costemejoraAutoclick = 100;
    TextView counter, says, clicklevel, autoclicklevel, ovenlevel, assetfeed, playingAs;
    ImageView miku;
    boolean boost = false;
    BigInteger coins;
    ScaleAnimation boing = new ScaleAnimation(0.7f, 1.2f, 0.7f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB_Handler(this);

        //BG Color
        View root = findViewById(R.id.game);
        root.setBackgroundColor(bgcolor);

        //Initialization
        devmode = false;
        coins = new BigInteger("0");
        boing.setDuration(100);

        //Defining elements
        assetfeed = (TextView) findViewById(R.id.asset);
        miku = (ImageView) findViewById(R.id.miku);
        counter = (TextView) findViewById(R.id.countertext);
        clicklevel = (TextView) findViewById(R.id.cliclevel);
        autoclicklevel = (TextView) findViewById(R.id.autocliclevel);
        ovenlevel = (TextView) findViewById(R.id.ovenlevel);
        says = (TextView) findViewById(R.id.mikusay);
        playingAs = (TextView) findViewById(R.id.PlayingAs);
        miku = (ImageView) findViewById(R.id.miku);

        //Configuring elements
        assetfeed.setTextColor(txtcolor);
        assetfeed.setBackgroundColor(assetscolor);
        counter.setTextColor(txtcolor);
        clicklevel.setTextColor(txtcolor);
        autoclicklevel.setTextColor(txtcolor);
        ovenlevel.setTextColor(txtcolor);
        says.setTextColor(txtcolor);

        //Unpacking bundle
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null){
            //Comes from the shop
            if(!bundle.getString("From").equals("Main")){
                idPlayer = bundle.getInt("idPlayer");
                valorSumaClick = bundle.getLong("valorSumaClick");
                valorSumaAutoclick = bundle.getLong("valorSumaAutoclick");
                valorOven = bundle.getInt("valorOven");
                nivelClick = bundle.getInt("nivelClick");
                nivelAutoclick = bundle.getInt("nivelAutoclick");
                nivelOven = bundle.getInt("nivelOven");
                boost = bundle.getBoolean("boost");

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
            //comes from the main menu
            }else{
                idPlayer = bundle.getInt("idPlayer");
                devmode = bundle.getBoolean("dev");
                ArrayList<String> load = db.readFromDB(String.format("id = '%d'", idPlayer));

                playingAs.setText("Playing as: " + load.get(0).split("\\s\\|\\s")[1]);
                coins = new BigInteger(load.get(0).split("\\s\\|\\s")[3]);
                valorSumaClick = Integer.parseInt(load.get(0).split("\\s\\|\\s")[4]);
                valorSumaAutoclick = Integer.parseInt(load.get(0).split("\\s\\|\\s")[5]);
                valorOven = Integer.parseInt(load.get(0).split("\\s\\|\\s")[6]);
                nivelClick =Integer.parseInt(load.get(0).split("\\s\\|\\s")[7]);
                nivelAutoclick =Integer.parseInt(load.get(0).split("\\s\\|\\s")[8]);
                nivelOven =Integer.parseInt(load.get(0).split("\\s\\|\\s")[9]);
                costemejoraClick = Integer.parseInt(load.get(0).split("\\s\\|\\s")[10]);
                costemejoraAutoclick = Integer.parseInt(load.get(0).split("\\s\\|\\s")[11]);

                clicklevel.setText((clicklevel.getText().toString().replaceAll(" \\d+ ", "<-->")).replaceAll("<-->", ""+ nivelClick));
                autoclicklevel.setText((autoclicklevel.getText().toString().replaceAll("\\d+ ", "<-->")).replaceAll("<-->", ""+ nivelAutoclick));
                ovenlevel.setText((ovenlevel.getText().toString().replaceAll(" \\d+ ", "<-->")).replaceAll("<-->", ""+ nivelOven));
                clicklevel.setText((clicklevel.getText().toString().replaceAll("\\+\\d+", "<-->")).replaceAll("<-->", "+"+ valorSumaClick));
                autoclicklevel.setText((autoclicklevel.getText().toString().replaceAll("\\+\\d+", "<-->")).replaceAll("<-->", "+"+ valorSumaAutoclick));
            }
        }

        //different configs
        if(devmode) valorSumaClick = 10000000;
        if(boost) boost();
        coinDisplayer();
        autoSum();
    }

    //click miku
    public void sum(View v) {
        counter.setTextColor(txtcolor);
        coins = coins.add(new BigInteger(Long.toString(valorSumaClick)));
        miku.startAnimation(boing);
        coinDisplayer();
    }

    //Back button
    public void backbutton(View v){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE Jugadores SET ");
        sb.append("score="+coins);
        sb.append(", suma=" + valorSumaClick);
        sb.append(", autosuma="+ valorSumaAutoclick);
        sb.append(", oven="+ valorOven);
        sb.append(", ClickLvl=" + nivelClick);
        sb.append(", AutoLvl="+ nivelAutoclick);
        sb.append(", OvenLvl="+ nivelOven);
        sb.append(", CosteClick="+ costemejoraClick);
        sb.append(", CosteAuto="+ costemejoraAutoclick);
        sb.append(" WHERE id="+idPlayer);
        db.rawUpdate(sb.toString());

        finish();
    }

    //Go to store
    public void gotoStore (View v){
        Intent i = new Intent(this, Upgrades.class);
        i.putExtra("idPlayer", idPlayer);
        i.putExtra("valorSumaClick", valorSumaClick);
        i.putExtra("valorSumaAutoclick", valorSumaAutoclick);
        i.putExtra("valorOven", valorOven);
        i.putExtra("nivelClick", nivelClick);
        i.putExtra("nivelAutoclick", nivelAutoclick);
        i.putExtra("nivelOven", nivelOven);
        i.putExtra("costemejoraAutoclick", costemejoraAutoclick);
        i.putExtra("costemejoraClick", costemejoraClick);
        i.putExtra("costemejoraOven", costemejoraOven);
        i.putExtra("boost", false);
        i.putExtra("coins", coins.toString());
        startActivity(i);
        finish();
    }

    //Format the coins properly
    public void coinDisplayer() {
        //In order compares to: thousand, million, billion, trillion, quadrillion, quintillion, sextillion, septillion (and normal units) in short scale.
        if (coins.compareTo(new BigInteger("1000")) == 1 && coins.compareTo(new BigInteger("1000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000"), 2, RoundingMode.FLOOR)).toString() + "k");
        } else if (coins.compareTo(new BigInteger("1000000")) == 1 && coins.compareTo(new BigInteger("1000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000"), 2, RoundingMode.FLOOR)).toString() + "Mi");
        } else if (coins.compareTo(new BigInteger("1000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000"), 2, RoundingMode.FLOOR)).toString() + "Bi");
        } else if (coins.compareTo(new BigInteger("1000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000"), 2, RoundingMode.FLOOR)).toString() + "Tr");
        } else if (coins.compareTo(new BigInteger("1000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000"), 2, RoundingMode.FLOOR)).toString() + "Qa");
        } else if (coins.compareTo(new BigInteger("1000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "Qi");
        } else if (coins.compareTo(new BigInteger("1000000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "Si");
        } else if (coins.compareTo(new BigInteger("1000000000000000000000000")) == 1 && coins.compareTo(new BigInteger("1000000000000000000000000000")) != 1) {
            counter.setText((new BigDecimal(coins).divide(new BigDecimal("1000000000000000000000000"), 2, RoundingMode.FLOOR)).toString() + "Se");
        } else {
            counter.setText(coins.toString());
        }
    }

    //Thread for autosum
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

    //Thread for boost
    public void boost() { //not working properly
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
            boost = false;
        }).start();
    }


}