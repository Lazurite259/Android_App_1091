package ncku.geomatics.p7_1113;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("新增店家");

        ((Button)findViewById(R.id.buttonEnter)).setOnClickListener(this);
        ((Button)findViewById(R.id.buttonCancel)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.buttonEnter){

        }
        else {
            finish();
        }
    }
}