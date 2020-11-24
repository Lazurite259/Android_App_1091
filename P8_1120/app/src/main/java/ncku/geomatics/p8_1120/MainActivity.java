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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
    ArrayList<String> alHistory = new ArrayList<>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //設置重力感測器
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor srA = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, srA, SensorManager.SENSOR_DELAY_NORMAL);
        //取得控制權
        ivPenguin = findViewById(R.id.imageViewPenguin);
        ivFish = findViewById(R.id.imageViewFish);
        ivBear = findViewById(R.id.imageViewBear);
        timer = findViewById(R.id.chronometer);
        tvC = findViewById(R.id.textViewCount);
        //取得影像參數
        paramsPenguin = (ConstraintLayout.LayoutParams) ivPenguin.getLayoutParams();
        paramsFish = (ConstraintLayout.LayoutParams) ivFish.getLayoutParams();
        paramsBear = (ConstraintLayout.LayoutParams) ivBear.getLayoutParams();
        //開啟首頁
        it.setClass(this, PopupActivity.class);
        String[] history = {"日期", "時間", "名字", "數量", "花費時間"};
        alHistory.addAll(Arrays.asList(history));
        it.putExtra("history", alHistory);
        startActivityForResult(it, 99);
    }

    public void setImagePosition(ConstraintLayout.LayoutParams params, ImageView imageView) {
        //當影像在中心區域時，重新設置座標
        do {
            //隨機設定影像XY座標
            Random random = new Random();
            params.verticalBias = ((float) random.nextInt(97) + 2) / 100;
            params.horizontalBias = ((float) random.nextInt(97) + 2) / 100;
            imageView.setLayoutParams(params);
        } while (params.horizontalBias < 0.6 && params.horizontalBias > 0.4
                && params.verticalBias < 0.6 && params.verticalBias > 0.4);
    }

    public void reset() {
        //設定魚位置
        setImagePosition(paramsFish, ivFish);
        //計時器重新開始計算
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        tvC.setText(count + ""); //顯示次數
        ivFish.setVisibility(View.VISIBLE); //顯示魚
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            if (data != null) {
                name = data.getStringExtra("name");
            }
            if (!name.equals("")) {
                reset();
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //移動企鵝
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
        //企鵝吃到魚以後重新設定魚位置
        if (Math.abs(paramsPenguin.verticalBias - paramsFish.verticalBias) < 0.06
                && Math.abs(paramsPenguin.horizontalBias - paramsFish.horizontalBias) < 0.06) {
            count++; //數量加一
            tvC.setText(count + "");
            setImagePosition(paramsFish, ivFish);
            if (count >= 5) {
                //顯示熊
                ivBear.setVisibility(View.VISIBLE);
                setImagePosition(paramsBear, ivBear);
            }
        }
        //企鵝碰到熊以後結束，並跳出警示窗
        if (Math.abs(paramsPenguin.verticalBias - paramsBear.verticalBias) < 0.05
                && Math.abs(paramsPenguin.horizontalBias - paramsBear.horizontalBias) < 0.05) {
            String endTime = timer.getText().toString(); //結束時間
            //建立警示窗
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.bomb)
                    .setTitle("遊戲結束").setMessage("花費時間：" + endTime + "\n吃魚數量：" + count)
                    .setPositiveButton("重新開始", this)
                    .setNegativeButton("返回首頁", this)
                    .setCancelable(false)
                    .show();
            timer.stop(); //停止計時
            //魚位置歸零、消失
            paramsFish.verticalBias = 0;
            paramsFish.horizontalBias = 0;
            ivFish.setVisibility(View.GONE);
            //熊位置歸零、消失
            paramsBear.verticalBias = 0;
            paramsBear.horizontalBias = 0;
            ivBear.setVisibility(View.GONE);
            //取得現在時間
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            String date = dateFormat.format(calendar.getTime());
            String time = timeFormat.format(calendar.getTime());
            //新增歷史紀錄
            String[] newHistory = {date, time, name, count + "", endTime};
            alHistory.addAll(5, Arrays.asList(newHistory));
            it.putExtra("history", alHistory);
            count = 0; //歸零
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            reset(); //重設
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            //回到首頁
            startActivityForResult(it, 99);
        }
    }
}