package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;

public class Heart extends Sprite{

    private final int BMP_COLUMN = 5;

    private int currentColumn = 0;

    private static final int MAX_FRAME_DURATION = 15;

    private int frameDuration = MAX_FRAME_DURATION;
    private int currentFrame = 0;

    public Heart(GameView gameView, Bitmap bmp) {
        super(gameView, bmp);
        x = gameView.getWidth() -100;
        y = 0 + 100;

        this.width = bmp.getWidth() / BMP_COLUMN;

        this.height = bmp.getHeight();


    }

    @Override
    public void update() {
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = currentFrame % this.BMP_COLUMN;
            frameDuration = MAX_FRAME_DURATION;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.update();
        int srcX = currentFrame * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public void cambiarEstadoHeart(){
        currentFrame++;
    }

}
