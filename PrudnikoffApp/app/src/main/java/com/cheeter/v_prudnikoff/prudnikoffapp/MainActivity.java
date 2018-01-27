package com.cheeter.v_prudnikoff.prudnikoffapp;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView avatarImageView;
    private ImageHelper imageHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageHelper = new ImageHelper(this);
        avatarImageView = findViewById(R.id.avatarImageView);
        int size = imageHelper.getScreenWidth() / 2;
        avatarImageView.getLayoutParams().height = size;
        avatarImageView.getLayoutParams().width = size;
        avatarImageView.setImageBitmap(imageHelper.getRoundedCornerBitmap(imageHelper
                .getBitmapFromAssets(getString(R.string.avatar)), 600));
    }

}
