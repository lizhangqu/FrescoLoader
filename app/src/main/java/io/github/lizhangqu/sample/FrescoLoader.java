package io.github.lizhangqu.sample;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.ColorFilter;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
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

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * fresco图片加载器, https://www.fresco-cn.org/docs/
 * 会覆盖ImageView的OnTouchListener
 *
 * @author lizhangqu
 * @version V1.0
 * @since 2017-07-26 13:16
 */
public class FrescoLoader implements View.OnAttachStateChangeListener, View.OnTouchListener {
    private Context mContext;
    private DraweeHolder<DraweeHierarchy> mDraweeHolder;

    private Uri mUri;
    private Uri mLowerUri;
    private ResizeOptions mResizeOptions;
    private boolean mAutoRotateEnabled = false;

    private int mFadeDuration;
    //宽高比
    private float mDesiredAspectRatio;
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

    private FrescoLoader(Context context) {
        this.mContext = context.getApplicationContext();

        mFadeDuration = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
        mDesiredAspectRatio = 0;

        mPlaceholderDrawable = null;
        mPlaceholderScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        mRetryDrawable = null;
        mRetryScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        mFailureDrawable = null;
        mFailureScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        mProgressBarDrawable = null;
        mProgressScaleType = GenericDraweeHierarchyBuilder.DEFAULT_SCALE_TYPE;

        mActualImageScaleType = GenericDraweeHierarchyBuilder.DEFAULT_ACTUAL_IMAGE_SCALE_TYPE;
        mActualImageFocusPoint = null;
        mActualImageColorFilter = null;

        mBackgroundDrawable = null;
        mOverlays = null;
        mPressedStateOverlay = null;
        mRoundingParams = null;

        mTapToRetryEnabled = false;
        mAutoPlayAnimations = false;
        mRetainImageOnFailure = false;

        this.mDraweeHolder = DraweeHolder.create(null, mContext);
    }

    public static FrescoLoader with(Context context) {
        return new FrescoLoader(context);
    }

    public FrescoLoader fadeDuration(int fadeDuration) {
        this.mFadeDuration = fadeDuration;
        return this;
    }

    public FrescoLoader desiredAspectRatio(float desiredAspectRatio) {
        this.mDesiredAspectRatio = desiredAspectRatio;
        return this;
    }

    public FrescoLoader tapToRetryEnabled(boolean tapToRetryEnabled) {
        this.mTapToRetryEnabled = tapToRetryEnabled;
        return this;
    }

    public FrescoLoader placeholder(Drawable placeholderDrawable) {
        this.mPlaceholderDrawable = placeholderDrawable;
        return this;
    }

    public FrescoLoader placeholder(int placeholderResId) {
        this.mPlaceholderDrawable = this.mContext.getResources().getDrawable(placeholderResId);
        return this;
    }

    public FrescoLoader placeholderScaleType(ScalingUtils.ScaleType scaleType) {
        this.mPlaceholderScaleType = scaleType;
        return this;
    }

    public FrescoLoader retry(Drawable retryDrawable) {
        this.mRetryDrawable = retryDrawable;
        return this;
    }

    public FrescoLoader retry(int retryResId) {
        this.mRetryDrawable = this.mContext.getResources().getDrawable(retryResId);
        return this;
    }

    public FrescoLoader retryScaleType(ScalingUtils.ScaleType scaleType) {
        this.mRetryScaleType = scaleType;
        return this;
    }

    public FrescoLoader failure(Drawable failureDrawable) {
        this.mFailureDrawable = failureDrawable;
        return this;
    }

    public FrescoLoader failure(int failureResId) {
        this.mFailureDrawable = this.mContext.getResources().getDrawable(failureResId);
        return this;
    }

    public FrescoLoader failureScaleType(ScalingUtils.ScaleType scaleType) {
        this.mFailureScaleType = scaleType;
        return this;
    }

    public FrescoLoader progressBar(Drawable placeholderDrawable) {
        this.mProgressBarDrawable = placeholderDrawable;
        return this;
    }

    public FrescoLoader progressBar(int progressResId) {
        this.mProgressBarDrawable = this.mContext.getResources().getDrawable(progressResId);
        return this;
    }

    public FrescoLoader progressScaleType(ScalingUtils.ScaleType scaleType) {
        this.mPlaceholderScaleType = scaleType;
        return this;
    }

    public FrescoLoader scaleType(ScalingUtils.ScaleType scaleType) {
        this.mActualImageScaleType = scaleType;
        return this;
    }

    public FrescoLoader focusPoint(PointF focusPoint) {
        this.mActualImageFocusPoint = focusPoint;
        return this;
    }

    public FrescoLoader colorFilter(ColorFilter colorFilter) {
        this.mActualImageColorFilter = colorFilter;
        return this;
    }

    public FrescoLoader background(Drawable backgroundDrawable) {
        this.mBackgroundDrawable = backgroundDrawable;
        return this;
    }

    public FrescoLoader background(int backgroundResId) {
        this.mBackgroundDrawable = this.mContext.getResources().getDrawable(backgroundResId);
        return this;
    }

    public FrescoLoader overlays(List<Drawable> overlays) {
        this.mOverlays = overlays;
        return this;
    }

    public FrescoLoader overlay(Drawable overlay) {
        if (overlay == null) {
            this.mOverlays = null;
        } else {
            this.mOverlays = Arrays.asList(overlay);
        }
        return this;
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

    public FrescoLoader cornersRadius(int radius) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setCornersRadius(radius);
        return this;
    }

    public FrescoLoader roundAsCircle(int borderColor, int borderWidth) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setBorder(borderColor, borderWidth);
        this.mRoundingParams.setRoundAsCircle(true);
        return this;
    }

    public FrescoLoader cornersRadii(
            float topLeft,
            float topRight,
            float bottomRight,
            float bottomLeft) {
        if (this.mRoundingParams == null) {
            this.mRoundingParams = new RoundingParams();
        }
        this.mRoundingParams.setCornersRadii(topLeft, topRight, bottomRight, bottomLeft);
        return this;
    }

    public FrescoLoader load(Uri uri) {
        this.mUri = uri;
        return this;
    }

    public FrescoLoader load(String uri) {
        this.mUri = Uri.parse(uri);
        return this;
    }

    public FrescoLoader load(File file) {
        this.mUri = Uri.fromFile(file);
        return this;
    }

    public FrescoLoader load(int resourceId) {
        this.mUri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resourceId))
                .build();
        return this;
    }

    public FrescoLoader lowerLoad(Uri uri) {
        this.mLowerUri = uri;
        return this;
    }

    public FrescoLoader lowerLoad(String uri) {
        this.mLowerUri = Uri.parse(uri);
        return this;
    }

    public FrescoLoader lowerLoad(File file) {
        this.mLowerUri = Uri.fromFile(file);
        return this;
    }

    public FrescoLoader lowerLoad(int resourceId) {
        this.mLowerUri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resourceId))
                .build();
        return this;
    }

    public FrescoLoader resize(ResizeOptions resizeOptions) {
        this.mResizeOptions = resizeOptions;
        return this;
    }

    public FrescoLoader resize(int targetWidth, int targetHeight) {
        this.mResizeOptions = new ResizeOptions(targetWidth, targetHeight);
        return this;
    }

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

    public void into(ImageView targetView) {
        if (mUri != null) {
            //hierarchy
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
                    .setDesiredAspectRatio(mDesiredAspectRatio)
                    .setRoundingParams(mRoundingParams)
                    .build();

            mDraweeHolder.setHierarchy(hierarchy);

            //image request
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(mUri)
                    .setResizeOptions(mResizeOptions)
                    .setAutoRotateEnabled(mAutoRotateEnabled)
                    .build();


            //controller
            PipelineDraweeControllerBuilder controllerBuilder = Fresco.newDraweeControllerBuilder()
                    .setOldController(mDraweeHolder.getController())
                    .setImageRequest(request)
                    .setTapToRetryEnabled(mTapToRetryEnabled)
                    .setRetainImageOnFailure(mRetainImageOnFailure)
                    .setAutoPlayAnimations(mAutoPlayAnimations);

            if (mLowerUri != null) {
                controllerBuilder.setLowResImageRequest(ImageRequest.fromUri(mLowerUri));
            }

            DraweeController draweeController = controllerBuilder.build();
            mDraweeHolder.setController(draweeController);

            //listener
            targetView.removeOnAttachStateChangeListener(this);
            targetView.addOnAttachStateChangeListener(this);
            targetView.setOnTouchListener(this);

            //tint
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ColorStateList imageTintList = targetView.getImageTintList();
                if (imageTintList == null) {
                    return;
                }
                targetView.setColorFilter(imageTintList.getDefaultColor());
            }

            targetView.setImageDrawable(mDraweeHolder.getTopLevelDrawable());
        }
    }

    @Override
    public void onViewAttachedToWindow(View v) {
        mDraweeHolder.onAttach();
    }

    @Override
    public void onViewDetachedFromWindow(View v) {
        mDraweeHolder.onDetach();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mDraweeHolder.onTouchEvent(event)) {
            return true;
        }
        return false;
    }
}