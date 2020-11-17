package ncku.geomatics.p7_1113;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity3 extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        setTitle("查詢紀錄");

        //取得查詢紀錄
        Intent it4 = getIntent();
        ArrayList<String> getHistory = it4.getStringArrayListExtra("history");
        setListView(getHistory);
        findViewById(R.id.buttonReturn).setOnClickListener(this);
    }

    //設定選單
    public void setListView(ArrayList<String> arrayList) {
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        ListView lv = findViewById(R.id.listViewHistory);
        lv.setAdapter(adp3);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}