package me.mahakagg.fragmentcommunicationhw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


/*
* Answers to homework questions
*
* Q1 - Which fragment-lifecycle callback draws the fragment's UI for the first time?
* A1 - onCreateView()
*
* Q2 - How do you send data from an activity to a fragment?
* A2 - Set a Bundle and use the Fragment.setArguments(Bundle) method to supply the construction arguments for the fragment.
* */

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener {
    private Button mButton;
    private boolean isFragmentDisplayed = false;
    static final String STATE_FRAGMENT = "state_of_fragment";
    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.open_button);
        // onclick listener for the button
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });
        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }
    }

    // instantiating fragment and displaying it
    public void displayFragment() {
        // get new instance
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice);
        // get the fragment manager and start the transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // add fragment and commit transaction
        // addToBackStack(null) adds the fragment to the back stack of fragment transactions
        // this stack is maintained by the activity and when user presses back button, the previous fragment is returned
        fragmentTransaction.add(R.id.fragment_container, simpleFragment).addToBackStack(null).commit();
        // update button and boolean flag
        mButton.setText(R.string.close);
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        // get fragment manager
        FragmentManager fragmentManager = getSupportFragmentManager();
        // check to see if the fragment is currently running or not
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // fragment is displayed so remove it and commit
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // update button and boolean flag
        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }

    // interface method (get choice and display toast)
    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        String choiceValue;
        switch (choice){
            case 0: choiceValue = "yes"; break;
            case 1: choiceValue = "no"; break;
            case 2: choiceValue = "none"; break;
            default: choiceValue = "none"; break;
        }
        Toast.makeText(this, "Choice is " + choiceValue, Toast.LENGTH_SHORT).show();
    }
}
