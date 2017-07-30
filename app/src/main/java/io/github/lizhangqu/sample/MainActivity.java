package io.github.lizhangqu.sample;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.github.lizhangqu.fresco.FrescoLoader;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private ViewGroup parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        parent = (ViewGroup) image.getParent();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick");
            }
        });

        final List<Drawable> bgs = new ArrayList<Drawable>();
        bgs.add(ContextCompat.getDrawable(this, R.mipmap.bg_zero));
        bgs.add(ContextCompat.getDrawable(this, R.mipmap.bg_one));
        bgs.add(ContextCompat.getDrawable(this, R.mipmap.bg_two));

        final List<Drawable> overlays = new ArrayList<Drawable>();
        overlays.add(new ColorDrawable(Color.parseColor("#77000000")));

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "";
                if (view.getTag() != null) {
                    view.setTag(null);
                    url = "";
                } else {
                    view.setTag("");
                    url = "http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg";
                }
                FrescoLoader.with(view.getContext())
                        .progressiveRenderingEnabled(true)
                        .fadeDuration(2000)
                        .autoPlayAnimations(true)
                        .autoRotateEnabled(true)
                        .retainImageOnFailure(true)
//                        .desiredAspectRatioWithHeight(0.5F)
                        .tapToRetryEnabled(true)
                        .focusPoint(new PointF(30, 50))
                        .resize(400, 400)
                        .fadeDuration(1000)
                        .border(Color.RED, 10)
                        .borderColor(Color.RED)
                        .borderWidth(10)
                        .cornersRadii(10, 10, 10, 10)
                        .cornersRadius(10)
//                        .roundAsCircle()
                        .backgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.bg_zero))
                        .progressBar(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_progress_bar))
                        .progressBarScaleType(ImageView.ScaleType.CENTER_CROP)
                        .placeholder(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_placeholder))
                        .placeholderScaleType(ImageView.ScaleType.CENTER_CROP)
                        .failure(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_failure))
                        .failureScaleType(ImageView.ScaleType.CENTER_CROP)
                        .retry(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_retry))
                        .retryScaleType(ImageView.ScaleType.CENTER_CROP)
//                        .colorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN))
                        .overlays(overlays)
                        .pressedStateOverlay(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.bg_one))
                        .actualScaleType(ImageView.ScaleType.CENTER_CROP)
                        .lowerLoad(R.mipmap.ic_launcher_round)
                        .load(url)//fail
//                        .load("http://photocdn.sohu.com/20160208/mp58375678_1454886851667_2.gif")//gif
//                        .load("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg")
                        .localThumbnailPreviewsEnabled(true)
                        .into(image);
            }
        });

        findViewById(R.id.remove_and_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeView(image);
                parent.addView(image);
            }
        });

        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.removeView(image);
            }
        });

        findViewById(R.id.listview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivity.class));
            }
        });

    }
}
