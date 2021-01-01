package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Gallery extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db = null;
    SimpleCursorAdapter sca = null;
    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        setTitle("圖鑑");
        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        c = db.rawQuery("SELECT * FROM test1", null);
        sca = new SimpleCursorAdapter(this,
                R.layout.grid_item,
                c,
                new String[]{"name", "image"},
                new int[]{R.id.textView, R.id.imageView},
                0);

        GridView gv = findViewById(R.id.gridView);
        gv.setAdapter(sca);
//        gv.getChildAt()
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}