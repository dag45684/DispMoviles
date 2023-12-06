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

/*
DB DATA LOADING:
Returns: ArrayList of each row separated by " | "
on readFromDB(filter) or data corresponds to:
id: .get(n).split(" | ")[0]
name: .get(n).split(" | ")[1]
pass: .get(n).split(" | ")[2]
score: .get(n).split(" | ")[3]
suma: .get(n).split(" | ")[4]
autosuma: .get(n).split(" | ")[5]
oven: .get(n).split(" | ")[6]
cliclvl: .get(n).split(" | ")[7]
autolvl: .get(n).split(" | ")[8]
ovenlvl: .get(n).split(" | ")[9]
costeclick: .get(n).split(" | ")[10]
costeauto: .get(n).split(" | ")[11]
 */

public class Game extends AppCompatActivity {

    DB_Handler db;
    int idPlayer;
    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.BLACK : Color.parseColor("#f2f2f2");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DB_Handler(this);

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
            if(!bundle.getString("From").equals("Main")){
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
            }else{
                idPlayer = bundle.getInt("idPlayer");
                ArrayList<String> load = db.readFromDB(String.format("id = '%d'", idPlayer));

                Log.i("semen", load.get(0));

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
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE Jugadores SET ");
        sb.append("score="+coins);
        sb.append(", suma=" + valorSumaClick);
        sb.append(", autosuma="+ valorSumaAutoclick);
        sb.append(", oven"+ valorOven);
        sb.append(", ClickLvl" + nivelClick);
        sb.append(", AutoLvl"+ nivelAutoclick);
        sb.append(", OvenLvl"+ nivelOven);
        sb.append(", CosteClick"+ costemejoraClick);
        sb.append(", CosteAuto"+ costemejoraAutoclick);
        sb.append(" WHERE id="+idPlayer);

        db.rawUpdate(sb.toString());
        Intent i = new Intent(this, Welcome.class);
        i.putExtra("idPlayer", idPlayer);
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