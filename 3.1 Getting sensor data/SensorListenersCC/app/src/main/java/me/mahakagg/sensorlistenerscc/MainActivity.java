package me.mahakagg.sensorlistenerscc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    // Individual light and proximity sensors.
    private Sensor mSensorProximity;
    private Sensor mSensorLight;

    // TextViews to display current sensor values
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        imageView = findViewById(R.id.imageView);
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
                // change background color based on readings from light sensor
                int shadeGrey = (int) currentValue;
                if (shadeGrey > 255){
                    shadeGrey = 255;
                }
                getWindow().getDecorView().setBackgroundColor(Color.rgb(shadeGrey, shadeGrey, shadeGrey));
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(getResources().getString(R.string.proximity_sensor_text, currentValue));
                if (currentValue == 0){
                    // near
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(128, 128));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(128, 128);
                    imageView.setLayoutParams(layoutParams);
//                    imageView.requestLayout();
//                    imageView.getLayoutParams().height = 128;
//                    imageView.getLayoutParams().width = 128;
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                else{
                    // far
//                    imageView.setLayoutParams(new LinearLayout.LayoutParams(64, 64));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(64, 64);
                    imageView.setLayoutParams(layoutParams);
//                    imageView.requestLayout();
//                    imageView.getLayoutParams().height = 64;
//                    imageView.getLayoutParams().width = 64;
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }
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
