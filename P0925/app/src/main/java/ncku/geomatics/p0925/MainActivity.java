package ncku.geomatics.p0925;

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
    }

    public void onClick(View v)
    {
        //步驟一，取得控制權
        EditText etl = findViewById(R.id.editTextLastName);
        EditText etf = findViewById(R.id.editTextFirstName);
        EditText etp = findViewById(R.id.editTextPhone);
        TextView tv4 = findViewById(R.id.textView4);

        //步驟二，讀取字串
        String name_l = etl.getText().toString();
        String name_f = etf.getText().toString();
        String phone = etp.getText().toString();

        //步驟三，輸出字串
        tv4.setText(name_l+name_f+"的電話是"+phone);

        //取出電話末三碼
        String last3num1 =  phone.substring(5);
        String last3num2 =  phone.substring(phone.length()-3);
        int num = Integer.parseInt(phone);
        num %= 1000; //num = num%1000
        String last3num3 = num+"";
        String last3num4 = Integer.toString(num);
    }
}