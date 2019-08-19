package me.mahakagg.itemsforsalev3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

/*
* Answers to homework questions
* Q1 - What tools are available in Android Studio for measuring memory performance?
* A1 - Systrace
* Heap dumps
* Memory Profiler
*
* Q2 - Looking at this Memory Monitor graph, which of the following statements is true?
* A2 - The app is allocating an increasing amount of memory, and you should investigate for possible memory leaks.
* The app will run out of memory and crash.
* More garbage collection events will happen, resulting in a slower app.
*
* Q3 - Select all answers that describe features and benefits of a heap dump:
* A3 - A heap dump is a snapshot of the allocated memory at a specific time.
* You can look at a static snapshot of allocated memory.
* You can dump the Java heap to see which objects are using up memory at any given time.
* Doing several heap dumps over an extended period can help you identify memory leaks.
*
* Q4 - What are the benefits of recording memory allocations? Select up to four.
* A4 -You can track allocations and de-allocations over time.
* You can inspect the call stack and find out where in your code an allocation was made.
* You can track down which objects might be responsible for memory leaks.
* You can record memory allocations during normal and extreme user interactions. This recording lets you identify where your code is allocating too many objects in a short time, or allocating objects that leak memory.
*
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
