package me.mahakagg.sensorlisteners;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    // Individual light and proximity sensors.
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {

            // get instances of proximity and light sensors
            mSensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            String sensor_error = getResources().getString(R.string.error_no_sensor);

            // check if sensors exist or not
            if (mSensorLight == null) {
                mTextSensorLight.setText(sensor_error);
            }
            if (mSensorProximity == null) {
                mTextSensorProximity.setText(sensor_error);
            }
        }
    }

    // register sensor listeners
    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            sensorManager.registerListener(this, mSensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            sensorManager.registerListener(this, mSensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    // call when new sensor data is available
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(getResources().getString(R.string.light_sensor_text, currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.proximity_sensor_text, currentValue));
                break;
            default:
                // do nothing
        }
    }

    // method is called if sensor accuracy changes
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // unregister sensor listener
    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
