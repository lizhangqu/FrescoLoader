//Copyright 2017 区长. All rights reserved.
//
//Redistribution and use in source and binary forms, with or without
//modification, are permitted provided that the following conditions are
//met:
//
//* Redistributions of source code must retain the above copyright
//notice, this list of conditions and the following disclaimer.
//* Redistributions in binary form must reproduce the above
//copyright notice, this list of conditions and the following disclaimer
//in the documentation and/or other materials provided with the
//distribution.
//* Neither the name of Google Inc. nor the names of its
//contributors may be used to endorse or promote products derived from
//this software without specific prior written permission.
//
//THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
//"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
//LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
//A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
//OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
//SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
//LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
//DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
//THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
//(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
//OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
package io.github.lizhangqu.fresco;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.DraweeHierarchy;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * fresco图片加载器, https://www.fresco-cn.org/docs/
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-26 13:16
 */
public class FrescoLoader {
    private Context mContext;
    private DraweeHolderDispatcher mDraweeHolderDispatcher;
    private DraweeHolder<DraweeHierarchy> mDraweeHolder;
    private Postprocessor mPostprocessor;
    private ControllerListener mControllerListener;

    private Uri mUri;
    private Uri mLowerUri;
    private ResizeOptions mResizeOptions;
    private float mDesiredAspectRatio;
    private boolean mUseFixedWidth;
    private boolean mAutoRotateEnabled = false;

    private int mFadeDuration;
    private Drawable mPlaceholderDrawable;
    private Drawable mRetryDrawable;
    private Drawable mFailureDrawable;
    private Drawable mProgressBarDrawable;
    private Drawable mBackgroundDrawable;

    /**
     * 缩放方式
     *
     * @param scaleType 支持以下类型参数
     * center 居中无缩放
     * centerCrop 保持宽高比缩小或放大，使得两边都大于或等于显示边界。居中显示
     * focusCrop 同centerCrop, 但居中点不是中点，而是指定的某个点
     * centerInside 使两边都在显示边界内，居中显示。如果图尺寸大于显示边界，则保持长宽比缩小图片。
     * fitCenter 保持宽高比，缩小或者放大，使得图片完全显示在显示边界内。居中显示
     * fitStart 同上。但不居中，和显示边界左上对齐
     * fitEnd 同fitCenter， 但不居中，和显示边界右下对齐
     * fitXY 不保存宽高比，填充满显示边界
     * none 如要使用tile mode显示, 需要设置为none
     */
    private ScalingUtils.ScaleType mPlaceholderScaleType;
    private ScalingUtils.ScaleType mRetryScaleType;
    private ScalingUtils.ScaleType mFailureScaleType;
    private ScalingUtils.ScaleType mProgressScaleType;

    private ScalingUtils.ScaleType mActualImageScaleType;
    private PointF mActualImageFocusPoint;
    private ColorFilter mActualImageColorFilter;
    private RoundingParams mRoundingParams;

    private List<Drawable> mOverlays;
    private Drawable mPressedStateOverlay;

    private boolean mTapToRetryEnabled;
    private boolean mAutoPlayAnimations;
    private boolean mRetainImageOnFailure;
    private boolean mProgressiveRenderingEnabled;
    private boolean mLocalThumbnailPreviewsEnabled;

    private FrescoLoader(Context context) {
        this.mContext = context;
        this.mDraweeHolderDispatcher = new DraweeHolderDispatcher();

        this.mDesiredAspectRatio = 0;
        this.mUseFixedWidth = true;
        this.mFadeDuration = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;

        this.mPlaceholderDrawable = null;
        this.mPlaceholderScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        this.mRetryDrawable = null;
        this.mRetryScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        this.mFailureDrawable = null;
        this.mFailureScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        this.mProgressBarDrawable = null;
        this.mProgressScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        this.mActualImageScaleType = GenericDraweeHierarchyBuilder.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
        this.mActualImageFocusPoint = null;
        this.mActualImageColorFilter = null;

        this.mBackgroundDrawable = null;
        this.mOverlays = null;
        this.mPressedStateOverlay = null;
        this.mRoundingParams = null;

        this.mTapToRetryEnabled = false;
        this.mAutoPlayAnimations = false;
        this.mRetainImageOnFailure = false;
        this.mProgressiveRenderingEnabled = false;
        this.mLocalThumbnailPreviewsEnabled = false;

        this.mPostprocessor = null;
        this.mControllerListener = null;
        this.mDraweeHolder = DraweeHolder.create(null, mContext);
    }

    //****************context start*******************
    public static FrescoLoader with(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context == null");
        }
        return new FrescoLoader(context);
    }
    //****************context start*******************

    //****************load start*******************
    public FrescoLoader load(Uri uri) {
        this.mUri = uri;
        return this;
    }

    public FrescoLoader load(String uri) {
        return load(Uri.parse(uri));
    }

    public FrescoLoader load(File file) {
        return load(Uri.fromFile(file));
    }

    public FrescoLoader load(int resourceId) {
        return load(
                new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(resourceId))
                        .build()
        );
    }

    //****************load end*******************


    //**************lowerLoad start**************
    public FrescoLoader lowerLoad(Uri uri) {
        this.mLowerUri = uri;
        return this;
    }

    public FrescoLoader lowerLoad(String uri) {
        return lowerLoad(Uri.parse(uri));
    }

    public FrescoLoader lowerLoad(File file) {
        return lowerLoad(Uri.fromFile(file));
    }

    public FrescoLoader lowerLoad(int resourceId) {
        return lowerLoad(
                new Uri.Builder()
                        .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                        .path(String.valueOf(resourceId))
                        .build()
        );
    }
    //**************lowerLoad end****************

    //**************drawable and scaleType start****************
    public FrescoLoader placeholder(Drawable placeholderDrawable) {
        this.mPlaceholderDrawable = placeholderDrawable;
        return this;
    }

    public FrescoLoader placeholder(int placeholderResId) {
        return placeholder(this.mContext.getResources().getDrawable(placeholderResId));
    }

    public FrescoLoader placeholderScaleType(ImageView.ScaleType scaleType) {
        this.mPlaceholderScaleType = convertToFrescoScaleType(scaleType, GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE);
        return this;
    }

    public FrescoLoader retry(Drawable retryDrawable) {
        this.mRetryDrawable = retryDrawable;
        return this;
    }

    public FrescoLoader retry(int retryResId) {
        return retry(this.mContext.getResources().getDrawable(retryResId));
    }

    public FrescoLoader retryScaleType(ImageView.ScaleType scaleType) {
        this.mRetryScaleType = convertToFrescoScaleType(scaleType, GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE);
        return this;
    }

    public FrescoLoader failure(Drawable failureDrawable) {
        this.mFailureDrawable = failureDrawable;
        return this;
    }

    public FrescoLoader failure(int failureResId) {
        return failure(this.mContext.getResources().getDrawable(failureResId));
    }

    public FrescoLoader failureScaleType(ImageView.ScaleType scaleType) {
        this.mFailureScaleType = convertToFrescoScaleType(scaleType, GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE);
        return this;
    }

    public FrescoLoader progressBar(Drawable placeholderDrawable) {
        this.mProgressBarDrawable = placeholderDrawable;
        return this;
    }

    public FrescoLoader progressBar(int progressResId) {
        return progressBar(this.mContext.getResources().getDrawable(progressResId));
    }

    public FrescoLoader progressBarScaleType(ImageView.ScaleType scaleType) {
        this.mPlaceholderScaleType = convertToFrescoScaleType(scaleType, GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE);
        return this;
    }

    public FrescoLoader backgroundDrawable(Drawable backgroundDrawable) {
        this.mBackgroundDrawable = backgroundDrawable;
        return this;
    }

    public FrescoLoader backgroundDrawable(int backgroundResId) {
        return backgroundDrawable(this.mContext.getResources().getDrawable(backgroundResId));
    }

    public FrescoLoader actualScaleType(ImageView.ScaleType scaleType) {
        this.mActualImageScaleType = convertToFrescoScaleType(scaleType, GenericDraweeHierarchyBuilder.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE);
        return this;
    }

    public FrescoLoader focusPoint(PointF focusPoint) {
        this.mActualImageFocusPoint = focusPoint;
        return this;
    }
    //**************drawable and scaleType end****************


    //**************overlays start****************
    public FrescoLoader overlays(List<Drawable> overlays) {
        this.mOverlays = overlays;
        return this;
    }

    public FrescoLoader overlay(Drawable overlay) {
        return overlays(overlay == null ? null : Collections.singletonList(overlay));
    }

    public FrescoLoader overlay(int resId) {
        return overlay(this.mContext.getResources().getDrawable(resId));
    }

    public FrescoLoader pressedStateOverlay(Drawable drawable) {
        if (drawable == null) {
            this.mPressedStateOverlay = null;
        } else {
            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawable);
            this.mPressedStateOverlay = stateListDrawable;
        }
        return this;
    }

    public FrescoLoader pressedStateOverlay(int resId) {
        return pressedStateOverlay(this.mContext.getResources().getDrawable(resId));
    }

    //**************overlays end****************

    //**************colorFilter start****************
    public FrescoLoader colorFilter(ColorFilter colorFilter) {
        this.mActualImageColorFilter = colorFilter;
        return this;
    }
    //**************colorFilter end****************

    //***************RoundingParams start****************

    public FrescoLoader cornersRadius(int radius) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setCornersRadius(radius);
        return this;
    }

    public FrescoLoader border(int borderColor, float borderWidth) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setBorder(borderColor, borderWidth);
        return this;
    }

    public FrescoLoader borderColor(int borderColor) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setBorderColor(borderColor);
        return this;
    }

    public FrescoLoader borderWidth(float borderWidth) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setBorderWidth(borderWidth);
        return this;
    }

    public FrescoLoader roundAsCircle() {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setRoundAsCircle(true);
        return this;
    }

    public FrescoLoader cornersRadii(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
        return this;
    }

    public FrescoLoader cornersRadii(float[] radii) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setCornersRadii(radii);
        return this;
    }

    public FrescoLoader overlayColor(int overlayColor) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setOverlayColor(overlayColor);
        return this;
    }

    public FrescoLoader padding(float padding) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setPadding(padding);
        return this;
    }


    public FrescoLoader roundingMethodWithOverlayColor() {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);
        return this;
    }

    public FrescoLoader roundingMethodWithBitmapOnly() {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        return this;
    }

    //***************RoundingParams end****************

    //***************resize start****************
    public FrescoLoader resize(Point point) {
        this.mResizeOptions = new ResizeOptions(point.x, point.y);
        return this;
    }

    public FrescoLoader resize(int targetWidth, int targetHeight) {
        this.mResizeOptions = new ResizeOptions(targetWidth, targetHeight);
        return this;
    }
    //***************resize end****************

    //***************fadeDuration start****************
    public FrescoLoader fadeDuration(int fadeDuration) {
        this.mFadeDuration = fadeDuration;
        return this;
    }
    //***************fadeDuration end****************

    //***************desiredAspectRatio start****************
    public FrescoLoader desiredAspectRatioWithWidth(float desiredAspectRatio) {
        this.mUseFixedWidth = true;
        this.mDesiredAspectRatio = desiredAspectRatio;
        return this;
    }

    public FrescoLoader desiredAspectRatioWithHeight(float desiredAspectRatio) {
        this.mUseFixedWidth = false;
        this.mDesiredAspectRatio = desiredAspectRatio;
        return this;
    }

    //***************desiredAspectRatio end****************

    //***************boolean start****************
    public FrescoLoader autoRotateEnabled(boolean enabled) {
        this.mAutoRotateEnabled = enabled;
        return this;
    }

    public FrescoLoader autoPlayAnimations(boolean enabled) {
        this.mAutoPlayAnimations = enabled;
        return this;
    }

    public FrescoLoader retainImageOnFailure(boolean enabled) {
        this.mRetainImageOnFailure = enabled;
        return this;
    }

    public FrescoLoader progressiveRenderingEnabled(boolean enabled) {
        this.mProgressiveRenderingEnabled = enabled;
        return this;
    }

    public FrescoLoader localThumbnailPreviewsEnabled(boolean enabled) {
        this.mLocalThumbnailPreviewsEnabled = enabled;
        return this;
    }

    public FrescoLoader tapToRetryEnabled(boolean tapToRetryEnabled) {
        this.mTapToRetryEnabled = tapToRetryEnabled;
        return this;
    }
    //***************boolean end****************

    //use fresco class method
    //you'd better not use
    @Deprecated
    public FrescoLoader postProcessor(Postprocessor postProcessor) {
        this.mPostprocessor = postProcessor;
        return this;
    }

    //you'd better not use
    @Deprecated
    public FrescoLoader controllerListener(ControllerListener controllerListener) {
        this.mControllerListener = controllerListener;
        return this;
    }


    //load into an ImageView
    public void into(ImageView targetView) {
        if (targetView == null) {
            return;
        }
        if (mDraweeHolder == null) {
            return;
        }
        if (mUri == null) {
            return;
        }
        //build hierarchy
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(mContext.getResources())
                .setPlaceholderImage(mPlaceholderDrawable)
                .setPlaceholderImageScaleType(mPlaceholderScaleType)
                .setFailureImage(mFailureDrawable)
                .setFailureImageScaleType(mFailureScaleType)
                .setProgressBarImage(mProgressBarDrawable)
                .setProgressBarImageScaleType(mProgressScaleType)
                .setRetryImage(mRetryDrawable)
                .setRetryImageScaleType(mRetryScaleType)
                .setFadeDuration(mFadeDuration)
                .setActualImageFocusPoint(mActualImageFocusPoint)
                .setActualImageColorFilter(mActualImageColorFilter)
                .setActualImageScaleType(mActualImageScaleType)
                .setBackground(mBackgroundDrawable)
                .setOverlays(mOverlays)
                .setPressedStateOverlay(mPressedStateOverlay)
                .setRoundingParams(mRoundingParams)
                .setDesiredAspectRatio(mDesiredAspectRatio)
                .build();

        //set hierarchy
        mDraweeHolder.setHierarchy(hierarchy);

        //image request
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(mUri)
                .setAutoRotateEnabled(mAutoRotateEnabled)
                .setLocalThumbnailPreviewsEnabled(mLocalThumbnailPreviewsEnabled)
                .setPostprocessor(mPostprocessor)
                .setProgressiveRenderingEnabled(mProgressiveRenderingEnabled)
                .setResizeOptions(mResizeOptions)
                .build();

        //controller
        PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(mAutoPlayAnimations)
                .setControllerListener(mControllerListener)
                .setImageRequest(request)
                .setOldController(mDraweeHolder.getController())
                .setRetainImageOnFailure(mRetainImageOnFailure)
                .setTapToRetryEnabled(mTapToRetryEnabled);


        //if set the mLowerUri, then pass this param
        if (mLowerUri != null) {
            controllerBuilder.setLowResImageRequest(ImageRequest.fromUri(mLowerUri));
        }
        //build controller
        DraweeController draweeController = controllerBuilder.build();
        //set controller
        mDraweeHolder.setController(draweeController);

        //if targetView is instanceof TemporaryDetachListener, set TemporaryDetachListener
        //in your will, you should override onSaveTemporaryDetachListener(TemporaryDetachListener l) to holder the param TemporaryDetachListener.
        //also override method onStartTemporaryDetach() and onFinishTemporaryDetach() to call the holder's onStartTemporaryDetach() and onFinishTemporaryDetach()
        if (targetView instanceof TemporaryDetachListener) {
            ((TemporaryDetachListener) targetView).onSaveTemporaryDetachListener(mDraweeHolderDispatcher);
        }

        //remove listener if needed
        targetView.removeOnAttachStateChangeListener(mDraweeHolderDispatcher);
        //if is already attached, call method onViewAttachedToWindow.
        if (isAttachedToWindow(targetView)) {
            mDraweeHolderDispatcher.onViewAttachedToWindow(targetView);
        }
        //add attach state change listener
        targetView.addOnAttachStateChangeListener(mDraweeHolderDispatcher);
        //if is enable retry when fail, set OnTouchListener to intercept the touch event
        if (mTapToRetryEnabled) {
            targetView.setOnTouchListener(mDraweeHolderDispatcher);
        }
        //set image drawable
        targetView.setImageDrawable(mDraweeHolder.getTopLevelDrawable());

        //compat for desiredAspectRatio
        if (mDesiredAspectRatio != 0) {
            ViewGroup.LayoutParams layoutParams = targetView.getLayoutParams();
            if (layoutParams != null) {
                int width = layoutParams.width;
                int height = layoutParams.height;
                int newWidth = -1;
                int newHeight = -1;
                //mDesiredAspectRatio= width/height;
                if (mUseFixedWidth) {
                    //with must > 0
                    if (width > 0) {
                        newWidth = width;
                        newHeight = (int) (width * 1.0 / mDesiredAspectRatio + 0.5);
                    }
                } else {
                    //height must > 0
                    if (height > 0) {
                        newHeight = height;
                        newWidth = (int) (height * mDesiredAspectRatio + 0.5);
                    }
                }
                if (newWidth != -1 && newHeight != -1) {
                    layoutParams.width = newWidth;
                    layoutParams.height = newHeight;
                    targetView.requestLayout();
                }
            }
        }

    }

    private static boolean isAttachedToWindow(View view) {
        if (Build.VERSION.SDK_INT >= 19) {
            return view.isAttachedToWindow();
        } else {
            return view.getWindowToken() != null;
        }
    }

    private static ScalingUtils.ScaleType convertToFrescoScaleType(ImageView.ScaleType scaleType, ScalingUtils.ScaleType defaultScaleType) {
        switch (scaleType) {
            case CENTER:
                return ScalingUtils.ScaleType.CENTER;
            case CENTER_CROP:
                return ScalingUtils.ScaleType.CENTER_CROP;
            case CENTER_INSIDE:
                return ScalingUtils.ScaleType.CENTER_INSIDE;
            case FIT_CENTER:
                return ScalingUtils.ScaleType.FIT_CENTER;
            case FIT_START:
                return ScalingUtils.ScaleType.FIT_START;
            case FIT_END:
                return ScalingUtils.ScaleType.FIT_END;
            case FIT_XY:
                return ScalingUtils.ScaleType.FIT_XY;
            case MATRIX:
                //NOTE this case
                //you should set FocusPoint to make sentence
                return ScalingUtils.ScaleType.FOCUS_CROP;
            default:
                return defaultScaleType;
        }
    }


    //if needed, let's your image view implement this interface
    //also it's not must be required to implement this interface
    public interface TemporaryDetachListener {

        void onSaveTemporaryDetachListener(TemporaryDetachListener listener);

        void onStartTemporaryDetach(View view);

        void onFinishTemporaryDetach(View view);
    }


    //DraweeHolder event dispatch
    private class DraweeHolderDispatcher implements View.OnAttachStateChangeListener, View.OnTouchListener, TemporaryDetachListener {

        @Override
        public void onViewAttachedToWindow(View v) {
            if (mDraweeHolder != null) {
                mDraweeHolder.onAttach();
            }
        }

        @Override
        public void onViewDetachedFromWindow(View v) {
            if (mDraweeHolder != null) {
                mDraweeHolder.onDetach();
            }
        }

        @Override
        public void onSaveTemporaryDetachListener(TemporaryDetachListener listener) {
            //empty
        }

        @Override
        public void onStartTemporaryDetach(View view) {
            if (mDraweeHolder != null) {
                mDraweeHolder.onDetach();
            }
        }

        @Override
        public void onFinishTemporaryDetach(View view) {
            if (mDraweeHolder != null) {
                mDraweeHolder.onAttach();
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mDraweeHolder != null) {
                if (mDraweeHolder.onTouchEvent(event)) {
                    return true;
                }
            }
            return false;
        }
    }

}