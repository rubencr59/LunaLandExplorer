package com.example.lunalandexplorer;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Enemy {

    private static final int BMP_COLUMNS = 2;

    private GameView gameView;

    private Bitmap bmp;

    private int x = 0;

    private int y = 0;

    private int xSpeed;

    private int ySpeed;

    private int currentFrame = 0;

    private static final int MAX_FRAME_DURATION = 15;

    private int width;

    private int height;

    private  int life;

    private boolean deathSprite = false;

    public Enemy(GameView gameView, Bitmap bmp, int life) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight();
        this.life = life;
        Random rnd = new Random();

        x = rnd.nextInt(gameView.getWidth() - width);
        y = 0;

        xSpeed = 1;
        ySpeed = 5;

    }

    public void update() {

        int spaceshipX = gameView.spaceShip.getX();
        int spaceshipY = gameView.spaceShip.getY();

        // Calcular la dirección hacia la nave
        int xDirection = (spaceshipX > x) ? 1 : -1;
        int yDirection = (spaceshipY > y) ? 1 : -1;



        //si los enemigos chocan que solo se cambie la y
        for (Enemy enemy : gameView.enemies) {
            if (enemy != this && checkCollision(enemy)) {
                xDirection = 0;
                break;
            }
        }

        x = x + xDirection * xSpeed;

        y = y + yDirection * ySpeed;

    }

    public void onDraw(Canvas canvas){
        update();
        int srcX = currentFrame * width;

        int srcY = 0 * height;

        @SuppressLint("DrawAllocation") Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);

        @SuppressLint("DrawAllocation") Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src , dst, null);
    }

    private boolean checkCollision(Enemy otherEnemy) {
        Rect rectThis = new Rect(x, y, x + width, y + height);
        Rect rectOther = new Rect(otherEnemy.x, otherEnemy.y, otherEnemy.x + otherEnemy.width, otherEnemy.y + otherEnemy.height);

        return Rect.intersects(rectThis, rectOther);
    }

    public void kick(int damage){
        life -= damage;
        if(life == 0){
            deathSprite = true;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDeathSprite() {
        return deathSprite;
    }

}
