package ncku.geomatics.p1023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener {

    TextView tv;
    Spinner sp;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv=findViewById(R.id.textView);
        sp=findViewById(R.id.spinner);
        sp.setOnItemSelectedListener(this);
        //((Spinner)findViewById(R.id.spinner)).setOnItemSelectedListener(this);//與上兩行意思相同
        lv=findViewById(R.id.listView);
        lv.setOnItemClickListener(this);

    }

    public void order(View v){
        //1
        String str=sp.getSelectedItem().toString();
        tv.setText("訂"+str+"的票");
        //2
        int idx=sp.getSelectedItemPosition();
        String[] arr=new String[5];
        arr[0]="台北影城";
        arr[1]="板橋影城";
        arr[2]="台中影城";
        arr[3]="台南影城";
        arr[4]="高雄影城";
        tv.setText("訂"+arr[idx]+"的票");
        //3
        String[] arr2={"台北影城", "板橋影城", "台中影城", "台南影城", "高雄影城"};
        tv.setText("訂"+arr2[idx]+"的票");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tv.setText(position+" 訂"+((TextView)view).getText()+"的票");

        //動態配置的spinner
        //Step1,p44
        String[] tempSet1={"可樂"};
        String[] tempSet2={"可樂", "礦泉水"};
        String[] tempSet3={"可樂", "礦泉水", "啤酒"};
        ArrayList<String> al4=new ArrayList<>();
        al4.add("可樂");
        al4.add("礦泉水");
        al4.add("啤酒");
        al4.add("阿B");


        if(position==0){
            //Step2,p34-35=============Spinner===========
            ArrayAdapter<String> adp=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet1
            );
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Step3
            ((Spinner)findViewById(R.id.spinner2)).setAdapter(adp);

            //============ListView==========
            ArrayAdapter<String> adp2=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet1
            );
            ((ListView)findViewById(R.id.listView)).setAdapter(adp2);
        }
        else if(position==1){
            //Step2,p34-35
            ArrayAdapter<String> adp=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet2
            );
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Step3
            ((Spinner)findViewById(R.id.spinner2)).setAdapter(adp);

            //============ListView==========
            ArrayAdapter<String> adp2=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet2
            );
            ((ListView)findViewById(R.id.listView)).setAdapter(adp2);
        }
        else if(position==2){
            //Step2,p34-35
            ArrayAdapter<String> adp=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet3
            );
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Step3
            ((Spinner)findViewById(R.id.spinner2)).setAdapter(adp);

            //============ListView==========
            ArrayAdapter<String> adp2=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, tempSet3
            );
            ((ListView)findViewById(R.id.listView)).setAdapter(adp2);
        }
        else if(position==3){
            //Step2,p34-35
            ArrayAdapter<String> adp=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, al4
            );
            adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Step3
            ((Spinner)findViewById(R.id.spinner2)).setAdapter(adp);

            //============ListView==========
            ArrayAdapter<String> adp2=new ArrayAdapter<>(
                    this, android.R.layout.simple_spinner_item, al4
            );
            ((ListView)findViewById(R.id.listView)).setAdapter(adp2);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    ArrayList<String> al=new ArrayList<>();
    String[] foodName={"漢堡", "薯條", "可樂"};
    boolean[] selected={false, false, false};
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*if(selected[position]==false){
            selected[position]=true;
        }
        else {
            selected[position]=false;
        }*///簡化為下列
        selected[position]=!selected[position];
        String food="";

        /*for(int i=0; i<selected.length; i++){
            if(selected[i]==true){
                food += foodName[i]+"";
            }
        }*/

        String clickedFood=((TextView)view).getText().toString();
        if(al.contains(clickedFood)) {
            al.remove(clickedFood);
        }
        else {
            al.add(clickedFood);
        }
        for(int i=0; i<al.size(); i++){
                food += al.get(i)+"";
        }
        /*for(String s: al){
            food += s+"";
        }*/
        tv.setText("你點了:"+food);
    }
}