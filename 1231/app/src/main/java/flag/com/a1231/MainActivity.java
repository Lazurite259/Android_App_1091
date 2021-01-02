package flag.com.a1231;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static androidx.constraintlayout.widget.Constraints.*;

public class MainActivity extends AppCompatActivity implements
        OnTouchListener,
        SensorEventListener
{

    Vibrator vib;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在代碼中設置字體顏色
        setContentView(R.layout.activity_main);
        TextView tv1=(TextView)findViewById(R.id.textView);
        tv1.setTextColor(Color.RED);
        TextView tv2=(TextView)findViewById(R.id.textView2);
        tv2.setTextColor(Color.YELLOW);

        //打地鼠背景
        ImageView iv1=(ImageView)findViewById(R.id.imageView);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);

        //地鼠位置
        ImageView iv2=(ImageView)findViewById(R.id.imageView2);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) iv2.getLayoutParams();

        //監聽手機傾斜
        SensorManager sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sr1=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this,sr1,SensorManager.SENSOR_DELAY_NORMAL);

        //震動
        Vibrator vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        //計時器Timer，讓地鼠每隔3秒換位置
        Timer timer=new Timer(true);
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {

            }
        };
        timer.schedule(timerTask, 3000,3000);

        //監聽打地鼠背景
        iv1.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //event,getRawX()獲取x座標
                float x=(event.getRawX()/720);
                float y=(event.getY()/590);

                String s2="x="+String.valueOf(x)+"\ny="+
                        String.valueOf(y);
                tv2.setText(s2);

                String s1="x="+String.valueOf(event.getRawX())+"\ny="+
                        String.valueOf(event.getY());
                tv1.setText(s1);

                //碰到地鼠
                float c=0;
                float d=0;
                int b=0;
                b = (int) (Math.floor(Math.random()*9)+1);
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
                if(Math.abs(params.verticalBias-y)<(float)0.1 && Math.abs(params.horizontalBias-x)<(float)0.1 &&
                Math.abs(params.verticalBias-params2.verticalBias)<(float)0.15 && Math.abs(params.horizontalBias-params2.horizontalBias)<(float)0.15){
                    params.verticalBias=d;
                    params.horizontalBias=c;
                    iv2.setLayoutParams(params);
                    vib.vibrate(300);
                }


                return true;
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    //槌子的座標轉換
    float xp=0, yp=0;
    ConstraintLayout.LayoutParams params2;
    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tv3=findViewById(R.id.textView3);
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        tv3.setText("x="+x+"\ny="+y+"\nz="+z);
        ImageView iv3 = (ImageView) findViewById(R.id.imageView3); //槌子
         params2 = (ConstraintLayout.LayoutParams) iv3.getLayoutParams();




        if (x > 0 && y==0) {
            xp += x / 200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;

            iv3.setLayoutParams(params2);
        }
        if(x>0 && y<0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if(x>0 && y>0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if (x < 0 && y==0) {
            xp += x / 200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if(x<0 && y<0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if(x<0 && y>0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if(x==0 && y<0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }
        if(x==0 && y>0){
            xp+=x/200;
            yp+=y/200;
            params2.verticalBias = (10 + yp) / 20f;
            params2.horizontalBias = (10 - xp) / 20f;
            iv3.setLayoutParams(params2);
        }


        if((int)yp==10){
            yp=-10f;
            iv3.setLayoutParams(params2);
        }
        else if((int)yp==-10){
            yp=10f;
            iv3.setLayoutParams(params2);
        }

        if((int)xp==-10){
            xp=10f;
            iv3.setLayoutParams(params2);
        }
        else if((int)xp==10){
            xp=-10f;
            iv3.setLayoutParams(params2);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}