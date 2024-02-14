package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Paint;

import com.example.lunalandexplorer.GameLogic;
import com.example.lunalandexplorer.View.GameView;

import java.util.Random;

public class Enemy extends Sprite {

    private static final int BMP_ROWS = 1;
    private static final int BMP_COLUMNS = 2;
    private double xSpeed;
    private double ySpeed;
    private int life;

    private int currentFrame;
    private int frameDuration;

    private Spaceship spaceship;

    private Bitmap damagedBitmap;

    public Enemy(GameView gameView, Bitmap bmp, int life, Spaceship spaceship) {
        super(gameView, bmp);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.life = life;
        this.spaceship = spaceship;
        this.damagedBitmap = applyRedOverlay(bmp);

        Random rnd = new Random();

        x = rnd.nextInt(gameView.getWidth() - width);
        y = 0;

        xSpeed = 2;
        ySpeed = 3;
    }

    private Bitmap applyRedOverlay(Bitmap originalBitmap) {
        Bitmap redBitmap = Bitmap.createBitmap(originalBitmap.getWidth(), originalBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(redBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAlpha(100);
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
        paint.setXfermode(new PorterDuffXfermode(mode));

        canvas.drawRect(0, 0, originalBitmap.getWidth(), originalBitmap.getHeight(), paint);

        return redBitmap;
    }

    @Override
    public void update() {
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;
            frameDuration = getMaxFrameDuration();
        }

        int spaceshipX = spaceship.getX() + 50;
        int spaceshipY = spaceship.getY();

        int xDirection = (spaceshipX > x) ? 1 : -1;
        int yDirection = (spaceshipY > y) ? 1 : -1;


        if (x > gameView.getWidth() - width - xSpeed || x + xSpeed < 0) {
            xSpeed = -xSpeed;
        }

        if (y > gameView.getHeight() - height - ySpeed || y + ySpeed < 0) {
            ySpeed = -ySpeed;
        }

        x += xSpeed * xDirection;

        y += ySpeed * yDirection;

    }



    @Override
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    //Si choca con un enemigo que ya no se mueva para los lados.
    public void evitarChocarEnemigos(GameLogic gameLogic) {
        for (Enemy otherEnemy : gameLogic.getEnemies()) {
            if (this != otherEnemy && Rect.intersects(this.getArea(), otherEnemy.getArea())) {
                this.xSpeed = 0;
            }else if(this != otherEnemy && !Rect.intersects(this.getArea(), otherEnemy.getArea())){
                this.xSpeed = 1;
            }else if(this != otherEnemy && Rect.intersects(this.getArea(), otherEnemy.getArea())){
                this.ySpeed = 0;
            }
        }
    }

    public boolean isCollition(Spaceship spaceship) {
        return spaceship.getX() < x + width && spaceship.getX() + spaceship.getWidth() > x && spaceship.getY() < y + height && spaceship.getY() + spaceship.getHeight() > y;
    }


    @Override
    public int getAnimationRow() {
        return 0;
    }

    @Override
    public void kick(int damage) {
        this.setBmp(damagedBitmap);
        life -= damage;
        if (life <= 0) {
           setDeathSprite(true);
        }
    }


    public Rect getArea(){
        return new Rect(x, y, x + width, y + height);
    }

    public int getLife(){
        return life;
    }

    public void setLife(int life){
        this.life = life;
    }
}
