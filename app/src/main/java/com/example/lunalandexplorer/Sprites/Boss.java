package com.example.lunalandexplorer.Sprites;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Boss extends Enemy {

    private static final int BMP_ROWS = 5;
    private static final int BMP_COLUMNS = 5;
    private static final int MAX_FRAME_DURATION = 15;
    private int frameDuration = MAX_FRAME_DURATION;
    private int currentFrame;
    private int movesPerRow = 5;
    private int movesCount = 0;
    private int totalRows = 5;
    private int currentRow;

    private int speedX = 3;

    private int life = 100;

    private int maxFrameDuration;

    private Bitmap damagedBitmap;

    private boolean deathSprite = false;

    private List<BossAttack> bossAttacks = new ArrayList<>();

    public Boss (GameView gameView, Bitmap bmp, int life, Spaceship spaceship) {
        super(gameView, bmp  , life, spaceship);
        this.x = gameView.getWidth() / 2 - bmp.getWidth() / BMP_COLUMNS / 2;
        this.y = 0;
        this.damagedBitmap = aplicarCapaRoja(bmp);
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.currentRow = 0;
        this.maxFrameDuration = 999999999;
    }

    @Override
    public void update() {
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;
            frameDuration = maxFrameDuration;
        }
        if (++movesCount >= movesPerRow) {
            movesCount = 0;
            currentRow = (currentRow + 1) % totalRows;
            currentFrame = 0;
        }

        if (x > gameView.getWidth() - width - speedX || x + speedX < 0) {
            speedX = -speedX;
        }

        x += speedX;

        Random rnd = new Random();
        if (rnd.nextInt(40) == 0) {
            shoot();
        }
    }

    private Bitmap aplicarCapaRoja(Bitmap originalBitmap) {
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
    public void onDraw(Canvas canvas) {
        update();
        int srcX = currentFrame * width;
        int srcY = currentRow * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    @Override
    public void kick(int damage) {
        this.setBmp(damagedBitmap);
        life -= damage;
        if (life <= 0) {
            setDeathSprite(true);
        }
    }

    public void shoot() {
        Bitmap bmpAttack = BitmapFactory.decodeResource(gameView.getResources(), R.drawable.laser_bolts);
            bossAttacks.add(new BossAttack(gameView, bmpAttack, this, 1));
    }


    public List<BossAttack> getBossAttacks() {
        return bossAttacks;
    }

}

