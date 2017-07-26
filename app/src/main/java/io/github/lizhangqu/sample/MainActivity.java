package io.github.lizhangqu.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.drawable.ScalingUtils;

import io.github.lizhangqu.fresco.FrescoLoader;

public class MainActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrescoLoader.with(view.getContext())
                        .progressiveRenderingEnabled(true)
                        .autoPlayAnimations(true)
                        .autoRotateEnabled(true)
                        .retainImageOnFailure(true)
                        .tapToRetryEnabled(true)
                        .fadeDuration(1000)
                        .border(Color.RED, 10)
                        .cornersRadius(10)
                        .lowerLoad(R.mipmap.ic_launcher_round)
                        .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .load("http://img1.imgtn.bdimg.com/it/u=615670559,766970618&fm=26&gp=0.jpg")
                        .localThumbnailPreviewsEnabled(true)
                        .into(image);
            }
        });

    }
}
