package me.mahakagg.physicsanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;

public class FlingAnimTextView extends AppCompatTextView {
    public FlingAnimTextView(Context context) {
        super(context);
    }

    public FlingAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlingAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
            FlingAnimation fling = new FlingAnimation(this, DynamicAnimation.ROTATION_X);
            fling.setStartVelocity(150)
                    .setMinValue(0)
                    .setMaxValue(1000)
                    .setFriction(0.1f)
                    .start();
        }
        return super.onTouchEvent(event);
    }
}
