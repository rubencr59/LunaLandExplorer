package com.example.lunalandexplorer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class GameView extends SurfaceView implements  SurfaceHolder.Callback{


    public Spaceship spaceShip;
    public List<Enemy> enemies;

    private GameLoopThread gameLoopThread;

    private Bitmap laserBitmap;

    private boolean shooting = false;

    private Handler shootingHandler = new Handler();
    private static final long SHOOTING_INTERVAL = 500;

    public GameView(Context context) {
        super(context);

        enemies = new ArrayList<Enemy>();
        getHolder().addCallback(this);


    }



    @Override
    public void surfaceCreated( SurfaceHolder holder) {
        this.setBackgroundResource(R.drawable.background);
        gameLoopThread = new GameLoopThread(this, getHolder());
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
        createSpaceship();
        createEnemies();

        laserBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.laser_bolts);

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

        for (Laser laser : spaceShip.getLasers()) {
            laser.onDraw(canvas);
        }
        for (Enemy enemy : enemies) {
            enemy.onDraw(canvas);
        }

        handleCollision();
    }

    private void createSpaceship(){
        this.spaceShip = (createSprite4(R.drawable.spaceship));
    }

    private void createEnemies(){
        Random numeroRandom = new Random();
        int numEnemies = 5;
        for(int i = 0; i < numEnemies; i++){
            if(numeroRandom.nextInt(2) == 0){
                enemies.add(generarEnemigo(R.drawable.enemy_medium,3));
            }else{
                enemies.add(generarEnemigo(R.drawable.enemy_big,6));
            }
        }
    }

     private Enemy generarEnemigo(int resource, int life){
          Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
          return new Enemy(this, bitmap, life);
     }

     private Laser createLaser(int resource){
         Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
         return new Laser(this, bitmap);
     }


    private Spaceship createSprite4(int resource){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resource);
        return new Spaceship(this, bitmap);
    }

    //Método para manejar las colisiones de los enemigos con los lasers
    public void handleCollision() {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            Iterator<Laser> laserIterator = spaceShip.getLasers().iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();

                if (laser.isCollition(enemy)) {
                    enemy.kick(laser.getDamage());
                    laserIterator.remove();
                    if(enemy.isDeathSprite()){
                        enemyIterator.remove();
                    }
                    break;
                }
            }
        }
    }

    private Runnable shootingRunnable = new Runnable() {
        @Override
        public void run() {
            if (shooting) {
                spaceShip.shoot(laserBitmap);
                shootingHandler.postDelayed(this, SHOOTING_INTERVAL);
            }
        }
    };

    private void startShootingTimer() {
        shootingHandler.postDelayed(shootingRunnable, 0);
    }

    private void stopShootingTimer() {
        shootingHandler.removeCallbacks(shootingRunnable);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                shooting = true;
                startShootingTimer();
                break;

            case MotionEvent.ACTION_UP:
                shooting = false;
                stopShootingTimer();
                break;

            case MotionEvent.ACTION_MOVE:
                spaceShip.setX((int) event.getX()- 80);
                break;

        }

        return true;
    }




}
