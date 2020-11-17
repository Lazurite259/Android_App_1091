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

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener, View.OnClickListener {

    String[] catalog = {"餐廳", "飲料店", "甜品店"};
    String[] restaurant = {"小妞炒飯", "活力小廚", "小赤佬", "轉角", "麥當勞", "新增店家"};
    String[] drinks = {"大苑子", "御私藏", "可不可熟成紅茶", "50嵐", "茶湯會", "迷客夏", "COCO", "橘子水漾", "波哥茶飲", "新增店家"};
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

    //陣列存入動態陣列
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

    //動態陣列存入陣列
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
        if (position == (alCatalog.size() - 1)) { //選取選單中最後一項(新增店家)
            //開啟第二個視窗(新增店家)
            it.setClass(this, MainActivity2.class);
            it.putExtra("catalog", choseCatalog);
            startActivityForResult(it, 99);
        } else {
            //取得現在時間
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            String dateTime = dateFormat.format(calendar.getTime());
            alHistory.add(dateTime + "    " + item);
            //開啟網頁查詢
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
            Snackbar.make(findViewById(R.id.root), "新增成功", Snackbar.LENGTH_SHORT).show();
            //新增店家存入原選單
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
                setListView(alNewCatalog);
            }
        }
    }

    @Override
    public void onClick(View v) {
        //開啟第三個視窗(查詢紀錄)
        Intent it3 = new Intent();
        it3.setClass(this, MainActivity3.class);
        it3.putExtra("history", alHistory);
        startActivity(it3);
    }
}