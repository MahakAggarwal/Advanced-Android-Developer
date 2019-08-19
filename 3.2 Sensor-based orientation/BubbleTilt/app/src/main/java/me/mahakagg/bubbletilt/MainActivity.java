package me.mahakagg.bubbletilt;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.ImageView;

/*
* Answers to homework questions
*
* Q1 - Which sensors report values with respect to Earth's coordinate system, instead of reporting values with respect to the device-coordinate system?
* A1 - Orientation sensor
*
* Q2 - Which sensors can you use to get the orientation of the device?
* A2 - Geomagnetic field sensor and accelerometer
* */
public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagnetometer;
    private ImageView imageViewCenter;
    private ImageView imageViewRight;
    private ImageView imageViewLeft;
    private float[] accelerometerData = new float[3];
    private float[] magnetometerData = new float[3];
    private Display display;
    private static final float VALUE_DRIFT = 0.05f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageViewCenter = findViewById(R.id.imageViewCenter);
        imageViewRight = findViewById(R.id.imageViewRight);
        imageViewLeft = findViewById(R.id.imageViewLeft);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager != null){
            sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        }
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (windowManager != null) {
            display = windowManager.getDefaultDisplay();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType){
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetometerData = event.values.clone();
                break;
            default: break;
        }

        // rotation matrix
        float [] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData);

        // new adjusted matrix based on device orientation
        float[] rotationMatrixAdjusted = new float[9];
        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                rotationMatrixAdjusted = rotationMatrix.clone();
                break;
            case Surface.ROTATION_90:
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, rotationMatrixAdjusted);
                break;
            case Surface.ROTATION_180:
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_MINUS_X, SensorManager.AXIS_MINUS_Y, rotationMatrixAdjusted);
                break;
            case Surface.ROTATION_270:
                SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, rotationMatrixAdjusted);
                break;
        }
        // orientation angles
        float[] orientationValues = new float[3];
        if (rotationOK){
            SensorManager.getOrientation(rotationMatrixAdjusted, orientationValues);
        }

        // left to right tilt of the device. 0 is flat
        float roll = orientationValues[2];
        imageViewCenter.setAlpha(0f);
        imageViewLeft.setAlpha(0f);
        imageViewRight.setAlpha(0f);
        // move imageView here

        // since animations are not covered yet, they are not being used here
        if (Math.abs(roll) < VALUE_DRIFT) {
            roll = 0;
        }

        if (roll == 0){
            imageViewCenter.setAlpha(1f);
        }

        if(roll > 0){
            imageViewRight.setAlpha(roll);
        }
        else {
            imageViewLeft.setAlpha(Math.abs(roll));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        // register listeners
        if (sensorAccelerometer != null) {
            sensorManager.registerListener(this, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorMagnetometer != null) {
            sensorManager.registerListener(this, sensorMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // unregister listeners
        sensorManager.unregisterListener(this);
    }
}
