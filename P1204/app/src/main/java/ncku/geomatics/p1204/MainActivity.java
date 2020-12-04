package ncku.geomatics.p1204;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    Uri imgUri;

    public void onClick(View v) {
        imgUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(it, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == 100) {
            //不存在外部儲存空間，照片用Intent回傳
//            Bundle bundle = data.getExtras();
//            Bitmap bmp = (Bitmap) bundle.get("data");
            //存到外部儲存空間，Intent不會有照片回傳，要用Content Resolver解碼
            Bitmap bmp = null;
            try {
                bmp= BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUri),null,null);
            }
            catch (Exception e){

            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bmp);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}