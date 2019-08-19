package me.mahakagg.memoryoverload;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int NO_OF_TEXTVIEWS_ADDED = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Adds a new row of text views when the floating action button is pressed.
     */
    public void addRowOfTextViews(View view) {

        LinearLayout root = findViewById(R.id.rootLinearLayout);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(linearLayoutParams);
        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView[] textViews = new TextView[NO_OF_TEXTVIEWS_ADDED];

        for (int i = 0; i < NO_OF_TEXTVIEWS_ADDED; i++) {
            textViews[i] = new TextView(this);
            textViews[i].setLayoutParams(textViewParams);
            textViews[i].setText(String.valueOf(i));
            textViews[i].setBackgroundColor(getRandomColor());
            linearLayout.addView(textViews[i]);
        }
        root.addView(linearLayout);
    }

    /**
     * Creates a random color for background color of the text view.
     */
    private int getRandomColor() {
        Random r = new Random();
        int red = r.nextInt(255);
        int green = r.nextInt(255);
        int blue = r.nextInt(255);
        return Color.rgb(red, green, blue);
    }
}
