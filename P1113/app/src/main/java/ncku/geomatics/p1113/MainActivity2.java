package ncku.geomatics.p1113;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.ETC1;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import javax.xml.transform.Result;

public class MainActivity2 extends AppCompatActivity {

    TextView tvID;
    EditText etC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent it = getIntent();
        int gID = it.getIntExtra("id", 0);
        String gStr = it.getStringExtra("content");
        tvID = findViewById(R.id.textViewID);
        tvID.setText(gID + "");
        etC = findViewById(R.id.editTextContent);
        etC.setText(gStr);
    }

    public void cancel(View v) {
        finish(); //關掉此畫面
    }

    public void save(View v) {
        Intent it2 = new Intent();
        int vID = Integer.parseInt(tvID.getText().toString());
        String vStr = etC.getText().toString();
        it2.putExtra("id", vID);
        it2.putExtra("content", vStr);
        setResult(RESULT_OK, it2);
        finish();
    }
}