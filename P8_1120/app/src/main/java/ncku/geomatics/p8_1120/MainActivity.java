package ncku.geomatics.p8_1120;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView ivStar, ivElf, ivBomb;
    ConstraintLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivStar = findViewById(R.id.imageViewStar);
        ivElf = findViewById(R.id.imageViewElf);
        ivBomb = findViewById(R.id.imageViewBomb);
        Intent it = new Intent();
        it.setClass(this, PopupActivity.class);
        startActivityForResult(it, 99);
    }

    public void setImagePosition(ImageView imageView) {
        Random random = new Random();
        params = (ConstraintLayout.LayoutParams) imageView.getLayoutParams();
        params.verticalBias = ((float) random.nextInt(99) + 1) / 100;
        params.horizontalBias = ((float) random.nextInt(99) + 1) / 100;
        imageView.setLayoutParams(params);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == RESULT_OK) {
            String str = null;
            if (data != null) {
                str = data.getStringExtra("content");
            }
            do {
                setImagePosition(ivStar);
                Chronometer timer = findViewById(R.id.chronometer);
                if (str != null && str.equals("start")) {
                    timer.start();
                }
            } while (params.horizontalBias < 0.4 && params.horizontalBias > 0.6 && params.verticalBias < 0.4 && params.verticalBias > 0.6);
        }
    }
}