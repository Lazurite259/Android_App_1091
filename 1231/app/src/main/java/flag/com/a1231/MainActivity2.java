package flag.com.a1231;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void confirm(View v) {
        String p="99";
        Intent it = new Intent();
        it.setClass(this, MainActivity.class);
        it.putExtra("開啟遊戲",99);
        setResult(RESULT_OK, it);
        finish();
    }
}