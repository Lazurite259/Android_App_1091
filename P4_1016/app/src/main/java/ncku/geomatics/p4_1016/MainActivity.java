package ncku.geomatics.p4_1016;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements RadioGroup.OnCheckedChangeListener,
        CompoundButton.OnCheckedChangeListener {

    RadioGroup main, drink, sugar, ice;
    CheckBox fries, sundae;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main=findViewById(R.id.Main);
        main.setOnCheckedChangeListener(this);
        drink=findViewById(R.id.Drink);
        drink.setOnCheckedChangeListener(this);
        sugar=findViewById(R.id.Suger);
        sugar.setOnCheckedChangeListener(this);
        ice=findViewById(R.id.Ice);
        ice.setOnCheckedChangeListener(this);
        fries=findViewById(R.id.checkBoxFries);
        fries.setOnCheckedChangeListener(this);
        sundae=findViewById(R.id.checkBoxSundae);
        sundae.setOnCheckedChangeListener(this);
    }

    void order(){
        String str="";
        int sum=0;
        ImageView ivb=findViewById(R.id.imageViewBurger);
        ImageView ivg=findViewById(R.id.imageViewChicken);
        ImageView ivt=findViewById(R.id.imageViewTea);
        ImageView ivc=findViewById(R.id.imageViewCoffee);
        ImageView ivf=findViewById(R.id.imageViewFries);
        ImageView ivs=findViewById(R.id.imageViewSundae);
        TextView tv=findViewById(R.id.textView);
        if(main.getCheckedRadioButtonId()==R.id.radioButtonBurger){
            //選擇漢堡
            str+="餐點為漢堡";
            sum+=40;
            ivb.setVisibility(View.VISIBLE);
            ivg.setVisibility(View.GONE);
        }
        else{
            //選擇炸雞
            str+="餐點為炸雞";
            sum+=50;
            ivb.setVisibility(View.GONE);
            ivg.setVisibility(View.VISIBLE);
        }
        if(drink.getCheckedRadioButtonId()==R.id.radioButtonTea){
            //選擇紅茶
            str+="搭配紅茶";
            sum+=20;
            ivt.setVisibility(View.VISIBLE);
            ivc.setVisibility(View.GONE);
        }
        else{
            //選擇咖啡
            str+="搭配咖啡";
            sum+=30;
            ivt.setVisibility(View.GONE);
            ivc.setVisibility(View.VISIBLE);
        }
        if(sugar.getCheckedRadioButtonId()==R.id.radioButtonHalfSuger){
            //選擇半糖
            str+="(半糖";
        }
        else{
            //選擇無糖
            str+="(無糖";
        }
        if(ice.getCheckedRadioButtonId()==R.id.radioButtonFewIce){
            //選擇少冰
            str+="、少冰)，";
        }
        else{
            //選擇去冰
            str+="、去冰)，";
        }
        if(fries.isChecked() && !sundae.isChecked()){
            str+="加點薯條，";
            sum+=20;
            ivf.setVisibility(View.VISIBLE);
        }
        else{
            ivf.setVisibility(View.GONE);
        }
        if(sundae.isChecked() && !fries.isChecked()){
            str+="加點聖代，";
            sum+=30;
            ivs.setVisibility(View.VISIBLE);
        }
        else{
            ivs.setVisibility(View.GONE);
        }
        if(fries.isChecked() && sundae.isChecked()){
            str+="加點薯條和聖代，";
            sum+=50;
            ivf.setVisibility(View.VISIBLE);
            ivs.setVisibility(View.VISIBLE);
        }
        tv.setText(str+"共計"+sum+"元");
    }

    public void onClick(View v){
        order();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        order();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        order();
    }
}