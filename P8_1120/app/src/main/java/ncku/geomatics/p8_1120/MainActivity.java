package ncku.geomatics.p8_1120;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView ivStar, ivElf, ivBomb;
    ConstraintLayout.LayoutParams paramsStar, paramsElf, paramsBomb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);

        ivElf = findViewById(R.id.imageViewElf);
        ivStar = findViewById(R.id.imageViewStar);
        ivBomb = findViewById(R.id.imageViewBomb);
        paramsElf = (ConstraintLayout.LayoutParams) ivElf.getLayoutParams();
        paramsStar = (ConstraintLayout.LayoutParams) ivStar.getLayoutParams();
        paramsBomb = (ConstraintLayout.LayoutParams) ivBomb.getLayoutParams();
        Intent it = new Intent();
        it.setClass(this, PopupActivity.class);
        startActivityForResult(it, 99);
    }

    public void setImagePosition(ConstraintLayout.LayoutParams params, ImageView imageView) {
        do {
            Random random = new Random();
            params.verticalBias = ((float) random.nextInt(97) + 2) / 100;
            params.horizontalBias = ((float) random.nextInt(97) + 2) / 100;
            imageView.setLayoutParams(params);
            Chronometer timer = findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
        } while (paramsStar.horizontalBias < 0.6 && paramsStar.horizontalBias > 0.4 && paramsStar.verticalBias < 0.6 && paramsStar.verticalBias > 0.4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            String str = null;
            if (data != null) {
                str = data.getStringExtra("content");
            }
            if (str != null && str.equals("start")) {
                setImagePosition(paramsStar, ivStar);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        paramsElf = (ConstraintLayout.LayoutParams) ivElf.getLayoutParams();
        if (x > 7.5) {
            x = (float) 7.5;
        } else if (x < -7.5) {
            x = (float) -7.5;
        }
        if (y > 7.5) {
            y = (float) 7.5;
        } else if (y < -7.5) {
            y = (float) -7.5;
        }
        paramsElf.verticalBias = (float) ((7.5 + y) / 15);
        paramsElf.horizontalBias = (float) ((7.5 - x) / 15);
        ivElf.setLayoutParams(paramsElf);

        if (Math.abs(paramsElf.verticalBias - paramsStar.verticalBias) < 0.05 && Math.abs(paramsElf.horizontalBias - paramsStar.horizontalBias) < 0.05) {
            setImagePosition(paramsStar, ivStar);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}