package io.github.lizhangqu.fresco;

import android.view.View;

import java.util.HashMap;

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

    private static final boolean COVER_TAG = true; //是否覆盖

    /**
     * 设置tag
     *
     * @param view   View
     * @param tagKey tag key
     * @param tagObj tag value
     * @return 是否设置成功
     */
    public static boolean setTag(View view, String tagKey, Object tagObj) {
        return setTag(view, tagKey, tagObj, COVER_TAG);
    }

    /**
     * 设置tag
     *
     * @param view        View
     * @param tagKey      tag key
     * @param tagObj      tag value
     * @param overrideTag 是否覆盖非TagMap类型的已设置tag
     * @return 是否设置成功
     */
    public static boolean setTag(View view, String tagKey, Object tagObj, boolean overrideTag) {
        if (view != null) {
            Object tag = view.getTag();
            if (tag instanceof TagMap) {
                ((TagMap) tag).put(tagKey, tagObj);
                return true;
            }
            if (tag == null || overrideTag) {
                view.setTag(new TagMap(tagKey, tagObj));
                return true;
            }
        }
        return false;
    }

    /**
     * 获取tag
     *
     * @param view   View
     * @param tagKey tag key
     * @return tag value
     */
    public static Object getTag(View view, String tagKey) {
        return getTag(view, tagKey, null);
    }

    /**
     * 获取tag
     *
     * @param view         View
     * @param tagKey       tag key
     * @param defaultValue 默认值
     * @return tag value
     */
    public static Object getTag(View view, String tagKey, Object defaultValue) {
        if (view != null) {
            Object tag = view.getTag();
            if (tag instanceof TagMap) {
                Object value = ((TagMap) tag).get(tagKey);
                if (value != null) {
                    return value;
                }
            }
        }
        return defaultValue;
    }

    /**
     * 是否包含tag
     *
     * @param view   View
     * @param tagKey tag key
     * @return 是否含tag
     */
    public static boolean containsTag(View view, String tagKey) {
        if (view != null && tagKey != null) {
            Object tag = view.getTag();
            if (tag instanceof TagMap) {
                return ((TagMap) tag).containsKey(tagKey);
            }
        }
        return false;
    }

    /**
     * 存tag用的map
     */
    private static class TagMap extends HashMap<String, Object> {
        private TagMap(String key, Object value) {
            put(key, value);
        }
    }
}
