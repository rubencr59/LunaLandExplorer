package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lunalandexplorer.R;

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
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.exitButton){
            finish();
        }
    }
}