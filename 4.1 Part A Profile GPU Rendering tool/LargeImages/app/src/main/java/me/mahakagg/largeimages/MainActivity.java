package me.mahakagg.largeimages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

// This app is made for testing performance. It is not ideal and doesn't follow android's best practices
public class MainActivity extends AppCompatActivity {
    private int toggle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeImage(View view) {
        switch (toggle){
            case 0: view.setBackgroundResource(R.drawable.ankylo); toggle = 1; break;
            case 1:
                try {
                    Thread.sleep(32); // two refreshes
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                view.setBackgroundResource(R.drawable.dinosaur_large); toggle = 0; break;
//            case 2: view.setBackgroundResource(R.drawable.ankylo); toggle = 0; break;
            default: break;
        }
    }
}
