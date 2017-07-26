package io.github.lizhangqu.sample;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 功能介绍
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-26 15:31
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFresco();
    }

    private void initFresco() {
        Fresco.initialize(this);
    }
}
