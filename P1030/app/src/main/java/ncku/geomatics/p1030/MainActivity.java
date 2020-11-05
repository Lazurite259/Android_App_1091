package ncku.geomatics.p1030;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        DialogInterface.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    AlertDialog.Builder bdr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ListView)findViewById(R.id.listView)).setOnItemClickListener(this);
        tos=Toast.makeText(this, "", Toast.LENGTH_SHORT);
        tos.setGravity(Gravity.TOP | Gravity.RIGHT, 0, 50);

        bdr=new AlertDialog.Builder(this);
        bdr.setIcon(android.R.drawable.presence_away);
        bdr.setTitle("歡迎");
        bdr.setMessage("交談窗示範教學");
        bdr.setPositiveButton("正向按鈕", this);
        bdr.setNegativeButton("負向按鈕", this);
        bdr.setNeutralButton("中立按鈕", this);
        bdr.setCancelable(false);
        //bdr.show();
    }

    String[] A={"球門", "虧", "傻瓜"};
    Toast tos;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0 && position<=2){ //Toast & SnackBar
            String ans=A[position];
            //Toast.makeText(this, ans, Toast.LENGTH_LONG).show();
            tos.setText(ans);
            tos.show();

            Snackbar.make(findViewById(R.id.root), ans, Snackbar.LENGTH_LONG).show();
        }
        else if(position==3){ //AlertDialog
            bdr.show();
        }
        else if(position==4){ //DatePicker
            DatePickerDialog dpd=new DatePickerDialog(this, this, 2017, 2,1);
            dpd.show();
        }
        else if(position==5){ //TimePicker
            TimePickerDialog tpd=new TimePickerDialog(this, this, 7, 12,true);
            tpd.show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            Toast.makeText(this, "正向按鈕被按下去了", Toast.LENGTH_LONG).show();
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE){
            Toast.makeText(this, "負向按鈕被按下去了", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "中立按鈕被按下去了", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String str=year+"/"+(month+1)+"/"+dayOfMonth;
        Snackbar.make(findViewById(R.id.listView), str, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String str=hourOfDay+":"+minute;
        Snackbar.make(findViewById(R.id.listView), str, Snackbar.LENGTH_LONG).show();
    }
}