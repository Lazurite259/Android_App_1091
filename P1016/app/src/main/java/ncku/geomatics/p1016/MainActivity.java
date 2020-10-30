package ncku.geomatics.p1016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup t=findViewById(R.id.type);
        t.setOnCheckedChangeListener(this);
        CheckBox c1=findViewById(R.id.checkBox1);
        c1.setOnCheckedChangeListener(this);
        CheckBox c2=findViewById(R.id.checkBox2);
        c2.setOnCheckedChangeListener(this);
        CheckBox c3=findViewById(R.id.checkBox3);
        c3.setOnCheckedChangeListener(this);
        CheckBox c4=findViewById(R.id.checkBox4);
        c4.setOnCheckedChangeListener(this);
    }

    void buy(){
        TextView tv=findViewById(R.id.textView);
        RadioGroup t=findViewById(R.id.type);
        if(t.getCheckedRadioButtonId()==R.id.radioButton1)
        {
            //全票被點選
            tv.setText("買全票");
        }
        else if(t.getCheckedRadioButtonId()==R.id.radioButton2)
        {
            //半票被點選
            tv.setText("買半票");
        }
        else
        {
            //敬老票被點選
            tv.setText("買敬老票");
        }
    }

    void order(){
        String str="";
        CheckBox c1=findViewById(R.id.checkBox1);
        CheckBox c2=findViewById(R.id.checkBox2);
        CheckBox c3=findViewById(R.id.checkBox3);
        CheckBox c4=findViewById(R.id.checkBox4);
        ImageView iv5=findViewById(R.id.imageView5);
        ImageView iv6=findViewById(R.id.imageView6);
        ImageView iv7=findViewById(R.id.imageView7);
        ImageView iv8=findViewById(R.id.imageView8);
        if(c1.isChecked())
        {
            str+="漢堡\n";
            iv5.setVisibility(View.VISIBLE);
        }
        else
        {
            iv5.setVisibility(View.GONE);
        }
        if(c2.isChecked())
        {
            str+="薯條\n";
            iv6.setVisibility(View.VISIBLE);
        }
        else
        {
            iv6.setVisibility(View.GONE);
        }
        if(c3.isChecked())
        {
            str+="可樂\n";
            iv7.setVisibility(View.VISIBLE);
        }
        else
        {
            iv7.setVisibility(View.GONE);
        }
        if(c4.isChecked())
        {
            str+="玉米濃湯\n";
            iv8.setVisibility(View.VISIBLE);
        }
        else
        {
            iv8.setVisibility(View.GONE);
        }
        TextView tv=findViewById(R.id.textView);
        tv.setText("你點購的餐點是：\n"+str);
    }

    public void onClick(View v){
        //buy();
        order();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        buy();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        order();
    }
}