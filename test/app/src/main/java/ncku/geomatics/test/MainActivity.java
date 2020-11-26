package ncku.geomatics.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton(View v) {
        Intent it = new Intent();
        it.setClass(this, test2.class);
        startActivityForResult(it, 99);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            int getId = 0;
            if (data != null) {
                getId = data.getIntExtra("id", 0);
            }
            ImageView iv=findViewById(R.id.imageView);
            iv.setImageResource(getId);
        }

    }
}