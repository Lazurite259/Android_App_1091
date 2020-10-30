package ncku.geomatics.p1_0918;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView myTxt;
    EditText myEdit;
    String iniText;
    ColorStateList iniColor;
    int iniSize=30;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取得元件(文字顯示框)的控制權
        myTxt = findViewById(R.id.textView);
        //設定文字初始內容
        iniText = String.valueOf(myTxt.getText());
        iniColor = myTxt.getTextColors();
        //取得文字輸入框的控制權
        myEdit = findViewById(R.id.editTextName);
    }

    public void enter(View v)
    {
        //取得文字輸入框的文字內容，並且交給字串變數
        String name = myEdit.getText().toString();
        //設定文字內容
        myTxt.setText(name.concat(", 您好！"));
    }

    int s= iniSize;
    public void enlarge(View v)
    {
        //設定文字大小
        s += 10;
        myTxt.setTextSize(s);
    }
    public void reduce(View v)
    {
        //設定文字大小
        s -= 10;
        myTxt.setTextSize(s);
    }

    public void red(View v)
    {
        //設定文字顏色
        myTxt.setTextColor(Color.RED);
    }
    public void blue(View v)
    {
        //設定文字顏色
        myTxt.setTextColor(Color.BLUE);
    }

    public void bold(View v)
    {
        //設定文字字體
        myTxt.setTypeface(null, Typeface.BOLD);
    }
    public void italic(View v)
    {
        //設定文字字體
        myTxt.setTypeface(null, Typeface.ITALIC);
    }

    public void clear(View v)
    {
        //清除文字輸入框的文字內容
        myEdit.getText().clear();
    }

    public void reset(View v)
    {
        //設定文字回到初始值
        myTxt.setText(iniText);
        myTxt.setTextSize(iniSize);
        myTxt.setTextColor(iniColor);
        myTxt.setTypeface(null, Typeface.NORMAL);
    }
}