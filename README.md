## FrescoLoader

FrescoLoader is a framework which use fresco to load image into **android.widget.ImageView**.

## Changelog

See details in [CHANGELOG](https://github.com/lizhangqu/FrescoLoader/blob/master/CHANGELOG.md).

## Examples

I have provided a sample.

See sample [here on Github](https://github.com/lizhangqu/FrescoLoader/tree/master/app).

To run the sample application, simply clone this repository and use android studio to compile, install it on a connected device.

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

add fresco if necessary

```
// fresco base
compile("com.facebook.fresco:fresco:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco gif support
compile("com.facebook.fresco:animated-gif:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco dynamic webp support
compile("com.facebook.fresco:animated-webp:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
// fresco static webp support
compile("com.facebook.fresco:webpsupport:${FRESCO_VERSION}") {
    exclude group: 'com.android.support'
}
```

### Loader Sample

```
FrescoLoader.with(view.getContext())
            .progressiveRenderingEnabled(true)
            .autoPlayAnimations(true)
            .autoRotateEnabled(true)
            .retainImageOnFailure(true)
            .tapToRetryEnabled(true)
            .desiredAspectRatio(1.5F)
            .focusPoint(new PointF(30, 50))
            .resize(400, 400)
            .fadeDuration(1000)
            .border(Color.RED, 10)
            .cornersRadii(10, 10, 10, 10)
            .cornersRadius(10)
            .roundAsCircle()
            .progressBar(new CircleProgressBarDrawable())
            .progressScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .placeholder(R.mipmap.ic_launcher_round)
            .placeholderScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .failure(R.mipmap.ic_launcher_round)
            .failureScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .retry(R.mipmap.ic_launcher_round)
            .retryScaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .colorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN))
            .pressedStateOverlay(R.mipmap.ic_launcher_round)
            .overlay(R.mipmap.ic_launcher_round)
            .background(new ColorDrawable(Color.parseColor("#ee000000")))
            .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .lowerLoad(R.mipmap.ic_launcher_round)
            .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .load("http://img1.imgtn.bdimg.com/it/u=615670559,766970618&fm=26&gp=0.jpg")
            .localThumbnailPreviewsEnabled(true)
            .into(image);
```

see more method in class **FrescoLoader**

## License

FrescoLoader is under the BSD license. See the [LICENSE](https://github.com/lizhangqu/FrescoLoader/blob/master/LICENSE) file for details.