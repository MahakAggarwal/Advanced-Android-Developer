package me.mahakagg.simpleaccessibilityhw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*
* Answers to homework questions
*
* Q1 - Which of the following attributes should you add to ImageView and ImageButton elements to enable screen readers to describe the image?
* A1 - android:contentDescription
*
* Q2 - When should you add a content description to an ImageView or ImageButton?
* A2 - When the image is meaningful to the user in their use of the app.
*
* Q3 - When do you NOT need to add a content description to a view element?
* A3 - All of the above
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
