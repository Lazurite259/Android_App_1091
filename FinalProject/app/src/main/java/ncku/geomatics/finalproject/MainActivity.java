package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        String s = "CREATE TABLE IF NOT EXISTS test2" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(32)," +
                "image VARCHAR(32)," +
                "details VARCHAR(500)," +
                "mode VARCHAR(16))";
        db.execSQL(s);

        Cursor c=db.rawQuery("SELECT * FROM test2", null);
        if(c.getCount()==0){
            addData("bear", R.drawable.bear, "This is a bear.", "false");
            addData("elf", R.drawable.elf, "This is an elf.", "true");
            addData("star", R.drawable.star, "This is a star.", "false");
        }
    }

    public void onClick(View v) {
        Intent it = new Intent();
        it.setClass(this, Gallery.class);
        startActivity(it);
    }

    void addData(String name, int image, String details, String mode) {
        ContentValues cv = new ContentValues(3);
        cv.put("name", name);
        cv.put("image", image);
        cv.put("details", details);
        cv.put("mode", mode);
        db.insert("test2", null, cv);
    }
}