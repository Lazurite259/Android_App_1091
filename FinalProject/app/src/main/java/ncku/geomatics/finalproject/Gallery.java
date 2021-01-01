package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
        c = db.rawQuery("SELECT * FROM test2", null);
        sca = new SimpleCursorAdapter(this,
                R.layout.grid_item,
                c,
                new String[]{"name", "image"},
                new int[]{R.id.textView, R.id.imageView},
                0){
            @Override
            public boolean isEnabled(int position) {
                c.moveToPosition(position);
                if (c.getString(c.getColumnIndex("mode")).equals("false")) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        gv = findViewById(R.id.gridView);
        gv.setAdapter(sca);
//        for (int i = 0; i < c.getCount(); i++) {
//            c.moveToPosition(i);
//            if (c.getString(c.getColumnIndex("mode")).equals("false")) {
//                Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
//                gv.getChildAt(0).setEnabled(false);
//            } else {
//                Toast.makeText(this, Integer.toString(i), Toast.LENGTH_SHORT).show();
//                gv.getChildAt(1).setEnabled(true);
//            }
//        }
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, Integer.toString(position), Toast.LENGTH_SHORT).show();
    }
}