package ncku.geomatics.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("殲滅地精大作戰");

        //建資料庫
        db = openOrCreateDatabase("DB", Context.MODE_PRIVATE, null);
        String s = "CREATE TABLE IF NOT EXISTS table5" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(32)," +
                "image VARCHAR(32)," +
                "details VARCHAR(500)," +
                "latitude VARCHAR(32)," +
                "longitude VARCHAR(32)," +
                "mode VARCHAR(16))";
        db.execSQL(s);

        //當資料庫無資料時，建一個table
        Cursor c = db.rawQuery("SELECT * FROM table5", null);
        if (c.getCount() == 0) {
            addData("國立成功大學附設臺南市私立員工子女幼兒園", R.drawable.kindergarten,
                    "可以在這裡撿到凱偉老師的小孩，還有你不想努力的機會。", 22.9993618, 120.218629, "false");
            addData("成功大學圖書館", R.drawable.library,
                    "明明晚上九點就閉館了，但還是會有人徘徊的地方。", 23.0000038, 120.219858, "false");
            addData("成大理學教學大樓", R.drawable.ncku_eds,
                    "當化學系跟物理系有了這一棟，就不難理解為什麼他們尾牙都可以辦一整條街。", 23.0002745, 120.218875, "false");
            addData("成功大學計算機與網路中心", R.drawable.computer,
                    "每個大一新生的早八惡夢來源。", 22.9981421, 120.218543, "false");
            addData("國立成功大學博物館", R.drawable.museum,
                    "一個踏溯台南去過後，就不會再踏進去的地方。", 22.9967249, 120.2196, "false");
            addData("國立成功大學測量與空間資訊學系", R.drawable.geomatics,
                    "全成大校園的中心，據傳也是全宇宙中心的所在。", 22.998669, 120.219832, "false");
            addData("國立成功大學材料科學及工程學系新館", R.drawable.mse,
                    "不知道什麼時候土木也可以來找我們蓋系館。", 22.9984536, 120.221855, "false");
            addData("格致堂", R.drawable.ncku_g,
                    "舊時代的考場，不知道那時候有沒有人平差是在這裡被當掉的。", 22.9970924, 120.219597, "false");
            addData("國立成功大學工程科學系", R.drawable.es,
                    "離郵局ATM兩分鐘，麥當勞跟小七只要三分鐘的地方，當初選系館位置的大哥一定是天才。", 22.9965916, 120.221601, "false");
            addData("國立成功大學環境工程學系", R.drawable.eve,
                    "一句話惹怒環工系：你長大是不是會去挖石油？", 23.0000087, 120.220807, "false");
        }
    }

    //開啟圖鑑
    public void openGallery(View v) {
        Intent it = new Intent();
        it.setClass(this, Gallery.class);
        startActivity(it);
    }

    //開啟遊戲畫面
    public void startGame(View v) {
        Intent it2 = new Intent();
        it2.setClass(this, Map.class);
        startActivity(it2);
    }

    //新增資料
    void addData(String name, int image, String details, double latitude, double longitude, String mode) {
        ContentValues cv = new ContentValues(3);
        cv.put("name", name);
        cv.put("image", image);
        cv.put("details", details);
        cv.put("latitude", latitude);
        cv.put("longitude", longitude);
        cv.put("mode", mode);
        db.insert("table5", null, cv);
    }
}