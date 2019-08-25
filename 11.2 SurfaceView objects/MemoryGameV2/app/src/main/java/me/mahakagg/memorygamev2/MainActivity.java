package me.mahakagg.memorygamev2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/*
* Answers to homework questions
*
* Q1 - What is a SurfaceView?
* A1 - A view in your app's view hierarchy that has its own separate surface.
*
* Q2 - What is the most distinguishing benefit of using a SurfaceView?
* A2 - You can move drawing operations away from the UI thread.
 *
* Q3 - When should you consider using a SurfaceView? Select up to three.
* A3 - When your app does a lot of drawing, or does complex drawing.
* When your app combines complex graphics with user interaction.
* When your app stutters, and moving drawing off the UI thread could improve performance.
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