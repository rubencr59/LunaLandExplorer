package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;

public class TempSprite extends Sprite{

    private static final int BMP_ROWS = 1;
    private static final int BMP_COLUMNS = 5;

    private int currentFrame;
    private int maxFrames;
    private int frameDuration;

    public TempSprite(GameView gameView, Bitmap bmp, int x, int y) {
        super(gameView, bmp);
        this.x = x;
        this.y = y;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.maxFrames = BMP_COLUMNS;
        this.frameDuration = 5;
    }

    @Override
    public int getAnimationRow() {
        return 0;
    }

    @Override
    public void kick(int damage) {
        // No necesitas implementar nada aquí para un sprite temporal de explosión
    }

    @Override
    public void update() {
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame++;
            frameDuration = 5;
        }

        if (currentFrame >= maxFrames) {
            setDeathSprite(true);
        }
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
    }