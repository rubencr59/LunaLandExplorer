package com.example.lunalandexplorer.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.lunalandexplorer.View.GameView;

import java.util.Random;

public class PowerUp  extends Sprite{
    private static final int BMP_ROWS = 2;
    private static final int BMP_COLUMNS = 2;

    private int currentColumn = 0;

    private int currentRow = 0;

    private int currentFrame = 0;

    private static final int MAX_FRAME_DURATION = 15;

    private int frameDuration = MAX_FRAME_DURATION;

    public PowerUp(GameView gameView, Bitmap bmp, int currentRow, int currentColumn) {
        super(gameView, bmp);
        Random rnd = new Random();
        x = rnd.nextInt(gameView.getWidth() - width);
        y = 0;

        this.currentRow = currentRow;
        this.currentColumn = currentColumn;

        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;

        this.setYSpeed(6);
    }

    @Override
    public void update() {
        y += getySpeed();
        frameDuration--;
        if (frameDuration <= 0) {
            currentFrame = currentFrame % BMP_COLUMNS;
            frameDuration = MAX_FRAME_DURATION;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        update();
        int srcX =  currentColumn * width;
        int srcY =  currentRow * height;
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

    public int getPowerUpType(){
        if(currentRow == 0) {
            if (currentColumn == 0) {
                return 1;
            } else {
                return 2;
            }
        }else if(currentRow == 1){
            if (currentColumn == 0) {
                return 3;
            } else {
                return 4;
            }
        }
        return 0;
    }


}
