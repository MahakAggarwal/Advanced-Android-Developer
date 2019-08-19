package me.mahakagg.customfancontrollercc2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DialView dialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialView = findViewById(R.id.dialView);
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
        int selectionNumber;
        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.action_selection_3: selectionNumber = 3; break;
            case R.id.action_selection_4: selectionNumber = 4; break;
            case R.id.action_selection_5: selectionNumber = 5; break;
            case R.id.action_selection_6: selectionNumber = 6; break;
            case R.id.action_selection_7: selectionNumber = 7; break;
            case R.id.action_selection_8: selectionNumber = 8; break;
            case R.id.action_selection_9: selectionNumber = 9; break;
            default: selectionNumber = 4; break;
        }
        dialView.setSelectionCount(selectionNumber);
        return super.onOptionsItemSelected(item);
    }
}
