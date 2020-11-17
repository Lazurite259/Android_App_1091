package ncku.geomatics.p7_1113;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    String[] catalog = {"餐廳", "飲料店", "甜品店"};
    String[] restaurant = {"小妞炒飯", "活力小廚", "新增店家"};
    String[] drinks = {"大苑子", "御私藏", "可不可", "新增店家"};
    String[] desserts = {"大碗公", "黑工號", "新增店家"};
    ArrayAdapter<String> adp;
    ArrayList<String> alCatalog = new ArrayList<>();
    ArrayList<String> alNewCatalog = new ArrayList<>();
    ArrayList<String> alHistory = new ArrayList<>();
    String choseCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinner(catalog, R.id.spinnerCatalog);
        findViewById(R.id.buttonHistory).setOnClickListener(this);
    }

    //設定下拉選單
    public void setSpinner(String[] str, int id) {
        ArrayAdapter<String> adp2 = new ArrayAdapter<>(
                this, R.layout.spinner_item, str);
        adp2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ((Spinner) findViewById(id)).setAdapter(adp2);
        ((Spinner) findViewById(id)).setOnItemSelectedListener(this);
    }

    //設定選單
    public void setListView(ArrayList<String> arrayList) {
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        ListView lv = findViewById(R.id.listViewStore);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
    }

    public void arraysToArrayList(ArrayList<String> arrayList, String str) {
        arrayList.clear();
        switch (str) {
            case "餐廳":
                arrayList.addAll(Arrays.asList(restaurant));
                break;
            case "飲料店":
                arrayList.addAll(Arrays.asList(drinks));
                break;
            case "甜品店":
                arrayList.addAll(Arrays.asList(desserts));
                break;
        }
    }

    public void arrayListToArrays(ArrayList<String> arrayList, String str) {
        switch (str) {
            case "餐廳":
                restaurant = arrayList.toArray(new String[0]);
                break;
            case "飲料店":
                drinks = arrayList.toArray(new String[0]);
                break;
            case "甜品店":
                desserts = arrayList.toArray(new String[0]);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent();
        String item = ((TextView) view).getText().toString();
        arraysToArrayList(alCatalog, choseCatalog);
        if (position == (alCatalog.size() - 1)) {
            it.setClass(this, MainActivity2.class);
            startActivityForResult(it, 99);
        } else {
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
            int hourOfDay=calendar.get(Calendar.HOUR_OF_DAY);
            int minute=calendar.get(Calendar.MINUTE);
            int second=calendar.get(Calendar.SECOND);
            alHistory.add(item+"  "+year+"/"+month+"/"+dayOfMonth+" "+hourOfDay+":"+minute+":"+second);
            it.setAction(Intent.ACTION_WEB_SEARCH);
            it.putExtra(SearchManager.QUERY, item);
            startActivity(it);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        arraysToArrayList(alCatalog, choseCatalog);
        alCatalog.remove(position);
        arrayListToArrays(alCatalog, choseCatalog);
        adp.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choseCatalog = ((TextView) view).getText().toString();
        arraysToArrayList(alCatalog, choseCatalog);
        setListView(alCatalog);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            String getStore = null;
            String getCatalog = null;
            if (data != null) {
                getCatalog = data.getStringExtra("catalog");
                getStore = data.getStringExtra("content");
            }
            arraysToArrayList(alNewCatalog, getCatalog);
            alNewCatalog.add(alNewCatalog.size() - 1, getStore);
            arrayListToArrays(alNewCatalog, getCatalog);
            if (getCatalog.equals(choseCatalog)) {
                //arraysToArrayList(alNewCatalog, getCatalog);
                setListView(alNewCatalog);
            }
        }
    }

    @Override
    public void onClick(View v) {
//        Intent it3 = new Intent();
//        it3.setClass(this, MainActivity3.class);
//        it3.putExtra("history", alHistory);
//        startActivity(it3);
    }
}