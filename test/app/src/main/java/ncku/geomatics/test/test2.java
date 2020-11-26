package ncku.geomatics.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import java.time.Instant;

public class test2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);


    }

    public void onClick(View v) {
        int image = 0;
        if (v.getId() == R.id.buttonB) {
            image = R.drawable.bear;
        } else if (v.getId() == R.id.buttonE) {
            image = R.drawable.elf;
        } else if (v.getId() == R.id.buttonF) {
            image = R.drawable.fish;
        }
        Intent it2 = new Intent();
        it2.putExtra("id", image);
        setResult(RESULT_OK, it2);
        finish();
    }
}