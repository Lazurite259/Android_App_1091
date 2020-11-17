package ncku.geomatics.p7_1113;

import androidx.appcompat.app.AppCompatActivity;

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
    String[] drinks = {"大苑子", "御私藏", "新增店家"};
    String[] desserts = {"大碗公", "黑工號", "新增店家"};
    ArrayAdapter<String> adp;
    ArrayList<String> alCatalog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpinner(catalog);
        //setListView(restaurant);
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
    public void setListView(ArrayList<String> arrayList) {
        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        ListView lv = findViewById(R.id.listViewStore);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (position ==)
//            Intent it = new Intent(Intent.ACTION_WEB_SEARCH);
//        it.putExtra()
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choseCatalog = ((TextView) view).getText().toString();
        switch (choseCatalog) {
            case "餐廳":
                alCatalog.clear();
                alCatalog.addAll(Arrays.asList(restaurant));
                break;
            case "飲料店":
                alCatalog.clear();
                alCatalog.addAll(Arrays.asList(drinks));
                break;
            case "甜品店":
                alCatalog.clear();
                alCatalog.addAll(Arrays.asList(desserts));
                break;
        }
        setListView(alCatalog);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}