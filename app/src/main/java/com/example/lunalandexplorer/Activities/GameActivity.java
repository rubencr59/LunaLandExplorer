package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

public class GameActivity extends AppCompatActivity {

    FrameLayout gameLayout;

    private  int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameLayout = findViewById(R.id.gameLayout);


        gameLayout.addView(new GameView(this));


        TextView score = new TextView(this);
        score.setText("Score: "+ score);
        score.setTextColor(Color.BLACK);
        score.setTextSize(22);
        score.setPadding(60, 60, 20, 20);

        gameLayout.addView(score);

    }

    public void setScore(int score){
        this.score = this.score + score;
    }
}