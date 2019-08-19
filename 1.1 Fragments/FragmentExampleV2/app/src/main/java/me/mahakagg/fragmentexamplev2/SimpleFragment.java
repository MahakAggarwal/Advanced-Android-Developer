package me.mahakagg.fragmentexamplev2;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {
    private static final int YES = 0;
    private static final int NO = 1;

    // used for when android needs to re-instantiate a fragment when needed
    public SimpleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // setup of root view (layout) and radio group
        final View rootView = inflater.inflate(R.layout.fragment_simple, container, false);
        final RadioGroup group = rootView.findViewById(R.id.radio_group);

        // check change listener for radio group
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = group.findViewById(checkedId);
                int index = group.indexOfChild(radioButton);
                TextView textView = rootView.findViewById(R.id.fragment_header);
                switch (index) {
                    case YES:
                        textView.setText(R.string.yes_message);
                        break;
                    case NO:
                        textView.setText(R.string.no_message);
                        break;
                    default: break;
                }
            }
        });

        return rootView;
    }

    public static SimpleFragment newInstance(){
        return new SimpleFragment();
    }

}
