package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;

public class SpecialLaser extends Laser{

    private int speed;
    private int direction;
    private int damage = 1;

    private static final int BMP_ROWS = 15;
    private static final int BMP_COLUMNS = 1;
    private static final int MAX_FRAME_DURATION = 12;
    private int frameCount = 15;

    private int currentFrame = 15;
    private int frameDuration = MAX_FRAME_DURATION;


    public SpecialLaser(GameView gameView, Bitmap bmp, Spaceship spaceship, int laserStyle) {
        super(gameView, bmp, spaceship, laserStyle);
        this.speed = 10;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.x = spaceship.getX() + spaceship.getWidth() / 2 - width / 2;
        this.y = spaceship.getY() - height;
        this.area = new Rect(x, y, x + width, y + height);
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
            currentFrame--;

            if (currentFrame < 0) {
                currentFrame = frameCount - 1;
            }

            frameDuration = MAX_FRAME_DURATION;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        update();

        int srcX = 0;
        int srcY = currentFrame * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
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
