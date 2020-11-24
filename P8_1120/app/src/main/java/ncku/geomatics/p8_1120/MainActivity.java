package ncku.geomatics.p8_1120;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
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
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements SensorEventListener,
        DialogInterface.OnClickListener {

    ImageView ivFish, ivPenguin, ivBear;
    ConstraintLayout.LayoutParams paramsFish, paramsPenguin, paramsBear;
    Intent it = new Intent();
    int count = 0;
    TextView tvC;
    Chronometer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設置重力感測器
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);
        //取得影像控制權
        ivPenguin = findViewById(R.id.imageViewPenguin);
        ivFish = findViewById(R.id.imageViewFish);
        ivBear = findViewById(R.id.imageViewBear);
        //取得影像參數
        paramsPenguin = (ConstraintLayout.LayoutParams) ivPenguin.getLayoutParams();
        paramsFish = (ConstraintLayout.LayoutParams) ivFish.getLayoutParams();
        paramsBear = (ConstraintLayout.LayoutParams) ivBear.getLayoutParams();
        //開啟首頁
        it.setClass(this, PopupActivity.class);
        startActivityForResult(it, 99);

        timer = findViewById(R.id.chronometer);
        tvC = findViewById(R.id.textViewCount);
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
                setImagePosition(paramsFish, ivFish);
                //計時器重新開始計算
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                tvC.setText(count + "");
                ivFish.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //移動小精靈
        float x = event.values[0];
        float y = event.values[1];
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
        if (Math.abs(paramsPenguin.verticalBias - paramsFish.verticalBias) < 0.06
                && Math.abs(paramsPenguin.horizontalBias - paramsFish.horizontalBias) < 0.06) {
            count++;
            tvC.setText(count + "");
            setImagePosition(paramsFish, ivFish);
            if (count >= 5) {
                ivBear.setVisibility(View.VISIBLE);
                setImagePosition(paramsBear, ivBear);
            }
        }
        if (Math.abs(paramsPenguin.verticalBias - paramsBear.verticalBias) < 0.04
                && Math.abs(paramsPenguin.horizontalBias - paramsBear.horizontalBias) < 0.04) {
            String endTime = timer.getText().toString();
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.bomb)
                    .setTitle("遊戲結束").setMessage("花費時間：" + endTime + "\n吃魚次數：" + count)
                    .setPositiveButton("重新開始", this)
                    .setNegativeButton("返回首頁", this)
                    .setCancelable(false)
                    .show();
            timer.stop();
            paramsFish.verticalBias = 0;
            paramsFish.horizontalBias = 0;
            ivFish.setVisibility(View.GONE);
            paramsBear.verticalBias = 0;
            paramsBear.horizontalBias = 0;
            ivBear.setVisibility(View.GONE);
            count = 0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            //設定星星位置
            setImagePosition(paramsFish, ivFish);
            //計時器重新開始計算
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            tvC.setText(count + "");
            ivFish.setVisibility(View.VISIBLE);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            startActivityForResult(it, 99);
        }
    }
}