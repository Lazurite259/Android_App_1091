package ncku.geomatics.p3_0926;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{ //設定監聽器

    Vibrator vib;
    TextView tv;
    EditText etn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); //設定振動器
        tv = findViewById(R.id.textViewNum);
        etn = findViewById(R.id.editTextNumber);

        //設定按鈕可以監聽onClick，onLongClick事件
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        btn1.setOnLongClickListener(this);
        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(this);
        btn2.setOnLongClickListener(this);
    }

    String multiple;
    int num = 0;
    @Override
    public void onClick(View v) {
        multiple = etn.getText().toString();
        if(v.getId() == R.id.button1){
            num++;
        }
        else if(v.getId() == R.id.button2){
            num--;
        }
        tv.setText(Integer.toString(num));
        if(!multiple.equals("")) {
            Vibrate();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        multiple = etn.getText().toString();
        if(v.getId() == R.id.button1){
            num += 2;
        }
        else if(v.getId() == R.id.button2){
            num -= 2;
        }
        tv.setText(Integer.toString(num));
        if(!multiple.equals("")) {
            Vibrate();
        }
        return true;
    }

    int multi;
    public void Vibrate(){
        multi = Integer.parseInt(multiple);
        //如果計數值為輸入值的倍數，則震動3秒
        if (num!=0 && multi!=0 && num%multi == 0) {
            vib.vibrate(3000);
        }
    }
}