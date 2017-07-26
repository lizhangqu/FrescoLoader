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
FrescoLoader.with(context)
            .placeholder(placeholder)
            .resize(resize, resize)
            .roundAsCircle(Color.RED, 10)
            .progressiveRenderingEnabled(true)
            .load(uri)
            .fadeDuration(3000)
            .scaleType(ScalingUtils.ScaleType.CENTER_CROP)
            .into(imageView);
```

see more method in class **FrescoLoader**

## License

FrescoLoader is under the BSD license. See the [LICENSE](https://github.com/lizhangqu/FrescoLoader/blob/master/LICENSE) file for details.