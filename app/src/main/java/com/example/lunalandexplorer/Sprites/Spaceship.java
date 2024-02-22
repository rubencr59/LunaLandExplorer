package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.health.connect.datatypes.units.Power;

import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

import java.util.ArrayList;
import java.util.List;

public class Spaceship extends Sprite {

    private List<Laser> lasers;

    private boolean disparar = false;
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 4;
    private int normalAnimationRow = 2;
    private int laserStyle = 1;
    private int currentFrame = 0;
    private static final int MAX_FRAME_DURATION = 15;
    private int frameDuration = MAX_FRAME_DURATION;

    private Bitmap bmpLaser;

    private Heart heart;
    private int life = 4;


    public Spaceship(GameView gameView, Bitmap bmp, Heart heart) {
        super(gameView, bmp);
        lasers = new ArrayList<Laser>();
        x = gameView.getWidth() / 2 - width / 2;
        y = gameView.getHeight() - 350;
        this.bmpLaser = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.laser_bolts);

        this.heart = heart;

    }



    @Override
    public int getAnimationRow() {
        return normalAnimationRow;
    }

    public void shoot() {
        if(laserStyle != 99){
            addLaser(new Laser(gameView, bmpLaser, this, laserStyle));
        }else{
            addLaser(new SpecialLaser(gameView, bmpLaser, this, laserStyle));
        }
    }


    public void cogerPowerUp(PowerUp powerUp){
            powerUp.setDeathSprite(true);
            cambiarAnimacion(powerUp.getPowerUpType());
    }


    public void cambiarAnimacion(int tipoPowerUp){
        switch (tipoPowerUp){
            case 1:
                if (normalAnimationRow == 2){
                    normalAnimationRow = 3;
                    laserStyle = 2;
                }
                break;
            case 2:
                if (normalAnimationRow == 3){
                    normalAnimationRow = 1;
                    laserStyle = 3;
                }
                break;
            case 3:
                if (normalAnimationRow == 1){
                    normalAnimationRow = 0;
                    laserStyle = 4;
                }
                break;
            case 4:
                if (normalAnimationRow == 0){
                    normalAnimationRow = 2;
                    laserStyle = 99;
                    this.bmpLaser = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.special_laser);
                }
                break;

        }
    }

    public boolean isCollitionWithPowerUp(PowerUp powerUp){
        return x < powerUp.getX() + powerUp.getWidth() && x + width > powerUp.getX() && y < powerUp.getY() + powerUp.getHeight() && y + height > powerUp.getY();
    }

    public boolean isCollition(float x2, float y2) {
        return x2 > x && x2 < x + width && y2 > y && y2 < y + height;
    }

    public boolean isCollitionWithBossAtack(BossAttack bossAtack){
        return x < bossAtack.getX() + bossAtack.getWidth() && x + width > bossAtack.getX() && y < bossAtack.getY() + bossAtack.getHeight() && y + height > bossAtack.getY();
    }
    @Override
    public void kick(int damage) {
        life -= damage;
        heart.cambiarEstadoHeart();
        if (life <= 0) {
            setDeathSprite(true);
        }
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

    public int getWidth() {
        return width;
    }

    public List<Laser> getLasers() {
        return lasers;
    }



    public void addLaser(Laser laser){
        lasers.add(laser);
    }

    public void removeLaser(Laser laser){
        lasers.remove(laser);
    }

    public void setDisparar(boolean disparar) {
        this.disparar = disparar;
    }

    public boolean getDisparar() {
        return disparar;
    }


}

