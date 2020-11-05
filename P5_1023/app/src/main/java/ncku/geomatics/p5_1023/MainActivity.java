package ncku.geomatics.p5_1023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    String[] station={"台北站", "新竹站", "台中站", "台南站", "高雄站"};
    String[] ticketCategory={"全票", "敬老票", "愛心票", "兒童票"};
    ArrayList<String> alStart=new ArrayList<>();
    ArrayList<String> alEnd=new ArrayList<>();
    ArrayList<String> alChoice=new ArrayList<>();
    RadioGroup rdDirection;
    TextView tv;
    String direction="北上", startStation, endStation, ticketChoice;
    int startIndex, endIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textViewTotal);
        //設定畫面一開始的起站下拉選單
        alStart.addAll(Arrays.asList(station));
        alStart.remove(station[0]);
        setSpinner(alStart, R.id.spinnerStart);
        //設定畫面一開始的票種下拉選單
        alChoice.addAll(Arrays.asList(ticketCategory));
        setSpinner(alChoice, R.id.spinnerTicketCategory);

        rdDirection=findViewById(R.id.radioGroupDirection);
        rdDirection.setOnCheckedChangeListener(this);
        ((Spinner)findViewById(R.id.spinnerStart)).setOnItemSelectedListener(this);
        ((ListView)findViewById(R.id.listViewEnd)).setOnItemClickListener(this);
        ((Spinner)findViewById(R.id.spinnerTicketCategory)).setOnItemSelectedListener(this);
    }

    //設定下拉選單
    public void setSpinner(ArrayList<String> arrayList, int id){
        ArrayAdapter<String> adp=new ArrayAdapter<>(
                this, R.layout.spinner_item, arrayList
        );
        adp.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ((Spinner)findViewById(id)).setAdapter(adp);
    }

    //設定迄站的選單
    public void setEndListView(){
        ArrayAdapter<String> adp2=new ArrayAdapter<>(
                this, R.layout.spinner_item, alEnd
        );
        ((ListView)findViewById(R.id.listViewEnd)).setAdapter(adp2);
    }

    public void setTextView(){
        int[] ticketPrice={0, 290, 700, 1350, 1490}; //從台北到新竹、台中、台南、高雄的票價
        int finalPrice=Math.abs(ticketPrice[startIndex]-ticketPrice[endIndex]);
        if(ticketChoice!="全票"){
            finalPrice/=2;
        }
        tv.setText(direction+startStation+"至"+endStation+"\n"+ticketChoice+"票價為"+finalPrice+"元");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //二選一(北上則無台北，南下則無高雄)
        if(rdDirection.getCheckedRadioButtonId()==R.id.radioButtonNorth){
            direction=direction.replace("南下", "北上");
            alStart.add(station.length-1,station[station.length-1]);
            alStart.remove(station[0]);
        }
        else {
            direction=direction.replace("北上", "南下");
            alStart.add(0,station[0]);
            alStart.remove(station[station.length-1]);
        }
        setSpinner(alStart, R.id.spinnerStart);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        endStation=((TextView)view).getText().toString();
        endIndex=Arrays.asList(station).indexOf(endStation);
        setTextView();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId()==R.id.spinnerStart) {
            startStation = ((TextView) view).getText().toString(); //取得起站下拉選單的選項
            startIndex = Arrays.asList(station).indexOf(startStation); //取得選項在陣列中的位置
            alEnd.clear();
            if (rdDirection.getCheckedRadioButtonId() == R.id.radioButtonNorth) {
                //取得選項以北的車站
                for (int i = 0; i < startIndex; i++) {
                    alEnd.add(station[i]);
                }
            } else {
                //取得選項以南的車站
                for (int i = startIndex + 1; i < station.length; i++) {
                    alEnd.add(station[i]);
                }
            }
            setEndListView();
        }
        else if(parent.getId()==R.id.spinnerTicketCategory) {
            ticketChoice=((TextView) view).getText().toString(); //取得票種下拉選單的選項
            if(tv.getText()!=""){
                setTextView();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}