package com.example.lunalandexplorer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Spaceship {

    private static final int BMP_ROWS = 4;

    private static final int BMP_COLUMNS = 4;

    private GameView gameView;

    private Bitmap bmp;

    private int x = 0;

    private int y = 0;

    private int xSpeed;

    private int ySpeed;

    private int currentFrame = 0;
    private static final int MAX_FRAME_DURATION = 10;
    private int frameDuration = MAX_FRAME_DURATION;

    private int width;

    private int height;

    private boolean deathSprite = false;

    private int normalAnimationRow = 2;

    int [] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};


    public int MAX_SPEED = 5;




    public Spaceship(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

        Random rbd = new Random();

        x = gameView.getWidth() / 2;

        y = rbd.nextInt(gameView.getHeight() - height);

        xSpeed = rbd.nextInt(MAX_SPEED*2) - MAX_SPEED;
        ySpeed = rbd.nextInt(MAX_SPEED*2) - MAX_SPEED;


    }

    private void update() {
        if(deathSprite == true){
            return;
        }
        if(x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;

        }
        x = x + xSpeed;

        if(y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }
        y = y + ySpeed;
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;
            frameDuration = MAX_FRAME_DURATION; // Restablecer la temporización
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;

        int srcY = getAnimationRow() * height;

        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src , dst, null);
    }


    private int getAnimationRow() {
        return normalAnimationRow;
    }

    public boolean isCollition(float x2, float y2){
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public boolean isDeathSprite() {
        return deathSprite;
    }

    public void setDeathSprite(boolean deathSprite) {
        this.deathSprite = deathSprite;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

