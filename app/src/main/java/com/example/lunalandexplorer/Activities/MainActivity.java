package com.example.lunalandexplorer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lunalandexplorer.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    String nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.drawable.main_menu);


        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.nameField);

        nameUser = textView.getText().toString();
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.playButton){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            this.finish();
        }else if (v.getId() == R.id.exitButton){
            finish();
        } else if (v.getId() == R.id.buttonSelectSpaceShip) {
            Intent intent = new Intent(this, SelectSpaceShip.class);
            startActivity(intent);
            this.finish();
        }
    }
}