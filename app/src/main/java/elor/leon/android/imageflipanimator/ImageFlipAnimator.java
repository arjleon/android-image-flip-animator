package elor.leon.android.imageflipanimator;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;


public class ImageFlipAnimator {

    public static final int TIMES_INFINITE = ObjectAnimator.INFINITE;
    public static final int AXIS_X = 0;
    public static final int AXIS_Y = 1;
    public static final int INTERPOLATOR_LINEAR = 0;
    public static final int INTERPOLATOR_ACCELERATE = 1;
    public static final int INTERPOLATOR_DECELERATE = 2;
    public static final int INTERPOLATOR_ACCELERATE_DECELERATE = 3;
    public static final int INTERPOLATOR_BOUNCE = 4;

    private static final String ROTATE_AXIS_Y = "rotationY";
    private static final String ROTATE_AXIS_X = "rotationX";

    private View mView;
    private int[] mResources;
    private int mIndex;
    private long mDuration;
    private int mTimes;
    private float mAlphaStart;
    private float mAlphaMid;
    private float mAlphaEnd;
    private String mAxisProperty;
    private Interpolator mInterpolator;

    public ImageFlipAnimator() {
        mIndex = 0;
        mDuration = 1000;
        mAlphaStart = 1.0f;
        mAlphaMid = 1.0f;
        mAlphaEnd = 1.0f;
        mAxisProperty = ROTATE_AXIS_Y;
        mInterpolator = new LinearInterpolator();
        mTimes = TIMES_INFINITE;
    }

    /**
     * By calling this method, the view will be considered an ImageView from now on, and an
     * attempt of converting it to an ImageView will be made. If conversion goes through,
     * the passed array of image resource IDs can be set once each flip. By <strong>not</strong>
     * calling this method, the view will be flipped but no images will be shown.
     */
    public ImageFlipAnimator imagesResources(int... resourceIds) {
        mResources = resourceIds;
        return this;
    }

    public ImageFlipAnimator duration(long duration) {
        mDuration = duration;
        return this;
    }

    public ImageFlipAnimator alpha(float start, float mid, float end) {
        mAlphaStart = start;
        mAlphaMid = mid;
        mAlphaEnd = end;
        return this;
    }

    public ImageFlipAnimator times(int times) {
        //to avoid an extra repeat of the animation, decrease by one if not set to be infinite
        if (times != TIMES_INFINITE) {
            times -= 1;
        }
        mTimes = times;
        return this;
    }

    public ImageFlipAnimator axis(int axis) {
        if (axis == AXIS_X) {
            mAxisProperty = ROTATE_AXIS_X;
        } else if (axis == AXIS_Y) {
            mAxisProperty = ROTATE_AXIS_Y;
        }

        return this;
    }

    public ImageFlipAnimator interpolator(int interpolator) {
        switch (interpolator) {
            case INTERPOLATOR_LINEAR:
                mInterpolator = new LinearInterpolator();
                break;
            case INTERPOLATOR_ACCELERATE:
                mInterpolator = new AccelerateInterpolator();
                break;
            case INTERPOLATOR_DECELERATE:
                mInterpolator = new DecelerateInterpolator();
                break;
            case INTERPOLATOR_ACCELERATE_DECELERATE:
                mInterpolator = new AccelerateDecelerateInterpolator();
                break;
            case INTERPOLATOR_BOUNCE:
                mInterpolator = new BounceInterpolator();
                break;
        }

        return this;
    }

    public void start(View view) {
        mView = view;

        ObjectAnimator flip = ObjectAnimator.ofFloat(mView, mAxisProperty, -90f, 90f);
        flip.setDuration(mDuration);
        flip.setRepeatCount(mTimes);
        flip.setInterpolator(mInterpolator);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mView, "alpha",
                mAlphaStart, mAlphaMid, mAlphaEnd);
        alpha.setDuration(mDuration);
        alpha.setRepeatCount(mTimes);
        alpha.setInterpolator(mInterpolator);

        //if non-null, the view will be treated as ImageView with switching images
        if (mResources != null) {
            flip.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {

                    mIndex++;

                    if (mIndex >= mResources.length) {
                        mIndex = 0;
                    }

                    ImageView image = (ImageView) mView;
                    image.setImageResource(mResources[mIndex]);
                }

                @Override
                public void onAnimationEnd(Animator animation) {}

                @Override
                public void onAnimationCancel(Animator animation) {}
            });
        }

        flip.start();
        alpha.start();
    }
}
