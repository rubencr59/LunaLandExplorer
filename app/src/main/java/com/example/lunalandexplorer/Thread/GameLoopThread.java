package com.example.lunalandexplorer.Thread;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.example.lunalandexplorer.GameLogic;
import com.example.lunalandexplorer.View.GameView;

public class GameLoopThread extends Thread {

    static final long FPS = 60;

    private SurfaceHolder sh;

    private GameView gv;

    private GameLogic gameLogic;

    private boolean running;

    public  GameLoopThread(GameView gameView, SurfaceHolder surfaceHolder, GameLogic gameLogic) {
        this.sh = surfaceHolder;
        this.gv = gameView;
        this.gameLogic = gameLogic;

        running = false;

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void run(){
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;

        Canvas canvas;

        while (running) {
            canvas = null;
            startTime = System.currentTimeMillis();
            try {
                canvas = sh.lockCanvas(null);
                synchronized (sh) {
                    gv.postInvalidate();
                }
            } finally {
                if (canvas != null) {
                    gv.getHolder().unlockCanvasAndPost(canvas);
                }
            }
            sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0) {
                    sleep(sleepTime);
                } else {
                    sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
