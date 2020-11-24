package ncku.geomatics.p1120;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

//Step 1
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Vibrator vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Step 2
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor srM = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this, srM, SensorManager.SENSOR_DELAY_NORMAL);

        vb = (Vibrator) getSystemService(VIBRATOR_SERVICE);
    }

    float[] mValues = null;
    float[] aValues = null;

    //Step 3
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            aValues = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mValues = event.values;
        }
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

//        String s = "X:" + aValues[0] + "\nY:" + aValues[1] + "\nZ:" + aValues[2];
//        ((TextView) findViewById(R.id.textView)).setText(s);
        if (mValues != null && aValues != null) {
            String s = "X:" + aValues[0] + "\nY:" + aValues[1] + "\nZ:" + aValues[2]
            + "\nX:" + mValues[0] + "\nY:" + mValues[1] + "\nZ:" + mValues[2];
            ((TextView) findViewById(R.id.textView)).setText(s);

            float[] Rotation = new float[9];
            float[] degree = new float[3];
            SensorManager.getRotationMatrix(Rotation, null, aValues, mValues);
            SensorManager.getOrientation(Rotation, degree);
            float angle = (float) Math.toDegrees(degree[0]);
            ((TextView) findViewById(R.id.textView)).setText(angle + "");

            ImageView iv = findViewById(R.id.imageView2);
            iv.setRotation(angle);
        }
        if (Math.abs(x) < 1 && Math.abs(y) < 1 && z < -9) {
            ((TextView) findViewById(R.id.textView)).setText("手機覆蓋");
        }
        if (Math.abs(x) + Math.abs(y) + Math.abs(z) > 32) {
            vb.vibrate(3000);
        }

        ImageView iv = findViewById(R.id.imageView);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv.getLayoutParams();
        params.verticalBias = (10 - y) / 20;
        params.horizontalBias = (10 + x) / 20;
        iv.setLayoutParams(params);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}