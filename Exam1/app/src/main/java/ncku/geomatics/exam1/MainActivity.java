package ncku.geomatics.exam1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        View.OnLongClickListener,
        CompoundButton.OnCheckedChangeListener,
        RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener{

    String[] president={"習大", "川普", "老杜", "恩恩"};
    ArrayList<String> alPresident =new ArrayList<>();
    CheckBox cbC, cbU, cbP, cbN;
    TextView tvAge, tvAll, tvC, tvU, tvP, tvN;
    String name, blood="A", country;
    RadioGroup rdBlood;
    LinearLayout llC, llU, llP, llN;
    Vibrator vib;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        tvAge=findViewById(R.id.textViewAge);
        tvAll=findViewById(R.id.textViewAll);
        tvC=findViewById(R.id.textViewChinaNum);
        tvU=findViewById(R.id.textViewUSANum);
        tvP=findViewById(R.id.textViewPhipNum);
        tvN=findViewById(R.id.textViewNKNum);
        llC=findViewById(R.id.layoutChina);
        llU=findViewById(R.id.layoutUSA);
        llP=findViewById(R.id.layoutPhip);
        llN=findViewById(R.id.layoutNK);
        cbC=findViewById(R.id.checkBoxChina);
        cbC.setOnCheckedChangeListener(this);
        cbU=findViewById(R.id.checkBoxUSA);
        cbU.setOnCheckedChangeListener(this);
        cbP=findViewById(R.id.checkBoxPhilippines);
        cbP.setOnCheckedChangeListener(this);
        cbN=findViewById(R.id.checkBoxNK);
        cbN.setOnCheckedChangeListener(this);
        alPresident.addAll(Arrays.asList(president));
        setSpinner(alPresident, R.id.spinnerName);
        ((Spinner)findViewById(R.id.spinnerName)).setOnItemSelectedListener(this);
        ((Button)findViewById(R.id.buttonTen)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonTen)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.buttonFive)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonFive)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.buttonOne)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonOne)).setOnLongClickListener(this);
        ((Button)findViewById(R.id.buttonSick)).setOnClickListener(this);
        rdBlood=findViewById(R.id.rdBlood);
        rdBlood.setOnCheckedChangeListener(this);
    }

    public void setSpinner(ArrayList<String> arrayList, int id){
        ArrayAdapter<String> adp=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item ,arrayList
        );
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner)findViewById(id)).setAdapter(adp);
    }

    public void setTextViewAll(){
        tvAll.setText("我是"+name+"，今年"+num+"歲，血型"+blood+"型，曾造訪"+country);
    }

    public void Vibrate(){
        if (sumC>=3 || sumU>=3 || sumP>=3 || sumN>=3) {
            vib.vibrate(3000);
        }
    }

    int num=0;
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonTen){
            num+=10;
        }
        else if(v.getId()==R.id.buttonFive){
            num+=5;
        }
        else if(v.getId()==R.id.buttonOne){
            num+=1;
        }
        else if(v.getId()==R.id.buttonSick){
            if(sumC!=0){
                tvC.setText(Integer.toString(sumC));
                llC.setVisibility(View.VISIBLE);
            }
            if(sumU!=0){
                tvU.setText(Integer.toString(sumU));
                llU.setVisibility(View.VISIBLE);
            }
            if(sumP!=0){
                tvP.setText(Integer.toString(sumP));
                llP.setVisibility(View.VISIBLE);
            }
            if(sumN!=0){
                tvN.setText(Integer.toString(sumN));
                llN.setVisibility(View.VISIBLE);
            }
            Vibrate();
        }
        if (num >= 99) {
            num=99;
        }
        tvAge.setText(Integer.toString(num));
        setTextViewAll();
    }

    @Override
    public boolean onLongClick(View v) {
        if(num!=0){
            if(v.getId()==R.id.buttonTen){
                num-=10;
            }
            else if(v.getId()==R.id.buttonFive){
                num-=5;
            }
            else if(v.getId()==R.id.buttonOne){
                num-=1;
            }
        }
        if(num<=0) {
            num = 0;
        }
        tvAge.setText(Integer.toString(num));
        setTextViewAll();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        name=((TextView) view).getText().toString();
        setTextViewAll();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    int sumC=0, sumU=0, sumP=0, sumN=0;
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        country="";
        if(cbC.isChecked()){
            country+="中國 ";
            sumC++;
        }
        if(cbU.isChecked()){
            country+="美國 ";
            sumU++;
        }
        if(cbP.isChecked()){
            country+="菲律賓 ";
            sumP++;
        }
        if(cbN.isChecked()){
            country+="北韓 ";
            sumN++;
        }
        setTextViewAll();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(rdBlood.getCheckedRadioButtonId()==R.id.radioButtonA){
            blood="A";
        }
        else if(rdBlood.getCheckedRadioButtonId()==R.id.radioButtonB){
            blood="B";
        }
        else if(rdBlood.getCheckedRadioButtonId()==R.id.radioButtonO){
            blood="O";
        }
        else if(rdBlood.getCheckedRadioButtonId()==R.id.radioButtonAB){
            blood="AB";
        }
        setTextViewAll();
    }
}