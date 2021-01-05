package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Map extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    public void confirm(View v) {
        String p="99";
        Intent it = new Intent();
        it.setClass(this, Game.class);
        it.putExtra("開啟遊戲",99);
        setResult(RESULT_OK, it);
        finish();
    }
}