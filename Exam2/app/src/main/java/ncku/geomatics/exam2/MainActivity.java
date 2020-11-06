package ncku.geomatics.exam2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        DialogInterface.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    Calendar calendar=Calendar.getInstance();
    TextView tvD, tvT, tvBN;
    ArrayList<Integer> alGirlNum =new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvD=findViewById(R.id.textViewDate);
        tvT=findViewById(R.id.textViewTime);
        tvBN=findViewById(R.id.textViewBoyNum);
        ((Button)findViewById(R.id.buttonDate)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonTime)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonPlus)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonMinus)).setOnClickListener(this);
        ((Spinner)findViewById(R.id.spinnerGirlNum)).setOnItemSelectedListener(this);
        alGirlNum.add(0);
        setSpinner(alGirlNum, R.id.spinnerGirlNum);
    }

    public void setSpinner(ArrayList<Integer> arrayList, int id){
        ArrayAdapter<Integer> adp=new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item ,arrayList
        );
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner)findViewById(id)).setAdapter(adp);
    }

    String date, time;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date=year+"/"+(month+1)+"/"+dayOfMonth;
        tvD.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        time=hourOfDay+":"+minute;
        tvT.setText(time);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    int year, month, dayOfMonth, hourOfDay, minute, boyNum, girlNum, girlSum;
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonDate){
            year=calendar.get(Calendar.YEAR);
            month=calendar.get(Calendar.MONTH);
            dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd=new DatePickerDialog(this, this, year, month, dayOfMonth);
            dpd.show();
        }
        else if(v.getId()==R.id.buttonTime){
            hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
            minute=calendar.get(Calendar.MINUTE);
            TimePickerDialog tpd=new TimePickerDialog(this, this, hourOfDay, minute, true);
            tpd.show();
        }
        else if(v.getId()==R.id.buttonPlus){
            boyNum++;
            girlNum++;
            alGirlNum.add(girlNum);
        }
        else if(v.getId()==R.id.buttonMinus){
            boyNum--;
            alGirlNum.remove(girlNum);
            girlNum--;
        }
        if(boyNum<=0){
            boyNum=0;
            girlNum=0;
        }
        else if(boyNum>=6){
            boyNum=6;
        }
        tvBN.setText(Integer.toString(boyNum));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        girlSum= Integer.parseInt(((TextView) view).getText().toString());
        if(boyNum+girlSum>6 || boyNum+girlSum<0){
            Toast.makeText(this, "請確認張數", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}