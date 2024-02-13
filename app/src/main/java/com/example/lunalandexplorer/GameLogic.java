package com.example.lunalandexplorer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.lunalandexplorer.Sprites.Boss;
import com.example.lunalandexplorer.Sprites.Enemy;
import com.example.lunalandexplorer.Sprites.Laser;
import com.example.lunalandexplorer.Sprites.PowerUp;
import com.example.lunalandexplorer.Sprites.Spaceship;
import com.example.lunalandexplorer.Sprites.TempSprite;
import com.example.lunalandexplorer.View.GameView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private Spaceship spaceship;
    private List<Enemy> enemies;

    private List<PowerUp> powerUps;

    private List<TempSprite> temps;
    private GameView gameView;
    private int numeroEnemigos = 3;

    private int nivel = 1;


    public GameLogic(GameView gameView) {
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        temps = new ArrayList<>();
        this.gameView = gameView;
        createSpaceship();
        crearEnemigos();
    }

    private void createSpaceship() {
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.spaceship);
        this.spaceship =  new Spaceship(gameView, bitmap);
    }


    private Enemy generarEnemigo(int resource, int life){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), resource);
        return new Enemy(gameView, bitmap, life, spaceship);
    }

    private TempSprite generarExplosion(int x, int y){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.explosion);
        return new TempSprite(gameView, bitmap, x, y);
    }

    private PowerUp generarPowerUp(int powerUpRow, int powerUpColumn){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.power_up);
        return new PowerUp(gameView, bitmap, powerUpRow, powerUpColumn);
    }

    private void crearEnemigos() {
        Random numeroRandom = new Random();

        if(enemies.isEmpty()){
            for(int i = 0; i < numeroEnemigos; i++){
                if(numeroRandom.nextInt(2) == 0){
                    enemies.add(generarEnemigo(R.drawable.enemy_medium,2));
                }else{
                    enemies.add(generarEnemigo(R.drawable.enemy_big,4));
                }
            }
            numeroEnemigos += 2;
        }
    }

    public void crearPowerUp(){
        switch (nivel){
            case 3:
                powerUps.add(generarPowerUp(0, 0));
                break;
            case 6:
                powerUps.add(generarPowerUp(0, 1));
                break;
            case 9:
                powerUps.add(generarPowerUp(1, 0));
                break;
            case 12:
                powerUps.add(generarPowerUp(1, 1));
                break;
        }
    }

    public void update() {
        spaceship.update();
        for (Laser laser : spaceship.getLasers()) {
            laser.update();
        }
        for (Enemy enemy : enemies) {
            enemy.update();

        }
        for(TempSprite temp : temps){
            temp.update();
        }

        for (PowerUp powerUp : powerUps) {
            powerUp.update();
        }



    }

    public void checkCollisions() {
        Iterator<Enemy> enemyIterator = enemies.iterator();
        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();

            Iterator<Laser> laserIterator = spaceship.getLasers().iterator();
            while (laserIterator.hasNext()) {
                Laser laser = laserIterator.next();

                if (laser.isCollition(enemy)) {
                    enemy.kick(laser.getDamage());
                    laserIterator.remove();
                    if(enemy.isDeathSprite()){
                        temps.add(generarExplosion(enemy.getX(), enemy.getY()));
                        enemyIterator.remove();
                        if(enemies.isEmpty()){
                            siguienteNivel();
                        }
                    }
                    break;
                }
            }
        }
    }

    public void checkPowerUpCollisions() {
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if(spaceship.isCollitionWithPowerUp(powerUp)){
                spaceship.cogerPowerUp(powerUp);
                powerUpIterator.remove();
            }
        }
    }

    public void siguienteNivel(){
        nivel++;
        if(nivel == 2){
            gameView.setBackgroundResource(R.drawable.inferno);
        }
        crearEnemigos();
        crearPowerUp();
    }

    public void checkGameOver() {
        for (Enemy enemy : enemies) {
            if (enemy.getY() == spaceship.getY() || enemy.isCollition(spaceship)) {
                spaceship.setDeathSprite(true);
            }
        }

        if(spaceship.isDeathSprite()){
            gameView.gameOver();
        }
    }

    public void startShooting() {
        final Handler handler = new Handler();
        final int delayMillis = 500;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (spaceship.getDisparar()) {
                    spaceship.shoot(BitmapFactory.decodeResource(gameView.getResources(), R.drawable.laser_bolts));
                    handler.postDelayed(this, delayMillis);
                }
            }
        }, delayMillis);
    }




    public Spaceship getSpaceship() {
        return spaceship;
    }


    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<TempSprite> getTemps() {
        return temps;
    }

    public List<PowerUp> getPowerUps() {
        return powerUps;
    }






}