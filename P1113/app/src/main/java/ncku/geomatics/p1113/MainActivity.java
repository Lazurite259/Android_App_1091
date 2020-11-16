package ncku.geomatics.p1113;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    String[] memo = {"1. xxx", "2. xxx", "3. ", "4. ", "5. ", "6. "};
    ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memo);
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
    }

    public void onClick(View v) {
        //開啟第二畫面
        Intent it = new Intent();
        it.setClass(this, MainActivity2.class);
        startActivity(it);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent();
        it.setClass(this, MainActivity2.class);
        it.putExtra("id", position + 1);
        String s = "";
        if (memo[position].length() > 3) {
            s = memo[position].substring(3);
        }
        it.putExtra("content", s);
//        startActivity(it);
        startActivityForResult(it, 999);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        memo[position] = (position + 1) + ". ";
//        adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, memo);
//        ListView lv = findViewById(R.id.listView);
//        lv.setAdapter(adp);
        adp.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999 && resultCode == RESULT_OK) {
            int gID = data.getIntExtra("id", 0);
            String gStr = data.getStringExtra("content");
            memo[gID - 1] = gID + ". " + gStr;
            adp.notifyDataSetChanged();
        }
    }

    public void call(View v){
        Intent it3=new Intent();
        it3.setAction(Intent.ACTION_VIEW);
//        it3.setData(Uri.parse("tel:800"));
        it3.setData(Uri.parse("geo:25.047095, 121.517308"));
        startActivity(it3);
    }
}