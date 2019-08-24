package me.mahakagg.physicsanimation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

public class SpringAnimTextView extends AppCompatTextView {
    public SpringAnimTextView(Context context) {
        super(context);
    }

    public SpringAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SpringAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN){
            final SpringAnimation anim = new SpringAnimation(this, DynamicAnimation.Y, 10);
            anim.setStartVelocity(10000);
            anim.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
            anim.start();
        }
        return super.onTouchEvent(event);
    }
}
