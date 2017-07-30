package io.github.lizhangqu.fresco;

import android.view.View;

/**
 * Tag兼容，可添加多个tag
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-30 13:08
 */
public class TagCompat {

    public static final String KEY_DRAWEE_HOLDER = "DraweeHolder";

    public static void setTag(View view, Object tag) {
        setTag(view, KEY_DRAWEE_HOLDER, tag);
    }

    public static Object getTag(View view) {
        return getTag(view, KEY_DRAWEE_HOLDER);
    }

    public static void setTag(View view, String key, Object tag) {
        com.android.support.application.TagCompat.setTag(view, key, tag);
    }

    public static Object getTag(View view, String key) {
        return com.android.support.application.TagCompat.getTag(view, key);
    }
}
