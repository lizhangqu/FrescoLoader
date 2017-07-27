## FrescoLoader

FrescoLoader is a framework which uses fresco to load an image into the **android.widget.ImageView**.

## Changelog

See changelog details in [CHANGELOG](https://github.com/lizhangqu/FrescoLoader/blob/master/CHANGELOG.md).

## Examples

I have provided a sample.

See sample [here on Github](https://github.com/lizhangqu/FrescoLoader/tree/master/app).

To run the sample application, simply clone this repository and use android studio to compile it, then install it on a connected device.

## Note

>If you use this FrescoLoader, please make sure you have not use the ImageView's tag.

## Usage

### Dependency

**latest version**

[ ![Download](https://api.bintray.com/packages/lizhangqu/maven/fresco-loader/images/download.svg) ](https://bintray.com/lizhangqu/maven/fresco-loader/_latestVersion)

>current not release a version

**gradle**

```
dependencies {
    compile "io.github.lizhangqu:fresco-loader:${latest_version}"
}
```

**maven**

```
<dependencies>
    <dependency>
      <groupId>io.github.lizhangqu</groupId>
      <artifactId>fresco-loader</artifactId>
      <version>${latest_version}</version>
    </dependency>
</dependencies>
```

### Fresco 

Add fresco dependencies as you need.

```
// fresco base
compile("com.facebook.fresco:fresco:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco gif support(if you need gif add this)
compile("com.facebook.fresco:animated-gif:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco dynamic webp support(if you need dynamic webp add this)
compile("com.facebook.fresco:animated-webp:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco static webp support(if you need static webp add this)
compile("com.facebook.fresco:webpsupport:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
```

### Loader Sample

```
FrescoLoader.with(view.getContext())
            .progressiveRenderingEnabled(true)
            .fadeDuration(2000)
            .autoPlayAnimations(true)
            .autoRotateEnabled(true)
            .retainImageOnFailure(true)
            .desiredAspectRatioWithHeight(0.5F)
            .tapToRetryEnabled(true)
            .focusPoint(new PointF(30, 50))
            .resize(400, 400)
            .fadeDuration(1000)
            .border(Color.RED, 10)
            .borderColor(Color.RED)
            .borderWidth(10)
            .cornersRadii(10, 10, 10, 10)
            .cornersRadius(10)
            .roundAsCircle()
            .backgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.bg_zero))
            .progressBar(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_progress_bar))
            .progressBarScaleType(ImageView.ScaleType.CENTER_CROP)
            .placeholder(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_placeholder))
            .placeholderScaleType(ImageView.ScaleType.CENTER_CROP)
            .failure(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_failure))
            .failureScaleType(ImageView.ScaleType.CENTER_CROP)
            .retry(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_retry))
            .retryScaleType(ImageView.ScaleType.CENTER_CROP)
            .colorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN))
            .overlays(overlays)
            .pressedStateOverlay(ContextCompat.getDrawable(getApplicationContext(), R.mipmap.bg_one))
            .actualScaleType(ImageView.ScaleType.CENTER_CROP)
            .lowerLoad(R.mipmap.ic_launcher_round)
            .load("http://desk.fd.zol-img.com.cn/t_s960x600c5/g5/M00/0D/01/ChMkJlgq0z-IC78PAA1UbwykJUgAAXxIwMAwQcADVSH340.jpg")
            .localThumbnailPreviewsEnabled(true)
            .into(image);
```

See more details in class [FrescoLoader.java](https://github.com/lizhangqu/FrescoLoader/blob/master/library/src/main/java/io/github/lizhangqu/fresco/FrescoLoader.java)

If you have extended the android.widget.ImageView class and you can add some method to it, you'd better add this. But it's not necessary if you don't want.

```
public class MyImageView extends ImageView implements FrescoLoader.TemporaryDetachListener {
    //holder the TemporaryDetachListener
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
        //call delegete
        if (this.listener != null) {
            listener.onStartTemporaryDetach(this);
        }

    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        //call delegete
        if (this.listener != null) {
            listener.onFinishTemporaryDetach(this);
        }
    }

    @Override
    public void onSaveTemporaryDetachListener(FrescoLoader.TemporaryDetachListener listener) {
        //holder it
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

```

## License

FrescoLoader is under the BSD license. See the [LICENSE](https://github.com/lizhangqu/FrescoLoader/blob/master/LICENSE) file for details.