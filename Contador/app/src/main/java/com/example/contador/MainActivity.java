package com.example.contador;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView counter;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        counter = (TextView) findViewById(R.id.countertext);
        b = (Button) findViewById(R.id.countersum);
    }

    public void sum(View v){
        counter.setTextColor(Color.rgb(0,0,0));
        int n = Integer.parseInt(counter.getText().toString()) + 1;
        counter.setText(Integer.toString(n));
        if(n >= 50){
           b.setEnabled(false);
           counter.setText("You've reach the end!");
           counter.setTextSize(20);
        }else{
            for (int i=2; i<=((int)n/2)+1; i++){
                if (n%i == 0 && n!=2){
                    counter.setTextColor(Color.rgb(0,0,0));
                    break;
                }else counter.setTextColor(Color.rgb(255,0,0));
            }
        }
    }

}