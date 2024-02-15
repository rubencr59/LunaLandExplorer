package com.example.lunalandexplorer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.example.lunalandexplorer.Activities.GameActivity;
import com.example.lunalandexplorer.Sprites.Boss;
import com.example.lunalandexplorer.Sprites.BossAttack;
import com.example.lunalandexplorer.Sprites.Enemy;
import com.example.lunalandexplorer.Sprites.Heart;
import com.example.lunalandexplorer.Sprites.Laser;
import com.example.lunalandexplorer.Sprites.PowerUp;
import com.example.lunalandexplorer.Sprites.Spaceship;
import com.example.lunalandexplorer.Sprites.TempSprite;
import com.example.lunalandexplorer.View.GameView;
import com.example.lunalandexplorer.service.SelectSpaceShipService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GameLogic {

    private Spaceship spaceship;
    private List<Enemy> enemies;

    private List<PowerUp> powerUps;

    private List<TempSprite> temps;


    private Boss boss;
    private GameView gameView;

    private Heart heart;
    private int numeroEnemigos = 3;

    private int nivel = 1;

    private GameActivity gameActivity;

    private int enemyLifeMedium = 2;


    public GameLogic(GameView gameView, GameActivity gameActivity) {
        enemies = new ArrayList<>();
        powerUps = new ArrayList<>();
        temps = new ArrayList<>();
        this.gameView = gameView;
        createSpaceship();
        crearEnemigos(2, 4);
        this.gameActivity = gameActivity;

    }

    private void createSpaceship() {
        int modelo = SelectSpaceShipService.getSpaceshipSelected();
        if(modelo == 0){
            modelo = R.drawable.spaceship;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), modelo);
        this.spaceship =  new Spaceship(gameView, bitmap);

        Bitmap heartBitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.heart_animated_1);
        this.heart = new Heart(gameView, heartBitmap);
    }

    private Enemy generarEnemigo(int resource, int life){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), resource);
        return new Enemy(gameView, bitmap, life, spaceship);
    }

    private Boss generarBoss(){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.boss);
        return new Boss(gameView, bitmap,50, spaceship);
    }

    private TempSprite generarExplosion(int x, int y){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.explosion);
        return new TempSprite(gameView, bitmap, x, y);
    }

    private PowerUp generarPowerUp(int powerUpRow, int powerUpColumn){
        Bitmap bitmap = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.power_up);
        return new PowerUp(gameView, bitmap, powerUpRow, powerUpColumn);
    }

    private void crearEnemigos(int enemyLifeMediug, int enemyLifeBig) {
        Random numeroRandom = new Random();

        if(enemies.isEmpty()){
            for(int i = 0; i < numeroEnemigos; i++){
                if(numeroRandom.nextInt(2) == 0){
                    enemies.add(generarEnemigo(R.drawable.enemy_medium,enemyLifeMediug));
                }else{
                    enemies.add(generarEnemigo(R.drawable.enemy_big,enemyLifeBig));
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
        heart.update();
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

        if(boss != null){
            for (BossAttack bossAttack : boss.getBossAttacks()) {
                bossAttack.update();
            }
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
                        gameActivity.setScore(1);
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

    public void checkBossCollisions() {
        if(boss != null){
            Iterator<BossAttack> bossAttackIterator = boss.getBossAttacks().iterator();
            while (bossAttackIterator.hasNext()) {
                BossAttack bossAttack = bossAttackIterator.next();
                if(bossAttack.isCollition(spaceship)){
                    spaceship.kick(bossAttack.getDamage());
                    bossAttackIterator.remove();
                }
            }
        }
    }

    public void siguienteNivel(){
        nivel++;

        if(nivel == 6){
            gameView.setBackgroundResource(R.drawable.desert_background);
            enemyLifeMedium = enemyLifeMedium + 2;
        }else if(nivel == 12){
            numeroEnemigos = 0;
            boss = generarBoss();
            gameView.setBackgroundResource(R.drawable.inferno);
        }
        crearEnemigos(enemyLifeMedium, enemyLifeMedium + 1);
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

    public void gameOver(){
        gameActivity.createGameOverLayout();
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

    public Boss getBoss(){
        return boss;
    }

    public Heart getHeart() {
        return heart;
    }


}