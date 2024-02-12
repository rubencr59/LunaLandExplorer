package com.example.lunalandexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.lunoland_card);


        setContentView(R.layout.activity_main);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playButton){
            GameView gameView = new GameView(this);
            gameView.setBackgroundColor(Color.BLACK);
            setContentView(gameView);
        }else if (v.getId() == R.id.exitButton){
            finish();
        }
    }
}