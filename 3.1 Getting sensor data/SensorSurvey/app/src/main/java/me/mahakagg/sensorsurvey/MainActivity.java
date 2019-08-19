package me.mahakagg.sensorsurvey;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
            StringBuilder sensorText = new StringBuilder();
            for (Sensor currentSensor : sensorList){
                sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
            }
            TextView sensorTextView = findViewById(R.id.sensor_list);
            sensorTextView.setText(sensorText);
        }
    }
}
