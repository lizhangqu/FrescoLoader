package io.github.lizhangqu.sample;

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
                        .resize(200, 200)
                        .scaleType(ScalingUtils.ScaleType.FIT_CENTER)
                        .load("http://img1.imgtn.bdimg.com/it/u=615670559,766970618&fm=26&gp=0.jpg")
                        .into(image);
            }
        });

    }
}
