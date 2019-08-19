package me.mahakagg.recyclerviewlocale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // LinkedList to hold the data
    private final LinkedList<String> mWordList = new LinkedList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int wordListSize = mWordList.size();
                // add new word to the linked list
//                mWordList.addLast("+ Word " + wordListSize);
                mWordList.addLast(String.format(getResources().getString(R.string.word), wordListSize));
                // notify adapter of data change
                Objects.requireNonNull(mRecyclerView.getAdapter()).notifyItemInserted(wordListSize);
                // scroll to the bottom of the list
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });

        // populate the initial data
        for (int i = 1; i < 21; i++) {
//            mWordList.add("Word " + i);
            mWordList.addLast(String.format(getResources().getString(R.string.word), i));
        }

        // set up recyclerView - find view, create and set adapter; set layout manager
        mRecyclerView = findViewById(R.id.recyclerView);
        WordListAdapter mAdapter = new WordListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
