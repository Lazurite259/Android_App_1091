package ncku.geomatics.p0926;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//Step 1，實作監聽器
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Step 2，設定按鈕可以監聽onClick事件
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(this);

        //Step 2，設定button, textView可以監聽onLongClick事件
        btn.setOnLongClickListener(this);
        TextView tv = findViewById(R.id.textView);
        tv.setOnLongClickListener(this);

        //Step 2，設定textView2可以監聽onTouch事件
        TextView tv2 = findViewById(R.id.textView2);
        tv2.setOnTouchListener(this);
    }

    int cnt = 0;
    @Override
    public void onClick(View v) {
        //Step 3，實作函數
        cnt++;
        TextView tv = findViewById(R.id.textView);
        //tv.setText(cnt+"");
        tv.setText(Integer.toString(cnt));
    }

    @Override
    public boolean onLongClick(View v) {
        TextView tv = findViewById(R.id.textView);
        if(v.getId() == R.id.textView){
            cnt = 0;
        }
        else{
            cnt += 2;
        }
        tv.setText(Integer.toString(cnt));
        return true;
    }

    Vibrator vib;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            vib.vibrate(5000);
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            vib.cancel();
        }
        return true;
    }
}