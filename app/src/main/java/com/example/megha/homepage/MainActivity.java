package com.example.megha.homepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Access to Main LinearLayout to access child layouts
        mainLayout = findViewById(R.id.mainLayout);
        setSingleEvent(mainLayout);
    }

    //Takes Main Layout
    private void setSingleEvent(LinearLayout mainLayout){

        CardView event = findViewById(R.id.eventid);
        CardView food = findViewById(R.id.foodid);
        CardView weather = findViewById(R.id.weatherid);
        CardView shop = findViewById(R.id.shopid);

        //Take Action based on CardView Id
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventActivity();
            }
        });
/*
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodActivity();
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weatherActivity();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shopActivity();
            }
        });
*/
    }

    //Call Activities based on selected Icons
    public void eventActivity(){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    /*
    public void foodActivity(){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    public void weatherActivity(){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    public void shopActivity(){
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
    */
}
