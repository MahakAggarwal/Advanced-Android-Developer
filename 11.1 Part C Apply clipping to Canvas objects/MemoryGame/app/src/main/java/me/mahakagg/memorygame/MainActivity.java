package me.mahakagg.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
* Answers to homework questions
*
* Q1 - To display something to the screen, which one of the following draw and animation classes is always required?
* A1 - View
*
* Q2 - What are some properties of drawables?
* A2 - Drawables are drawn into a view and the system handles drawing.
* Drawables are best for simple graphics that do not change dynamically.
* You can use drawables for frame-by-frame animations.
*
* Q3 - Which of the following statements are true?
* A3 - You use a Canvas object when elements in your app are redrawn regularly.
* To draw on a Canvas, you must override the onDraw() method of a custom view.
* A Paint object holds style and color information about how to draw geometries, text, and bitmaps.
*
* Q4 - What is clipping?
* A4 - A technique for defining regions on a Canvas that will not be drawn to the screen.
* A way of telling the system which portions of a Canvas do not need to be redrawn.
* A technique to consider when you're trying to speed up drawing.
* A way to create interesting graphical effects.
*
* */
public class MainActivity extends AppCompatActivity {
    private MemoryGameView memoryGameView1, memoryGameView2, memoryGameView3, memoryGameView4;
    private boolean isFirstClick = false;
    private int colorNumber1, colorNumber2;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        memoryGameView1 = findViewById(R.id.memoryGameView1);
        memoryGameView2 = findViewById(R.id.memoryGameView2);
        memoryGameView3 = findViewById(R.id.memoryGameView3);
        memoryGameView4 = findViewById(R.id.memoryGameView4);
        resetButton = findViewById(R.id.button);
        resetButton.setEnabled(false);
    }

    public void playGame(View view) {
        if (!isFirstClick){
            // user clicked for the first time
            isFirstClick = true;
            colorNumber1 = getColorNumber((MemoryGameView) view);
        }
        else {
            // user clicked the second time
            isFirstClick = false;
            colorNumber2 = getColorNumber((MemoryGameView) view);
            if (colorNumber1 == colorNumber2){
                Toast.makeText(this, "You Win!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "You lose. Try again!", Toast.LENGTH_LONG).show();
            }
            resetButton.setEnabled(true);
        }
    }

    private int getColorNumber(MemoryGameView memoryGameView){
        return memoryGameView.getTileColor();
    }
    // reset contents of all the custom views
    public void reset(View view){
        memoryGameView1.reset();
        memoryGameView2.reset();
        memoryGameView3.reset();
        memoryGameView4.reset();
        resetButton.setEnabled(false);
    }
}