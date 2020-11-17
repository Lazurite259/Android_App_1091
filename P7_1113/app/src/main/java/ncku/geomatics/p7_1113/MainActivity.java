package ncku.geomatics.p7_1113;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {

    String[] catalog = {"餐廳", "飲料店", "甜品店"};
    String[] restaurant = {"小妞炒飯", "活力小廚", "新增店家"};
    String[] drinks = {"大苑子", "御私藏", "可不可", "新增店家"};
    String[] desserts = {"大碗公", "黑工號", "新增店家"};
    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinner(catalog);
    }

    //設定下拉選單
    public void setSpinner(String[] str) {
        ArrayAdapter<String> adp2 = new ArrayAdapter<>(
                this, R.layout.spinner_item, str);
        adp2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinnerCatalog)).setAdapter(adp2);
        ((Spinner) findViewById(R.id.spinnerCatalog)).setOnItemSelectedListener(this);
    }

    //設定選單
    public void setListView(String[] str) {
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, str);
        ListView lv = findViewById(R.id.listViewStore);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
    }

    public String[] array(String str){
        String[] s={};
        switch (str) {
            case "餐廳":
                s= restaurant;
                break;
            case "飲料店":
                s= drinks;
                break;
            case "甜品店":
                s= desserts;
                break;
        }
        return s;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent();
        String item = ((TextView) view).getText().toString();
        if (position == array(choseCatalog).length - 1) {
            it.setClass(this, MainActivity2.class);
            startActivityForResult(it, 99);
        }
        else if(position != array(choseCatalog).length - 1){
            it.setAction(Intent.ACTION_WEB_SEARCH);
            it.putExtra(SearchManager.QUERY, item);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        array(choseCatalog)[position].isEmpty();
        adp.notifyDataSetChanged();
        return true;
    }

    String choseCatalog;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        choseCatalog = ((TextView) view).getText().toString();
        setListView(array(choseCatalog));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            String getStore = null;
            String getCatalog=null;
            if (data != null) {
                getCatalog=data.getStringExtra("catalog");
                getStore = data.getStringExtra("content");
            }
            array(getCatalog)[array(getCatalog).length-1]=getStore;
            adp.notifyDataSetChanged();
        }
    }
}