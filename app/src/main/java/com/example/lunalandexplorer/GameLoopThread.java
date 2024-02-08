package com.example.lunalandexplorer;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class GameLoopThread extends Thread {

    static final long FPS = 60;

    private SurfaceHolder sh;

    private GameView gv;

    private boolean running;

    public  GameLoopThread(GameView gameView, SurfaceHolder surfaceHolder) {
        this.sh = surfaceHolder;
        this.gv = gameView;
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
