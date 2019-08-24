package me.mahakagg.propertyanimationhw;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/*
* Answers to homework questions
*
* Q1 - What types of animations are available with Android?
* A1 - View, property, drawable, physics-based
*
* Q2 - Which of the following statements about property animation are true?
* A2 - Property animation lets you define an animation to change any object property over time.
* Property animation lets you create objects with custom properties that you can animate.
* A property animation tracks time and adapts its velocity to the time.
* Property animation lets you animate multiple properties with animator sets.
*
* Q3 - What are the advantages of using physics-based animation libraries? Select up to three answers.
* A3 - Physics-based animations are more realistic than other types of animations, because physics-based animations appear more natural.
* It is easier to use the physics-based support library than to implement adaptive animations yourself.
* Physics-based animations keep momentum when their target changes and end with a smoother motion than other types of animations.
*
* */

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
