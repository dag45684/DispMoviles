package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.contador.R;
import com.example.contador.utils.Upgrade;
import com.example.contador.utils.Upgrade_Adapter;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Upgrades extends AppCompatActivity {

    int idPlayer;
    boolean night = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES;
    int bgcolor = night ? Color.parseColor("#5c5c5c") : Color.parseColor("#b5d6eb");
    int txtcolor = night ? Color.CYAN : Color.BLACK;
    int assetscolor = night ? Color.CYAN : Color.parseColor("#f2f2f2");
    long valorSumaClick = 100000;
    long valorSumaAutoclick = 1;
    int valorOven = 2000;
    int nivelClick = 1;
    int nivelAutoclick = 1;
    int nivelOven = 1;
    int costemejoraClick = 100;
    int costemejoraOven = 1000000;
    int costemejoraAutoclick = 100;
    int costeBoost = 5000;
    boolean boost = false;
    BigInteger coins;
    TextView counter, textoNivelClick, textoNivelAutoclick, textoNivelOven;
    List<Upgrade> l;
    Bundle bundle;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrades);

        //Bg Color
        View rootUpgrade = findViewById(R.id.upgrades);
        rootUpgrade.setBackgroundColor(bgcolor);

        //define the counter
        counter = (TextView) findViewById(R.id.coinsreal);

        //the list for the upgrades
        l = Arrays.asList(
                new Upgrade("Increases click value",
                        "100",
                        "Buy Click level",
                        R.drawable.click,
                        1,
                        assetscolor),
                new Upgrade("Increases autoclick value",
                        "100",
                        "Buy Autoclick level",
                        R.drawable.autoclick,
                        2,
                        assetscolor),
                new Upgrade("Increases speed of the autoclick",
                        "1000000",
                        "Buy Oven level",
                        R.drawable.oven,
                        3,
                        assetscolor),
                new Upgrade("Boosts your points for 5 seconds.",
                        "5000",
                        "Buy Boost",
                        R.drawable.boost,
                        4,
                        assetscolor)
        );
        //the actual listview for the upgrades
        lv = (ListView) findViewById(R.id.list);
        Upgrade_Adapter upgradeAdapter = new Upgrade_Adapter(this, R.layout.item_upgrade, l);
        lv.setAdapter(upgradeAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> av, View v, int i, long l){
               Upgrade upgrade = (Upgrade) av.getItemAtPosition(i);
               levelup(upgrade.getTag());
               lv.invalidateViews();
            }
        });

        //Unpack the bundle
        bundle = getIntent().getExtras();
        idPlayer = bundle.getInt("idPlayer");
        valorSumaClick = bundle.getLong("valorSumaClick");
        valorSumaAutoclick = bundle.getLong("valorSumaAutoclick");
        valorOven = bundle.getInt("valorOven");
        nivelClick = bundle.getInt("nivelClick");
        nivelAutoclick = bundle.getInt("nivelAutoclick");
        nivelOven = bundle.getInt("nivelOven");
        costemejoraAutoclick = bundle.getInt("costemejoraAutoclick");
        costemejoraClick = bundle.getInt("costemejoraClick");
        costemejoraOven = bundle.getInt("costemejoraOven");
        boost = bundle.getBoolean("boost");
        coins = new BigInteger(bundle.getString("coins"));

        //set the proper price for the upgrades on creation
        l.get(0).setUpgradedetails(Integer.toString(costemejoraClick));
        l.get(1).setUpgradedetails(Integer.toString(costemejoraAutoclick));

        //format and restart the autosum
        coinDisplayer();
        autoSum();
    }

    //back button
    public void goback(View v){
        Intent i = new Intent(Upgrades.this, Game.class);

        //handle boost lost value (i do not know where lmao)
        boolean boost_bdl = boost ? true : false;
        i.putExtra("boost", boost_bdl);

        i.putExtra("idPlayer", idPlayer);
        i.putExtra("From", "Upgrades");
        i.putExtra("valorSumaClick", valorSumaClick);
        i.putExtra("valorSumaAutoclick", valorSumaAutoclick);
        i.putExtra("valorOven", valorOven);
        i.putExtra("nivelClick", nivelClick);
        i.putExtra("nivelAutoclick", nivelAutoclick);
        i.putExtra("nivelOven", nivelOven);
        i.putExtra("costemejoraAutoclick", costemejoraAutoclick);
        i.putExtra("costemejoraClick", costemejoraClick);
        i.putExtra("costemejoraOven", costemejoraOven);
        i.putExtra("coins", coins.toString());
        startActivity(i);
        finish();
    }

    //coins will not be formatted here so we just give it to the counter as string
    public void coinDisplayer() {
        counter.setText(coins.toString());
    }

    //thread for autosum
    public void autoSum() {
        new Thread(() -> {
            while (true) {
                coins = coins.add(new BigInteger(Long.toString(valorSumaAutoclick)));
                runOnUiThread(() -> coinDisplayer());
                try {
                    Thread.sleep(valorOven);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

    //switch for the item we will buy and call for the function that checks the money, buys it and
    //makes it work properly (right below this func.)
    public void levelup (int tag){
        switch (tag){
            case 1:
                click_levelup();
                break;
            case 2:
                auto_levelup();
                break;
            case 3:
                oven_levelup();
                break;
            case 4:
                boost();
        }
    }

    public void click_levelup() {
        long improvement = 10;
        if(coins.compareTo(new BigInteger(Integer.toString(costemejoraClick))) == 1){
            counter.setTextColor(Color.BLACK);
            nivelClick++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejoraClick)));
            coinDisplayer();
            if (nivelClick <= 10) {
                costemejoraClick = 100;
            } else if (nivelClick <= 20) {
                costemejoraClick = 1000;
                improvement += improvement+ nivelClick;
            } else if (nivelClick <= 30) {
                costemejoraClick = 500000;
                improvement *= nivelClick;
            } else {
                costemejoraClick = 10000000;
                improvement = nivelClick * nivelClick;
            }
            valorSumaClick += improvement;
            l.get(0).setUpgradedetails(Integer.toString(costemejoraClick));
        }
        else counter.setTextColor(Color.RED);
    }

    public void auto_levelup () {
        long improvement = 1;
        if(coins.compareTo(new BigInteger(Integer.toString(costemejoraAutoclick))) == 1){
            counter.setTextColor(Color.BLACK);
            nivelAutoclick++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejoraAutoclick)));
            coinDisplayer();
            if (nivelAutoclick <= 10) {
                costemejoraAutoclick = 100;
                improvement += nivelAutoclick;
            } else if (nivelAutoclick <= 20) {
                costemejoraAutoclick = 1000;
                improvement += nivelAutoclick * 2;
            } else if (nivelAutoclick <= 30) {
                costemejoraAutoclick = 50000;
                improvement *= nivelAutoclick;
            } else {
                costemejoraAutoclick = 1000000;
                improvement = nivelAutoclick * 12;
            }
            valorSumaAutoclick += improvement;
            l.get(1).setUpgradedetails(Integer.toString(costemejoraAutoclick));
        }
        else counter.setTextColor(Color.RED);
    }

    public void oven_levelup (){
        if(coins.compareTo(new BigInteger(Integer.toString(costemejoraOven))) == 1){
            counter.setTextColor(Color.BLACK);
            if (valorOven > 200){
                coins = coins.subtract(new BigInteger(Integer.toString(costemejoraOven)));
                coinDisplayer();
                valorOven -= 200;
                nivelOven++;
            }
            else {
                //TODO: gray button here and set text to max
                textoNivelOven.setText("Oven level: Max.");
            }
        }
        else counter.setTextColor(Color.RED);
    }

    public void boost() {
        if(coins.compareTo(new BigInteger(Integer.toString(costemejoraOven))) == 1) {
            counter.setTextColor(Color.BLACK);
            if (!boost) {
                boost = true;
                coins = coins.subtract(new BigInteger(Integer.toString(costeBoost)));
                coinDisplayer();
            } else counter.setTextColor(Color.RED);
        }else counter.setTextColor(Color.RED);
    }
}