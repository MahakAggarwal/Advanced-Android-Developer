package me.mahakagg.itemsforsale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

/*
* Answers to homework questions
* Q1 - Select all of the following that are good basic performance tests you can perform.
* A1 - Install your app on the lowest-end device that your target audience might have.
* Watch your friends while they use the app and make note of their comments.
* Run a small usability test.
*
* Q2 - Select all that are good ways to approach performance problems.
* A2 - Guess at what might be the problem, make a change, and see whether it helps.
* Use a systematic, iterative approach, so that you can measure improvements resulting from your changes to the app.
* Use tools to inspect your app and acquire performance-data measurements.
*
* Q3 - Select all of the following that are performance tools available on your mobile device.
* A3 - Profile GPU Rendering
* USB debugging
* Show GPU view updates
* Debug GPU Overdraw
* */
public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemsAdapter itemsAdapter;
    private ArrayList<Items> itemsArrayList;
    // hold drawables arrays defined in strings.xml
    private TypedArray itemsThumbnailsTypedArray;
    private TypedArray itemsImagesTypedArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // recyclerView setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // initialize array list
        itemsArrayList = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this, itemsArrayList);
        recyclerView.setAdapter(itemsAdapter);
        // populate recyclerView
        initializeData();
    }

    // method to populate recyclerView
    public void initializeData(){
        // arrays and typed arrays for names, prices, thumbnails and large images
        String[] itemNames = getResources().getStringArray(R.array.items_names);
        int[] itemsPrices = getResources().getIntArray(R.array.items_prices);
        itemsThumbnailsTypedArray = getResources().obtainTypedArray(R.array.items_thumbnails);
        itemsImagesTypedArray = getResources().obtainTypedArray(R.array.items_images);
        itemsArrayList.clear();

        // put items into array list
        for (int i = 0; i < itemNames.length; i++){
            itemsArrayList.add(new Items(itemNames[i], itemsPrices[i], itemsThumbnailsTypedArray.getResourceId(i, 0), itemsImagesTypedArray.getResourceId(i, 0)));
        }
        // recycle typed arrays and notify adapter for changes
        itemsThumbnailsTypedArray.recycle();
        itemsImagesTypedArray.recycle();
        itemsAdapter.notifyDataSetChanged();
    }
}
