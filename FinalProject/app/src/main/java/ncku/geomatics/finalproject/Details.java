package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class Details extends AppCompatActivity {

    SQLiteDatabase db = null;
    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent it = new Intent();
        int position = it.getIntExtra("position", 0);
        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        c = db.rawQuery("SELECT * FROM table3", null);
        c.moveToPosition(position);
        setTitle(c.getString(c.getColumnIndex("name")));
        ((ImageView) findViewById(R.id.imageViewTarget)).setImageResource(c.getInt(c.getColumnIndex("image")));
        ((TextView) findViewById(R.id.textViewName)).setText(c.getString(c.getColumnIndex("name")));
        ((TextView) findViewById(R.id.textViewDetails)).setText(c.getString(c.getColumnIndex("details")));
    }

    public void close(View v) {
        finish();
    }
}