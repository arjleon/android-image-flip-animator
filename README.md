#Image Flip Animator
===============

Simple project to apply a simple flipping animation to an ImageView while switching the images as soon as a fill spin is made. Have 1 or more images spinning around an specific axis as simple UI feedback.

##Usage

Still getting used to Android Studio's way of handling library projects/modules

Include in Android Studio project:

app.gradle:

```
compile project(':imageflipanimator')
```

settings.gradle:

```
include ':imageflipanimator'
project(':imageflipanimator').projectDir = new File(settingsDir, '../ImageFlipAnimator/app')
```

```
new ImageFlipAnimator()
		.axis(ImageFlipAnimator.AXIS_Y)
		.duration(3000) //milliseconds
		.imagesResources(R.drawable.flip_1, R.drawable.flip_2, R.drawable.flip_3) //switch between them all sequentially after every spin
		.interpolator(ImageFlipAnimator.INTERPOLATOR_LINEAR)
		.times(ImageFlipAnimator.TIMES_INFINITE)
		.start(mLogoImageView);
```

####Future features:
* Provide a possible callback with an updated 'offset' of the spinning.
* Optimizations
