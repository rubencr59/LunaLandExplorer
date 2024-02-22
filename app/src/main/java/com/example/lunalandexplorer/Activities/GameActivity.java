package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

public class GameActivity extends AppCompatActivity {

    FrameLayout gameLayout;
    TextView score;
    private  int scoreCount = 0;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameLayout = findViewById(R.id.gameLayout);


        gameLayout.addView(new GameView(this));


        score = new TextView(this);
        score.setText("Score: "+ scoreCount + "");
        score.setTextColor(Color.BLACK);
        score.setTextSize(22);
        score.setPadding(60, 60, 20, 20);

        gameLayout.addView(score);
    }

    @SuppressLint("SetTextI18n")
    public void setScore(int scoreSum){
        this.scoreCount = this.scoreCount + scoreSum;
        score.setText("Score: "+ scoreCount + "");
    }

    public void createGameOverLayout() {
        FrameLayout gameOverLayout = new FrameLayout(this);
        gameOverLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER));

        gameOverLayout.setBackgroundColor(Color.BLACK);

        TextView gameOverText = new TextView(this);
        gameOverText.setText("GAME OVER");
        gameOverText.setTextColor(Color.WHITE);
        gameOverText.setTextSize(40);
        gameOverText.setGravity(Gravity.CENTER);

        gameOverLayout.addView(gameOverText);
        gameLayout.addView(gameOverLayout);
    }

    public void createWinLayout() {
        FrameLayout winLayout = new FrameLayout(this);
        winLayout.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.CENTER));

        winLayout.setBackgroundColor(Color.BLACK);

        TextView winText = new TextView(this);
        winText.setText("YOU WIN");
        winText.setTextColor(Color.WHITE);
        winText.setTextSize(40);
        winText.setGravity(Gravity.CENTER);

        winLayout.addView(winText);
        gameLayout.addView(winLayout);
    }


}