package io.github.lizhangqu.sample;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
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
                        .focusPoint(new PointF(30, 50))
                        .resize(400, 400)
                        .fadeDuration(1000)
                        .border(Color.RED, 10)
                        .cornersRadii(10, 10, 10, 10)
                        .cornersRadius(10)
                        .roundAsCircle()
                        .progressBar(new CircleProgressBarDrawable())
                        .progressScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .placeholderScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .failure(R.mipmap.ic_launcher_round)
                        .failureScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .retry(R.mipmap.ic_launcher_round)
                        .retryScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .colorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN))
                        .pressedStateOverlay(R.mipmap.ic_launcher_round)
                        .overlay(R.mipmap.ic_launcher_round)
                        .background(new ColorDrawable(Color.parseColor("#ee000000")))
                        .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
                        .lowerLoad(R.mipmap.ic_launcher_round)
                        .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
//                        .load("")//fail
                        .load("http://photocdn.sohu.com/20160208/mp58375678_1454886851667_2.gif")//gif
                        .load("http://img1.imgtn.bdimg.com/it/u=615670559,766970618&fm=26&gp=0.jpg")
                        .localThumbnailPreviewsEnabled(true)
                        .into(image);
            }
        });

    }
}
