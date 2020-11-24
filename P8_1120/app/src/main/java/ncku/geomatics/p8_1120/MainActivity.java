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
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView ivFish1, ivFish2, ivFish3, ivFish4, ivPenguin, ivBomb1;
    ConstraintLayout constraintLayout;
    ConstraintLayout.LayoutParams paramsFish1, paramsPenguin, paramsBear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.root);
        //設置重力感測器
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);
        //取得影像控制權
        ivPenguin = findViewById(R.id.imageViewPenguin);
        ivFish1 = findViewById(R.id.imageViewFish1);
//        ivFish2 = findViewById(R.id.imageViewFish2);
//        ivFish3 = findViewById(R.id.imageViewFish3);
//        ivFish4 = findViewById(R.id.imageViewFish4);
//        ivBomb1 = findViewById(R.id.imageViewBear1);
        //取得影像參數
        paramsPenguin = (ConstraintLayout.LayoutParams) ivPenguin.getLayoutParams();
        paramsFish1 = (ConstraintLayout.LayoutParams) ivFish1.getLayoutParams();
        paramsBear = (ConstraintLayout.LayoutParams) ivBomb1.getLayoutParams();
        //開啟首頁
        Intent it = new Intent();
        it.setClass(this, PopupActivity.class);
        startActivityForResult(it, 99);
    }

    public void addImage() {
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.fish);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv.getLayoutParams();
        params.topToTop=0;
        params.bottomToBottom=0;
        params.endToEnd=0;
        params.startToStart=0;
        do {
            //隨機設定影像XY座標
            Random random = new Random();
            params.verticalBias = ((float) random.nextInt(97) + 2) / 100;
            params.horizontalBias = ((float) random.nextInt(97) + 2) / 100;
            iv.setLayoutParams(params);
            //計時器重新開始計算
            Chronometer timer = findViewById(R.id.chronometer);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
        } while (params.horizontalBias < 0.6 && params.horizontalBias > 0.4 && params.verticalBias < 0.6 && params.verticalBias > 0.4);
        constraintLayout.addView(iv);
        setContentView(constraintLayout);
    }

    public void setImagePosition(ConstraintLayout.LayoutParams params, ImageView imageView) {
        //當影像在中心區域時，重新設置座標
        do {
            //隨機設定影像XY座標
            Random random = new Random();
            params.verticalBias = ((float) random.nextInt(97) + 2) / 100;
            params.horizontalBias = ((float) random.nextInt(97) + 2) / 100;
            imageView.setLayoutParams(params);
        } while (params.horizontalBias < 0.6 && params.horizontalBias > 0.4 && params.verticalBias < 0.6 && params.verticalBias > 0.4);
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
                //設定星星位置
                //setImagePosition(paramsFish1, ivFish1);
                addImage();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //移動小精靈
        float x = event.values[0];
        float y = event.values[1];
        paramsPenguin = (ConstraintLayout.LayoutParams) ivPenguin.getLayoutParams();
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
        paramsPenguin.verticalBias = (float) ((7.5 + y) / 15);
        paramsPenguin.horizontalBias = (float) ((7.5 - x) / 15);
        ivPenguin.setLayoutParams(paramsPenguin);
        //小精靈吃到星星以後重新設定星星位置
//        if (Math.abs(paramsPenguin.verticalBias - paramsFish1.verticalBias) < 0.05 && Math.abs(paramsPenguin.horizontalBias - paramsFish1.horizontalBias) < 0.05) {
//            setImagePosition(paramsFish1, ivFish1);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}