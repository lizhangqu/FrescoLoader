package io.github.lizhangqu.sample;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import io.github.lizhangqu.fresco.FrescoLoader;

/**
 * 如果你继承了ImageView，并且能够增加方法，你最好添加如下代码，当然这不是必要的。
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-27 09:11
 */
public class MyImageView extends ImageView implements FrescoLoader.TemporaryDetachListener {
    FrescoLoader.TemporaryDetachListener listener;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        if (this.listener != null) {
            listener.onStartTemporaryDetach(this);
        }

    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        if (this.listener != null) {
            listener.onFinishTemporaryDetach(this);
        }
    }

    @Override
    public void onSaveTemporaryDetachListener(FrescoLoader.TemporaryDetachListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStartTemporaryDetach(View view) {
        //empty
    }

    @Override
    public void onFinishTemporaryDetach(View view) {
        //empty
    }
}
