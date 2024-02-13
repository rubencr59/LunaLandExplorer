package com.example.lunalandexplorer.Sprites;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.lunalandexplorer.R;
import com.example.lunalandexplorer.View.GameView;

public class Boss extends Sprite {

    private ImageView imageView;

    public Boss(Context context, GameView gameView) {
        super(gameView, null);
        initialize(context);
        this.x = gameView.getWidth() / 2;
        this.y = 0;
    }

    private void initialize(Context context) {
        imageView = new ImageView(context);
        Glide.with(context)
                .asGif()
                .load(R.raw.boss)  // Reemplaza con el nombre de tu archivo GIF
                .placeholder(R.drawable.image)
                .into(new ImageViewTarget<GifDrawable>(imageView) {
                    @Override
                    protected void setResource(GifDrawable resource) {
                        Bitmap bossBitmap = resource.getFirstFrame();
                        setBitmap(bossBitmap);
                        resource.start();
                    }
                });
    }

    public void setBitmap(Bitmap bitmap) {
        this.bmp = bitmap;
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
    }

}

