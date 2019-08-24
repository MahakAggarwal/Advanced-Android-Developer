package me.mahakagg.propertyanimationhw;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;

import androidx.appcompat.widget.AppCompatTextView;

public class AnimatedTextView extends AppCompatTextView {
    private static final int ANIMATION_DURATION = 5000;
    private static final int ANIMATION_DELAY = 1000;
    private AnimatorSet animatorSet = new AnimatorSet();

    public AnimatedTextView(Context context) {
        super(context);
    }

    public AnimatedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
            // if animation is running, cancel it
            if (animatorSet != null && animatorSet.isRunning()){
                animatorSet.cancel();
            }
            if (animatorSet != null){
                // play animations in sequence - rotate reduce size, wait, and then rotate increase size
                animatorSet.start();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // reducing text size animation
        ObjectAnimator sizeDecreaseAnimation = ObjectAnimator.ofFloat(this, "textSize", 40, 0);
        sizeDecreaseAnimation.setDuration(ANIMATION_DURATION);
        sizeDecreaseAnimation.setInterpolator(new LinearInterpolator());

        // rotating animation 1
        ObjectAnimator rotateAnimation1 = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f);
        rotateAnimation1.setDuration(ANIMATION_DURATION);
        rotateAnimation1.setInterpolator(new LinearInterpolator());

        // increasing text size animation
        ObjectAnimator sizeIncreaseAnimation = ObjectAnimator.ofFloat(this, "textSize", 0, 40);
        sizeIncreaseAnimation.setDuration(ANIMATION_DURATION);
        sizeIncreaseAnimation.setStartDelay(ANIMATION_DELAY);
        sizeIncreaseAnimation.setInterpolator(new LinearInterpolator());

        // rotating animation 2
        ObjectAnimator rotateAnimation2 = ObjectAnimator.ofFloat(this, "rotation", 360f, 0f);
        rotateAnimation2.setDuration(ANIMATION_DURATION);
        sizeDecreaseAnimation.setStartDelay(ANIMATION_DELAY);
        rotateAnimation2.setInterpolator(new LinearInterpolator());

        animatorSet.playTogether(sizeDecreaseAnimation, rotateAnimation1);
        animatorSet.playTogether(sizeIncreaseAnimation, rotateAnimation2);
    }
}
