package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.lunalandexplorer.View.GameView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameView(this));
    }
}