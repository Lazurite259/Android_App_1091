package flag.com.a1231;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import static androidx.constraintlayout.widget.Constraints.*;

//Timer的class，固定時間呼叫
class Timer {
    private final Handler handler;
    private boolean paused;

    private int interval;

    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            if (!paused) {
                runnable.run();
                Timer.this.handler.postDelayed(this, interval);
            }
        }
    };

    private final Runnable runnable;

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void startTimer() {
        paused = false;
        handler.postDelayed(task, interval);
    }

    public void stopTimer() {
        paused = true;
    }

    public Timer(Runnable runnable, int interval, boolean started) {
        handler = new Handler();
        this.runnable = runnable;
        this.interval = interval;
        if (started)
            startTimer();
    }
}

public class MainActivity extends AppCompatActivity implements
        OnTouchListener,
        SensorEventListener {

    //時間
    int t = 0;
    //震動
    Vibrator vib;
    //得分變數
    int score = 0;

    TextView tv1, tv2, tv4;
    ConstraintLayout.LayoutParams params, params2;
    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在代碼中設置字體顏色
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.textView);
        tv1.setTextColor(Color.RED);
        tv2 = findViewById(R.id.textView2);
        tv2.setTextColor(Color.YELLOW);
        tv4 = findViewById(R.id.textView4);
        tv4.setTextColor(Color.DKGRAY);

        //打地鼠背景
        ImageView iv1 = findViewById(R.id.imageView);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);

        //地鼠位置
        iv2 = findViewById(R.id.imageView2);
        params = (ConstraintLayout.LayoutParams) iv2.getLayoutParams();

        //監聽手機傾斜
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sr1 = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, sr1, SensorManager.SENSOR_DELAY_NORMAL);

        //震動
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //監聽打地鼠背景
        iv1.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView iv3 = findViewById(R.id.imageView3);

        iv3.setImageResource(R.drawable.gg);
        Animation am = new RotateAnimation(0, -30, 250, 100);
        am.setDuration(300);
        am.setRepeatCount(0);
        iv3.setAnimation(am);
        am.start();
        //event,getRawX()獲取x座標
        float x = (event.getRawX() / 720);
        float y = (event.getY() / 590);

        String s2 = "x=" + x + "\ny=" + y;
        tv2.setText(s2);

        String s1 = "x=" + event.getRawX() + "\ny=" + event.getY();
        tv1.setText(s1);

        //碰到地鼠
        float c = 0;
        float d = 0;
        int b = 0;
        b = (int) (Math.floor(Math.random() * 9) + 1);
        switch (b) {
            //地鼠
            //左上
            case 1:
                c = (float) 0.1962;
                d = (float) 0.1332;
                break;
            //中上
            case 2:
                c = (float) 0.4740;
                d = (float) 0.1332;
                break;
            //右上
            case 3:
                c = (float) 0.7397;
                d = (float) 0.1332;
                break;
            //左中
            case 4:
                c = (float) 0.1962;
                d = (float) 0.4721;
                break;
            //中中
            case 5:
                c = (float) 0.4740;
                d = (float) 0.4721;
                break;
            //右中
            case 6:
                c = (float) 0.7397;
                d = (float) 0.4721;
                break;
            //左下
            case 7:
                c = (float) 0.1962;
                d = (float) 0.8010;
                break;
            //中下
            case 8:
                c = (float) 0.4740;
                d = (float) 0.8010;
                break;
            //右下
            case 9:
                c = (float) 0.7397;
                d = (float) 0.8010;
                break;
        }
        //度數的新位置
        if (Math.abs(params.verticalBias - y) < (float) 0.1 && Math.abs(params.horizontalBias - x) < (float) 0.1 &&
                Math.abs(params.verticalBias - params2.verticalBias) < (float) 0.15 && Math.abs(params.horizontalBias - params2.horizontalBias) < (float) 0.15) {
            //地鼠新位置
            params.verticalBias = d;
            params.horizontalBias = c;
            iv2.setLayoutParams(params);
            //震動
            vib.vibrate(300);
            //得分+1
            score += 1;
            tv4.setText("得分" + score);
            //槌子
        }
        return true;
    }

    //槌子的座標轉換
    float xp = 0, yp = 0;

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tv3 = findViewById(R.id.textView3);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        tv3.setText("x=" + x + "\ny=" + y + "\nz=" + z);

        //槌子
        ImageView iv3 = findViewById(R.id.imageView3);
        params2 = (ConstraintLayout.LayoutParams) iv3.getLayoutParams();

        if (x > 0 && y == 0) {
            xp += x / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;

            iv3.setLayoutParams(params2);
        }
        if (x > 0 && y < 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x > 0 && y > 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x < 0 && y == 0) {
            xp += x / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x < 0 && y < 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x < 0 && y > 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x == 0 && y < 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x == 0 && y > 0) {
            xp += x / 100;
            yp += y / 100;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }

        if ((int) yp == 10) {
            yp = -10f;
            iv3.setLayoutParams(params2);
        } else if ((int) yp == -10) {
            yp = 10f;
            iv3.setLayoutParams(params2);
        }

        if ((int) xp == -10) {
            xp = 10f;
            iv3.setLayoutParams(params2);
        } else if ((int) xp == 10) {
            xp = -10f;
            iv3.setLayoutParams(params2);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void change() {
        ImageView iv2 = findViewById(R.id.imageView2);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv2.getLayoutParams();
        float c = 0;
        float d = 0;
        int b = 0;
        b = (int) (Math.floor(Math.random() * 9) + 1);
        switch (b) {
            //地鼠
            //左上
            case 1:
                c = (float) 0.1962;
                d = (float) 0.1332;
                break;
            //中上
            case 2:
                c = (float) 0.4740;
                d = (float) 0.1332;
                break;
            //右上
            case 3:
                c = (float) 0.7397;
                d = (float) 0.1332;
                break;
            //左中
            case 4:
                c = (float) 0.1962;
                d = (float) 0.4721;
                break;
            //中中
            case 5:
                c = (float) 0.4740;
                d = (float) 0.4721;
                break;
            //右中
            case 6:
                c = (float) 0.7397;
                d = (float) 0.4721;
                break;
            //左下
            case 7:
                c = (float) 0.1962;
                d = (float) 0.8010;
                break;
            //中下
            case 8:
                c = (float) 0.4740;
                d = (float) 0.8010;
                break;
            //右下
            case 9:
                c = (float) 0.7397;
                d = (float) 0.8010;
                break;
        }
        params.verticalBias = d;
        params.horizontalBias = c;
        iv2.setLayoutParams(params);
    }

    //地鼠每隔4秒換位置
    Timer changeplace = new Timer(new Runnable() {
        @Override
        public void run() {
            change();
        }
    }, 4000, true);

    Timer calculate = new Timer(new Runnable() {
        @Override
        public void run() {
            TextView tv5 = findViewById(R.id.textView5);
            if (t < 90) {
                t += 1;
                tv5.setText("時間:" + t);
            }
        }
    }, 1000, true);

}