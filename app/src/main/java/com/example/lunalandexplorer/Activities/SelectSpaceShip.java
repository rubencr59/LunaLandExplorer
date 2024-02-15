package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lunalandexplorer.GameLogic;
import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.service.SelectSpaceShipService;

public class SelectSpaceShip extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_space_ship);
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.selectSpaceShip2){
            SelectSpaceShipService.selectSpaceShip(R.drawable.spaceship);
        }else if (v.getId() == R.id.selectSpaceShip1){
            SelectSpaceShipService.selectSpaceShip(R.drawable.spaceship2);
        }else if(v.getId() == R.id.buttonReturn){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}