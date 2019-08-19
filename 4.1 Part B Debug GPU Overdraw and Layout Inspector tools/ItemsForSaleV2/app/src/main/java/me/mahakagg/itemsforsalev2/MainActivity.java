package me.mahakagg.itemsforsalev2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;

import java.util.ArrayList;

/*
* Answers to homework questions
* Q1 - How much time does your app have available to calculate and display one frame of content?
* A1 - Less than 16 milliseconds, because the Android framework is doing work at the same time as your app does work.
*
* Q2 - What are some techniques you can use to make rendering faster?
* A2 - Reduce overdraw.
* Move work away from the UI thread.
* Use AsyncTask.
* Use smaller images.
* Compress your data.
* Flatten your view hierarchy.
* Use as few views as possible.
* Use loaders to load data in the background.
* Use efficient views such as RecyclerView and ConstraintLayout.
*
* Q3 - Which answer best describes the measure-and-layout stage of the rendering pipeline?
* A3 - The system traverses the view hierarchy and calculates the size and position of each view inside the view's parent, relative to other views.
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
