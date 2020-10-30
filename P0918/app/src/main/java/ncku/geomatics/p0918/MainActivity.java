package ncku.geomatics.p0918;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //取得元件(文字顯示框)的控制權
        TextView myTxt = findViewById(R.id.textView);
        //設定文字內容
        myTxt.setText("Hello");
    }

    int s=30;
    public void large(View v)
    {
        //取得文字輸入框的控制權
        EditText etp = findViewById(R.id.editTextPhone);
        //取得文字輸入框的文字內容，並且交給字串變數
        String str = etp.getText().toString();
        //取得元件(文字顯示框)的控制權
        TextView myTxt = findViewById(R.id.textView);
        //設定文字大小
        s += 10;
        myTxt.setTextSize(s);
        //設定文字內容
        myTxt.setText(str);
    }
}