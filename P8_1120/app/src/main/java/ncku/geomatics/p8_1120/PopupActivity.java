package ncku.geomatics.p8_1120;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class PopupActivity extends AppCompatActivity
        implements View.OnClickListener {

    ArrayList<String> getHistory = new ArrayList<>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonHistory).setOnClickListener(this);
        //取得歷史紀錄
        Intent it3 = getIntent();
        getHistory = it3.getStringArrayListExtra("history");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonStart) {
            name = ((EditText) findViewById(R.id.editTextName)).getText().toString();
            if (!name.equals("")) {
                //開始遊戲
                Intent it2 = new Intent();
                it2.putExtra("name", name);
                setResult(RESULT_OK, it2);
                finish();
            }
        } else if (v.getId() == R.id.buttonHistory) {
            //開啟歷史紀錄
            Intent it4 = new Intent();
            it4.setClass(this, HistoryActivity.class);
            it4.putExtra("history", getHistory);
            startActivity(it4);
        }
    }
}