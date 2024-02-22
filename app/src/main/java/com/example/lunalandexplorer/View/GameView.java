package com.example.lunalandexplorer.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.lunalandexplorer.Activities.GameActivity;
import com.example.lunalandexplorer.Activities.MainActivity;
import com.example.lunalandexplorer.BackgroundMusicPlayer;
import com.example.lunalandexplorer.GameLogic;
import com.example.lunalandexplorer.Sprites.BossAttack;
import com.example.lunalandexplorer.Sprites.Heart;
import com.example.lunalandexplorer.Sprites.Spaceship;
import com.example.lunalandexplorer.Thread.GameLoopThread;
import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.Sprites.Enemy;
import com.example.lunalandexplorer.Sprites.Laser;
import com.example.lunalandexplorer.Sprites.PowerUp;
import com.example.lunalandexplorer.Sprites.TempSprite;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameLogic gameLogic;
    private boolean isGameOver = false;
    private long gameOverStartTime = 0;
    private static final long GAME_OVER_DELAY = 6000;
    private GameLoopThread gameLoopThread;




    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameLogic = new GameLogic(this, (GameActivity) getContext());
        this.setBackgroundResource(R.drawable.river_background);
        BackgroundMusicPlayer.start(getContext(), R.raw.music_background);

        gameLoopThread = new GameLoopThread(this, getHolder(), gameLogic);
        gameLoopThread.setRunning(true);
        gameLoopThread.start();
    }



    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        gameLoopThread.setRunning(false);
        BackgroundMusicPlayer.stop();
        boolean retry = true;
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void irMenu(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(intent);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


            Spaceship spaceship = gameLogic.getSpaceship();
            spaceship.onDraw(canvas);

            Heart heart = gameLogic.getHeart();
            heart.onDraw(canvas);

            for (Laser laser : gameLogic.getSpaceship().getLasers()) {
                laser.onDraw(canvas);
            }
            for (Enemy enemy : gameLogic.getEnemies()) {
                enemy.onDraw(canvas);
                enemy.evitarChocarEnemigos(gameLogic);
            }

            for (TempSprite temp : gameLogic.getTemps()) {
                temp.onDraw(canvas);
            }

            for (PowerUp powerUp : gameLogic.getPowerUps()) {
                powerUp.onDraw(canvas);
            }



            if(gameLogic.getBoss() != null){
                gameLogic.getBoss().onDraw(canvas);

                for (BossAttack bossAttack : gameLogic.getBoss().getBossAttacks()) {
                    bossAttack.onDraw(canvas);
                }
            }

            gameLogic.checkCollisions();
            gameLogic.checkGameOver();
            gameLogic.checkPowerUpCollisions();
            if(gameLogic.getBoss()!=null){
                gameLogic.checkBossAttackCollisions();
                gameLogic.checkBossGetHit();
            }

    }

    public void gameOver(){
        isGameOver = true;
        gameOverStartTime = System.currentTimeMillis();
        gameLoopThread.setRunning(false);
        BackgroundMusicPlayer.stop();
        isGameOver = true;

        gameLogic.gameOver();

    }

    public void gameWin(){
        isGameOver = true;
        gameOverStartTime = System.currentTimeMillis();
        gameLoopThread.setRunning(false);
        BackgroundMusicPlayer.stop();
        isGameOver = true;

        gameLogic.gameWin();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int xTouch = (int) event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(!gameLogic.getSpaceship().getDisparar()){
                    gameLogic.getSpaceship().setDisparar(true);
                    gameLogic.startShooting();
                    break;
                }
            case MotionEvent.ACTION_UP:
                gameLogic.getSpaceship().setDisparar(false);
                break;
            case MotionEvent.ACTION_MOVE:
                gameLogic.getSpaceship().setX(xTouch- gameLogic.getSpaceship().getWidth()/2);
                break;
        }
        return true;
    }


}
