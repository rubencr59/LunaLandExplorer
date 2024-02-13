package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;

public abstract class Sprite {

    public GameView gameView;
    public Bitmap bmp;
    public int x;
    public int y;
    private int xSpeed, ySpeed;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 4;
    private int currentFrame;
    private static final int MAX_FRAME_DURATION = 15;
    private int frameDuration = MAX_FRAME_DURATION;


    public int width;
    public int height;
    private boolean deathSprite;

    public Rect area;

    public Sprite(GameView gameView, Bitmap bmp) {
        this.gameView = gameView;
        this.deathSprite = false;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.area = new Rect(x, y, x + width, y + height);
        frameDuration = MAX_FRAME_DURATION;
    }

    public void update() {
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;
            frameDuration = MAX_FRAME_DURATION;
        }
    }

    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public boolean isHovered(int xPress, int yPress) {
        return xPress > x && xPress < x + width && yPress > y && yPress < y + height;
    }



    public int getAnimationRow(){
        return 0;
    };

    public  void kick(int damage){};

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isDeathSprite() {
        return deathSprite;
    }

    public void setDeathSprite(boolean deathSprite) {
        this.deathSprite = deathSprite;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
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

    public int getMaxFrameDuration() {
        return MAX_FRAME_DURATION;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getFrameDuration() {
        return frameDuration;
    }

    public void setFrameDuration(int frameDuration) {
        this.frameDuration = frameDuration;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public Rect getArea() {
        return area;
    }



}
