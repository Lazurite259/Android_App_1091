package ncku.geomatics.p8_1120;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        setTitle("歷史紀錄");

        //取得歷史紀錄
        Intent it5 = getIntent();
        ArrayList<String> getHistory = it5.getStringArrayListExtra("history");
        setListView(getHistory);
        findViewById(R.id.buttonReturn).setOnClickListener(this);

    }

    //設定選單
    public void setListView(ArrayList<String> arrayList) {
        ArrayAdapter<String> adp3 = new ArrayAdapter<>(this, android.R.layout.simple_gallery_item, arrayList);
        GridView gv = findViewById(R.id.gridViewHistory);
        gv.setAdapter(adp3);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}