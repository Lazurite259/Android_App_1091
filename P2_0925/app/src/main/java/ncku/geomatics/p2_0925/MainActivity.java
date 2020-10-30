package ncku.geomatics.p2_0925;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn0 = findViewById(R.id.button0);
        btn0.setOnClickListener(this);
        Button btn1 = findViewById(R.id.button1);
        btn1.setOnClickListener(this);
        Button btnB = findViewById(R.id.buttonBack);
        btnB.setOnClickListener(this);
        Button btnC = findViewById(R.id.buttonClear);
        btnC.setOnClickListener(this);
        Button btnCal = findViewById(R.id.buttonCount);
        btnCal.setOnClickListener(this);
    }

    String input;
    @Override
    public void onClick(View v) {
        TextView tvI = findViewById(R.id.textViewInput);
        TextView tv10 = findViewById(R.id.textView10Num);
        TextView tv16 = findViewById(R.id.textView16Num);
        input = tvI.getText().toString();
        if(v.getId() == R.id.button0){
            input += "0";
            tvI.setText(input);
        }
        else if(v.getId() == R.id.button1) {
            input += "1";
            tvI.setText(input);
        }
        else if(v.getId() == R.id.buttonBack) {
            if(input.length()>0){
                input = input.substring(0, input.length() - 1);
                tvI.setText(input);
            }
        }
        else if(v.getId() == R.id.buttonClear) {
            tvI.setText("");
        }
        else if(v.getId() == R.id.buttonCount) {
            int to10 = Integer.parseInt(input, 2); //2進位制轉10進位制
            tv10.setText(String.valueOf(to10));
            String to16 = Integer.toHexString(to10); //10進位制轉16進位制
            tv16.setText(to16);
        }
    }
}