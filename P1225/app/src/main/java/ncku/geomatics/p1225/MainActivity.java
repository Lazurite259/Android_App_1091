package ncku.geomatics.p1225;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        String s = "CREATE TABLE IF NOT EXISTS table1" +
                "(name VARCHAR(32)," +
                "phone VARCHAR(16)," +
                "email VARCHAR(32))";
        db.execSQL(s);

//        ContentValues cv = new ContentValues(3);
//        cv.put("name", "孫大小");
//        cv.put("phone", "(02)23963257");
//        cv.put("email", "small@flag.com.tw");
//        db.insert("table1", null, cv);

        TextView tv = findViewById(R.id.textView);
        tv.setText("Path: " + db.getPath() + "\n" +
                "Page Size: " + db.getPageSize() + "\n" +
                "Max Size: " + db.getMaximumSize() + "\n");
        Cursor c = db.rawQuery("SELECT * FROM table1 WHERE name LIKE '%大%'", null);
        tv.append("data: " + c.getCount() + "\n");
        if (c.moveToFirst()) { //c.getCount>0
            do {
                String name = c.getString(0);
                String phone = c.getString(1);
                String email = c.getString(c.getColumnIndex("email"));
                tv.append(name + " " + phone + " " + email + "\n");
            } while (c.moveToNext());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}