package me.mahakagg.humiditysensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/*
* Answers to homework questions
*
* Q1 - Which of the following features are provided by the SensorManager class?
* A1 - Methods to register and unregister sensor listeners.
    Methods to indicate whether a sensor is a wake-up sensor.
*
* Q2 - In which Activity lifecycle method should you register your sensor listeners?
* A2 - onStart()
*
* Q3 - What are best practices for using sensors in your app?
 * A3 - Register listeners for only for the sensors you're interested in.
    Test to make sure that a sensor is available on the device before you use the sensor.
    Check permissions for the sensor before you use it.
    Don't block onSensorChanged() to filter or transform incoming data.
* */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView textView;
    private SensorManager sensorManager;
    private Sensor humiditySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.humidity_textView);
        // get sensor service, sensor and change string if device doesn't have the sensor
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            String sensor_error = getResources().getString(R.string.error_no_sensor);
            if (humiditySensor == null){
                textView.setText(sensor_error);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // register listener
        if (humiditySensor != null){
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister listener
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float currentValue = event.values[0];
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            textView.setText(getResources().getString(R.string.humidity_sensor_text, currentValue));
        }
    }

    // no changes needed here
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
