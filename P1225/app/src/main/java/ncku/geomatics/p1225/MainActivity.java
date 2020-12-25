package ncku.geomatics.p1225;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    SQLiteDatabase db = null;
    SimpleCursorAdapter sca = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        String s = "CREATE TABLE IF NOT EXISTS table2" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(32)," +
                "phone VARCHAR(16)," +
                "email VARCHAR(32))";
        db.execSQL(s);

        addData("孫小小","(02)23963257","small@flag.com.tw");
        Cursor c = db.rawQuery("SELECT * FROM table2", null);
        sca = new SimpleCursorAdapter(this,
                R.layout.item,
                c,
                new String[]{"name", "phone", "email"},
                new int[]{R.id.textViewName, R.id.textViewPhone, R.id.textViewEmail},
                0);
        ListView lv=findViewById(R.id.listView);
        lv.setAdapter(sca);
        lv.setOnItemClickListener(this);

//        TextView tv = findViewById(R.id.textView);
//        tv.setText("Path: " + db.getPath() + "\n" +
//                "Page Size: " + db.getPageSize() + "\n" +
//                "Max Size: " + db.getMaximumSize() + "\n");
//        Cursor c = db.rawQuery("SELECT * FROM table1 WHERE name LIKE '%大%'", null);
//        tv.append("data: " + c.getCount() + "\n");
//        if (c.moveToFirst()) { //c.getCount>0
//            do {
//                String name = c.getString(0);
//                String phone = c.getString(1);
//                String email = c.getString(c.getColumnIndex("email"));
//                tv.append(name + " " + phone + " " + email + "\n");
//            } while (c.moveToNext());
//        }
    }

    void addData(String name, String phone, String email) {
        ContentValues cv = new ContentValues(3);
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("email", email);
        db.insert("table2", null, cv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}