package ncku.geomatics.p6_1030;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener,
        DialogInterface.OnClickListener,
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    String[] cinema={"請選擇影城", "台南大遠百威秀影城", "台南FOCUS威秀影城", "台南南紡威秀影城"};
    String[] movie={"鬼滅之刃劇場版 無限列車篇", "藥命時空", "入魔", "紫羅蘭永恆花園電影版", "電影哆啦A夢：大雄的新恐龍"};
    String[][] time={{"10:35", "12:50", "14:30", "17:20", "20:10", "23:00"},
            {"13:00", "17:25", "19:25", "22:00"},
            {"20:05", "23:50"},
            {"11:45"},
            {"09:50"}};
    ArrayList<String> alTime=new ArrayList<>();
    ArrayList<String> alCinema=new ArrayList<>();
    ArrayList<String> alMovie=new ArrayList<>();
    String cinemaChoice, movieChoice, choseDate, choseTime;
    LinearLayout llt, llm;
    Button btnBuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llt=findViewById(R.id.layoutTime);
        llm=findViewById(R.id.layoutMovie);
        btnBuy=findViewById(R.id.buttonBuy);
        //設定一開始的下拉選單
        alCinema.addAll(Arrays.asList(cinema));
        setSpinner(alCinema, R.id.spinnerCinema);

        ((Spinner)findViewById(R.id.spinnerCinema)).setOnItemSelectedListener(this);
        ((ListView)findViewById(R.id.listViewMovie)).setOnItemClickListener(this);
        ((Spinner)findViewById(R.id.spinnerTime)).setOnItemSelectedListener(this);
        btnBuy.setOnClickListener(this);
    }

    //設定下拉選單
    public void setSpinner(ArrayList<String> arrayList, int id){
        ArrayAdapter<String> adp=new ArrayAdapter<>(
                this, R.layout.spinner_item, arrayList
        );
        adp.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ((Spinner)findViewById(id)).setAdapter(adp);
    }

    //設定電影的選單
    public void setMovieListView(){
        ArrayAdapter<String> adp2=new ArrayAdapter<>(
                this, R.layout.spinner_item, alMovie
        );
        ((ListView)findViewById(R.id.listViewMovie)).setAdapter(adp2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spinnerCinema){
            cinemaChoice=((TextView) view).getText().toString();
            alMovie.clear();
            if(cinemaChoice!="請選擇影城"){
                llm.setVisibility(View.VISIBLE);
                alMovie.addAll(Arrays.asList(movie));
                if(cinemaChoice=="台南FOCUS威秀影城"){
                    alMovie.remove("電影哆啦A夢：大雄的新恐龍");
                }
                else if(cinemaChoice=="台南南紡威秀影城"){
                    alMovie.remove("紫羅蘭永恆花園電影版");
                }
                else {
                    alMovie.remove("紫羅蘭永恆花園電影版");
                    alMovie.remove("電影哆啦A夢：大雄的新恐龍");
                }
                setMovieListView();
            }
            else{
                llm.setVisibility(View.GONE);
            }
            llt.setVisibility(View.GONE);
            btnBuy.setVisibility(View.GONE);
        }
        else if(parent.getId()==R.id.spinnerTime){
            String timeChoice=((TextView) view).getText().toString();
            String[] splitTime=timeChoice.split(":"); //分別取得"時"與"分"
            choseTime=splitTime[0]+"時"+splitTime[1]+"分";
            btnBuy.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    Calendar calendar=Calendar.getInstance();
    int hourOfDay, minute, year, month, dayOfMonth;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        movieChoice=((TextView) view).getText().toString();
        //取得今天的日期
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd=new DatePickerDialog(this, this, year, month, dayOfMonth);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis()); //只能選取今天以後的日期
        dpd.show();
    }

    int[] selectDate;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        choseDate=year+"年"+(month+1)+"月"+dayOfMonth+"日";
        selectDate= new int[]{year, month, dayOfMonth};
        //取得現在的時間
        hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        TimePickerDialog tpd=new TimePickerDialog(this, this, hourOfDay, minute, true);
        tpd.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int min) {
        if(selectDate[0]==year && selectDate[1]==month && selectDate[2]==dayOfMonth){
            if(hour<hourOfDay || (hour==hourOfDay && min<=minute)){
                hour=hourOfDay;
                min=minute;
            }
        }
        llt.setVisibility(View.VISIBLE);
        int movieIndex=Arrays.asList(movie).indexOf(movieChoice);
        alTime.clear();
        for(int i=0;i<time[movieIndex].length;i++){
            int timeHour= Integer.parseInt(time[movieIndex][i].substring(0, 2));
            int timeMinute= Integer.parseInt(time[movieIndex][i].substring(3));
            if(timeHour>hour || (timeHour==hour && timeMinute>=min)){
                alTime.add(time[movieIndex][i]);
            }
        }
        setSpinner(alTime, R.id.spinnerTime);
    }

    @Override
    public void onClick(View v) {
        Random r=new Random();
        //取得隨機英文排數
        String alphabet="ABCDEFGHIJKLMNO";
        int alphabetIndex=r.nextInt(14);
        String seatAlphabet=alphabet.substring(alphabetIndex, alphabetIndex+1);
        int seatNumber=r.nextInt(19)+1; //取得隨機座位號
        //設定交談窗
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.movie)
                .setTitle("購票資訊")
                .setMessage("影城："+cinemaChoice+"\n電影："+movieChoice+"\n日期："+choseDate+"\n場次："+choseTime+"\n座位："+seatAlphabet+"排"+seatNumber+"號")
                .setPositiveButton("確定", this)
                .setNegativeButton("取消", this)
                .setCancelable(false)//不能使用手機的返回鍵取消
                .show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            Snackbar.make(findViewById(R.id.root), "購票成功", Snackbar.LENGTH_LONG).show(); //顯示即時訊息
        }
        else if(which==DialogInterface.BUTTON_NEGATIVE){
            Toast.makeText(this, "取消購票", Toast.LENGTH_SHORT).show(); //顯示即時訊息
        }
    }
}