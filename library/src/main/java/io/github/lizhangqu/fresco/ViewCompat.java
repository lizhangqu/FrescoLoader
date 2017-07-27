package io.github.lizhangqu.fresco;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;

/**
 * 兼容abs listview attach/detach 不回调，回调onStartTemporaryDetach/onFinishTemporaryDetach问题
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-27 15:28
 */
public class ViewCompat {
    public static void addOnAttachStateChangeListener(View view, View.OnAttachStateChangeListener listener) {
        CompatAttachStateChangeListener.addOnAttachStateChangeListener(view, listener);
    }

    private static class CompatAttachStateChangeListener implements View.OnAttachStateChangeListener, ViewTreeObserver.OnPreDrawListener {
        private View mView;
        private View.OnAttachStateChangeListener mListener;
        private boolean myAttached;
        private boolean yourAttached;

        private static void addOnAttachStateChangeListener(View view, View.OnAttachStateChangeListener listener) {
            new CompatAttachStateChangeListener(view, listener);
        }

        CompatAttachStateChangeListener(View view, View.OnAttachStateChangeListener listener) {
            mView = view;
            mListener = listener;
            myAttached = isAttachedToWindow(mView);
            yourAttached = false;
            if (myAttached) mView.getViewTreeObserver().addOnPreDrawListener(this);
            mView.addOnAttachStateChangeListener(this);
            update();
        }

        @Override
        public void onViewAttachedToWindow(View v) {
            if (myAttached) return;
            myAttached = true;
            mView.getViewTreeObserver().addOnPreDrawListener(this);
            update();
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            if (!myAttached) return;
            myAttached = false;
            mView.getViewTreeObserver().removeOnPreDrawListener(this);
            update();
        }

        @Override
        public boolean onPreDraw() {
            update();
            return true;
        }

        private void update() {
            boolean attached = attach();
            if (yourAttached != attached) {
                yourAttached = attached;
                if (yourAttached) {
                    mListener.onViewAttachedToWindow(mView);
                } else {
                    mListener.onViewDetachedFromWindow(mView);
                }
            }
        }

        private boolean attach() {
            if (myAttached) {
                View root = mView;
                while (true) {
                    ViewParent parent = root.getParent();
                    if (!(parent instanceof View)) break;
                    root = (View) parent;
                }
                if (root == mView.getRootView()) return true;
            }
            return false;
        }

        private boolean isAttachedToWindow(View view) {
            if (Build.VERSION.SDK_INT >= 19) {
                return view.isAttachedToWindow();
            } else {
                return view.getWindowToken() != null;
            }
        }
    }
}
