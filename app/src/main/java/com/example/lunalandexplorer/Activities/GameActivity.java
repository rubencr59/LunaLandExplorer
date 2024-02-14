package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

public class GameActivity extends AppCompatActivity {

    FrameLayout gameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameLayout = findViewById(R.id.gameLayout);

        gameLayout.addView(new GameView(this));


    }
}