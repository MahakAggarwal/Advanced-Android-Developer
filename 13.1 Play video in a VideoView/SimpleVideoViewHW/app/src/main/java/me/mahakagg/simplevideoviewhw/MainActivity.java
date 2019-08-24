package me.mahakagg.simplevideoviewhw;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
* Answers to homework questions
*
* Q1 - VideoView is a wrapper for which two classes?
* A1 - MediaPlayer and SurfaceView
*
* Q2 - Which of the following sources can VideoView play?
* A2 - The URL of a video sample located on a web server
* A sample contained on external device media
* A sample embedded in the app's resources
*
* Q3 - Which of these callbacks are available for media events in the VideoView class?
* A3 - onError, onInfo, onPrepared
*
* */

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<VideoInfo> videoInfoArrayList;
    private VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setup recyclerView and initialize member variables
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        videoInfoArrayList = new ArrayList<>();
        adapter = new VideoAdapter(this, videoInfoArrayList);
        recyclerView.setAdapter(adapter);
        initializeData();
    }

    // get data from string resources, put it in arrayList, and notify adapter of data change
    public void initializeData() {
        videoInfoArrayList.add(new VideoInfo("Burning Charcoal", "android.resource://" + getPackageName() + "/raw/burning_charcoal"));
        videoInfoArrayList.add(new VideoInfo("coffee", "android.resource://" + getPackageName() + "/raw/coffee"));
        videoInfoArrayList.add(new VideoInfo("spaghetti", "android.resource://" + getPackageName() + "/raw/spaghetti"));
        videoInfoArrayList.add(new VideoInfo("waves", "android.resource://" + getPackageName() + "/raw/waves"));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.setAdapter(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
    }
}

// royalty free videos from : https://www.pexels.com/videos/