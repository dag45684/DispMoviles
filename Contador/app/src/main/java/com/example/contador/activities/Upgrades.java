package com.example.contador.activities;

import androidx.appcompat.app.AppCompatActivity;

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

    long addition, autoSumValue;
    int ovenspeed, click_level, auto_level, oven_level, costemejora_click, costemejora_auto, costemejora_oven;
    boolean boost;
    BigInteger coins;
    TextView counter, clicklevel, autoclicklevel, ovenlevel;
    List<Upgrade> l;
    Bundle bundle;
    ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upgrades);
        l = Arrays.asList(
                new Upgrade("Increases click value",
                        "100",
                        "Buy Click level",
                        R.drawable.click,
                        1),
                new Upgrade("Increases autoclick value",
                        "100",
                        "Buy Autoclick level",
                        R.drawable.autoclick,
                        2),
                new Upgrade("Increases speed of the autoclick",
                        "-1000000",
                        "Buy Oven level",
                        R.drawable.oven,
                        3)
        );

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

        counter = (TextView) findViewById(R.id.coinsreal);
        bundle = getIntent().getExtras();
        addition = bundle.getLong("addition");
        autoSumValue = bundle.getLong("autoSumValue");
        ovenspeed = bundle.getInt("ovenspeed");
        click_level = bundle.getInt("click_level");
        auto_level = bundle.getInt("auto_level");
        oven_level = bundle.getInt("oven_level");
        costemejora_auto = bundle.getInt("costemejora_auto");
        costemejora_click = bundle.getInt("costemejora_click");
        costemejora_oven = bundle.getInt("costemejora_oven");
        boost = bundle.getBoolean("boost");
        coins = new BigInteger(bundle.getString("coins"));

        coinDisplayer();
        autoSum();
    }

    public void goback(View v){
        Intent i = new Intent(Upgrades.this, Game.class);
        i.putExtra("addition", addition);
        i.putExtra("autosumvalue", autoSumValue);
        i.putExtra("ovenspeed", ovenspeed);
        i.putExtra("click_level", click_level);
        i.putExtra("auto_level", auto_level);
        i.putExtra("oven_level", oven_level);
        i.putExtra("costemejora_auto", costemejora_auto);
        i.putExtra("costemejora_click", costemejora_click);
        i.putExtra("costemejora_oven", costemejora_oven);
        i.putExtra("boost", boost);
        i.putExtra("coins", coins.toString());
       // i.putExtra("clicklevel", clicklevel.getText());
       // i.putExtra("autoclicklevel", autoclicklevel.getText());
       // i.putExtra("ovenlevel", ovenlevel.getText());
        startActivity(i);
    }

    public void coinDisplayer() {
        counter.setText(coins.toString());
    }

    public void autoSum() {
        new Thread(() -> {
            while (true) {
                coins = coins.add(new BigInteger(Long.toString(autoSumValue)));
                runOnUiThread(() -> coinDisplayer());
                try {
                    Thread.sleep(ovenspeed);
                } catch (InterruptedException e) {}
            }
        }).start();
    }

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
        }
    }

    public void click_levelup() {
        long improvement = 10;
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_click))) == 1){
            counter.setTextColor(Color.BLACK);
            click_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_click)));
            coinDisplayer();
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
            l.get(0).setUpgradedetails(Integer.toString(costemejora_click));
        }
        else counter.setTextColor(Color.RED);
    }

    public void auto_levelup () {
        long improvement = 1;
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_auto))) == 1){
            counter.setTextColor(Color.BLACK);
            auto_level++;
            coins = coins.subtract(new BigInteger(Integer.toString(costemejora_auto)));
            coinDisplayer();
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
            l.get(1).setUpgradedetails(Integer.toString(costemejora_auto));
        }
        else counter.setTextColor(Color.RED);
    }

    public void oven_levelup (){
        if(coins.compareTo(new BigInteger(Integer.toString(costemejora_oven))) == 1){
            counter.setTextColor(Color.BLACK);
            if (ovenspeed > 200){
                coins = coins.subtract(new BigInteger(Integer.toString(costemejora_oven)));
                coinDisplayer();
                ovenspeed -= 200;
                oven_level++;
            }
            else {
                //gray button here and set text to max
                ovenlevel.setText("Oven level: Max.");
            }
        }
        else counter.setTextColor(Color.RED);
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
}