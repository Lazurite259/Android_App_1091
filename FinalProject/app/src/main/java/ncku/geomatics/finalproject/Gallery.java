package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Gallery extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db = null;
    SimpleCursorAdapter sca = null;
    Cursor c = null;
    GridView gv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setTitle("圖鑑");
        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        //取得資料庫資料匯入Grid
        c = db.rawQuery("SELECT * FROM table5", null);
        sca = new SimpleCursorAdapter(this,
                R.layout.grid_item,
                c,
                new String[]{"name", "image"},
                new int[]{R.id.textView, R.id.imageView},
                0) {
            @Override
            public boolean isEnabled(int position) {
                //當mode為false時，關閉點選功能
                c.moveToPosition(position);
                if (c.getString(c.getColumnIndex("mode")).equals("false")) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //當mode為false時，透明度為50%
                if (c.getString(c.getColumnIndex("mode")).equals("false")) {
                    view.setAlpha((float) 0.5);
                } else {
                    view.setAlpha((float) 1);
                }
                return view;
            }
        };
        gv = findViewById(R.id.gridView);
        gv.setAdapter(sca);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //開啟資訊頁面
        Intent it = new Intent();
        it.setClass(this, Details.class);
        it.putExtra("position", position);
        startActivity(it);
    }

    //返回
    public void returnMain(View v) {
        finish();
    }
}