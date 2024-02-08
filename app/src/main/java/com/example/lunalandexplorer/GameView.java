package com.example.lunalandexplorer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class GameView extends SurfaceView implements  SurfaceHolder.Callback{


    private Spaceship spaceShip;
    private List<Sprite> sprites;

    private GameLoopThread gameLoopThread;

    private long lastClick;




    public GameView(Context context) {
        super(context);

        sprites = new ArrayList<Sprite>();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated( SurfaceHolder holder) {
        gameLoopThread = new GameLoopThread(this, getHolder());
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
        createSprites();



    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);


        spaceShip.onDraw(canvas);


    }

    private void createSprites(){
        this.spaceShip = (createSprite4(R.drawable.spaceship));
    }


    private Sprite createSprite(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        return new Sprite(this, bitmap);
    }

    private Spaceship createSprite4(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        return new Spaceship(this, bitmap);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        //Cuando pulse en la spaceShip cambie el currentFrame
       // if(event.getAction() == MotionEvent.ACTION_DOWN){


        //}

        return true;
    }




}
