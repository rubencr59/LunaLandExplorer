package com.example.lunalandexplorer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Laser {


    private GameView gameView;

    private Bitmap bmp;

    private int x;
    private int y;

    private int speed;
    private boolean active;
    private int width;
    private int height;
    private int direction;
    private int damage = 1;

    private final int BMP_ROWS = 2;

    private final int BMP_COLUMNS = 2;

    private int currentFrame = 0;

    private int rowNumber = 0;

    private int columNumber = 0;

    private int numberLaserRow = 0;

    private int numberLaserColum = 0;

    private static final int MAX_FRAME_DURATION = 15;
    private int frameDuration = MAX_FRAME_DURATION;


    public Laser(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

        this.speed = 10;

        this.x = gameView.spaceShip.getX() + gameView.spaceShip.getWidth() / 2 - width / 2;

        this.y = gameView.spaceShip.getY() - height;



    }

    public void update() {
        if (direction == 0) {
            y -= speed;
        } else if (direction == 1) {
            y += speed;
        }
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;
            frameDuration = MAX_FRAME_DURATION;
        }

    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = numberLaserColum * width;

        int srcY = numberLaserRow * height;

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src , dst, null);

    }

    public boolean isCollition(Enemy enemy) {
        return enemy.getX() < x + width && enemy.getX() + enemy.getWidth() > x && enemy.getY() < y + height && enemy.getY() + enemy.getHeight() > y;

    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return this.damage;
    }



}
