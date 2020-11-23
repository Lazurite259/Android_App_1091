package ncku.geomatics.p8_1120;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PopupActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        findViewById(R.id.buttonStart).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent it2 = new Intent();
        it2.putExtra("content", "start");
        setResult(RESULT_OK, it2);
        finish();
    }
}