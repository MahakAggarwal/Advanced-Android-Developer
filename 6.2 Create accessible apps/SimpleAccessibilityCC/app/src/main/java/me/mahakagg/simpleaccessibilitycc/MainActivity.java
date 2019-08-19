package me.mahakagg.simpleaccessibilitycc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    // boolean to check if discard button has been clicked once or not
    private boolean isDiscardClicked;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageButton = findViewById(R.id.button_image);
        // initialize the boolean flag
        isDiscardClicked = false;
    }

    public void onImageButtonClick(View view) {
        if (! isDiscardClicked){
            // if discard is not clicked, means change image to lock
            // get lock drawable and change tint to black so its properly visible
            Drawable drawable = getResources().getDrawable(android.R.drawable.ic_lock_lock);
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, getResources().getColor(android.R.color.black));
            imageButton.setImageDrawable(drawable);

            // change content description and boolean flag
            imageButton.setContentDescription(getString(R.string.lock));
            isDiscardClicked = true;
        }
        else{
            // lock image is active so change it
            imageButton.setImageResource(R.drawable.ic_action_discard);
            imageButton.setContentDescription(getString(R.string.discard));
            isDiscardClicked = false;
        }
    }
}
