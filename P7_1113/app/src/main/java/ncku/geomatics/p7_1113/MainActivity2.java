package ncku.geomatics.p7_1113;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity
        implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    String[] catalog = {"餐廳", "飲料店", "甜品店"};
    String textCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("新增店家");

        findViewById(R.id.buttonEnter).setOnClickListener(this);
        findViewById(R.id.buttonCancel).setOnClickListener(this);
        setSpinner(catalog, R.id.spinnerChoseCatelog);
    }

    //設定下拉選單
    public void setSpinner(String[] str, int id) {
        ArrayAdapter<String> adp2 = new ArrayAdapter<>(
                this, R.layout.spinner_item, str);
        adp2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        ((Spinner) findViewById(id)).setAdapter(adp2);
        ((Spinner) findViewById(id)).setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it2 = new Intent();
        if (v.getId() == R.id.buttonEnter) {
            String textStore = ((EditText) findViewById(R.id.editTextStore)).getText().toString();
            it2.putExtra("content", textStore);
            it2.putExtra("catalog", textCatalog);
            setResult(RESULT_OK, it2);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textCatalog = ((TextView) view).getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}