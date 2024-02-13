package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;


public class Laser extends Sprite {

    private int speed;
    private int direction;
    private int damage = 1;

    private static final int BMP_ROWS = 2;
    private static final int BMP_COLUMNS = 2;
    private static final int MAX_FRAME_DURATION = 15;

    private int currentRow = 0;

    private int currentColumn = 0;

    private int currentFrame = 0;
    private int frameDuration = MAX_FRAME_DURATION;

    public Laser(GameView gameView, Bitmap bmp, Spaceship spaceship, int laserStyle) {
        super(gameView, bmp);
        this.speed = 10;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.x = spaceship.getX() + spaceship.getWidth() / 2 - width / 2;
        this.y = spaceship.getY() - height;
        this.area = new Rect(x, y, x + width, y + height);

        setStyleAndDamage(laserStyle);
    }


    public void setStyleAndDamage(int number){
        switch (number){
            case 1:
                this.currentColumn = 0;
                this.currentRow = 0;
                break;
            case 2:
                this.currentColumn = 1;
                this.currentRow = 0;
                this.damage = this.damage + 2;
                break;
            case 3:
                this.currentColumn = 0;
                this.currentRow = 1;
                this.damage = this.damage + 3;
                break;
            case 4:
                this.currentColumn = 1;
                this.currentRow = 1;
                this.damage = this.damage + 4;
                break;
        }
    }

    @Override
    public void update() {
        if (direction == 0) {
            y -= speed;
        } else if (direction == 1) {
            y += speed;
        }
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = getCurrentColumn();
            frameDuration = MAX_FRAME_DURATION;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentColumn * width;
        int srcY = currentRow * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }


    public int getCurrentColumn() {
        return currentColumn;
    }

    public int getAnimationRow() {

        return currentRow;
    }

    public boolean isCollition(Enemy enemy) {
        return enemy.getX() < x + width && enemy.getX() + enemy.getWidth() > x && enemy.getY() < y + height && enemy.getY() + enemy.getHeight() > y;

    }

    @Override
    public void kick(int damage) {
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return this.damage;
    }

    public Rect getArea() {
        return this.area;
    }
}

