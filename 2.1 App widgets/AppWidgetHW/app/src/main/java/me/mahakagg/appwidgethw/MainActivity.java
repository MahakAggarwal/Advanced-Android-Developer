package me.mahakagg.appwidgethw;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/*
* Answers to homework questions
*
* Q1 - Which of these app-widget components are required?
* A1 - Provider-info file, Widget-provider class, and Layout file
*
* Q2 - Which of these layout and view classes can be used in an app widget?
* A2 - Button, LinearLayout, and ImageButton
*
* Q3 - n which method in your widget-provider class do you initialize the layout (remote views) for the app widget?
* A3 - onUpdate()
* */
public class MainActivity extends AppCompatActivity {
    private EditText editText;
    SharedPreferences preferences;
    private static final String mSharedPrefFile = "me.mahakagg.appwidgethw";
    private static final String TEXT_INPUT_KEY = "text_input";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        preferences = getSharedPreferences(mSharedPrefFile, MODE_PRIVATE);
        String textString = preferences.getString(TEXT_INPUT_KEY, "");
        editText.setText(textString);
    }

    public void saveText(View view) {
        String text = editText.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TEXT_INPUT_KEY, text).apply();
        Toast.makeText(this, "Text saved!", Toast.LENGTH_LONG).show();
    }
}
