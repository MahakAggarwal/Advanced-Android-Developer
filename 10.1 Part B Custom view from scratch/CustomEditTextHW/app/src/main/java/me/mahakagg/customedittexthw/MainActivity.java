package me.mahakagg.customedittexthw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
* Answers to homework questions
*
* Q1 - Which constructor do you need to inflate the layout for a custom view? Choose one:
* A1 - public MyCustomView(Context context)
*
* Q2 - To define how your custom view fits into an overall layout, which method do you override?
* A2 - onDraw()
*
* Q3 - To calculate the positions, dimensions, and any other values when the custom view is first assigned a size, which method do you override?
* A3 - onSizeChanged()
*
* Q4 - To indicate that you'd like your view to be redrawn with onDraw(), which method do you call from the UI thread, after an attribute value has changed?
* A4 - invalidate()
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
