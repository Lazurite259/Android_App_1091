package ncku.geomatics.p1204;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    Uri imgUrl;

    public void onClick(View v) {
        imgUrl = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, imgUrl);
        startActivityForResult(it, 100);
    }

    public void onClick2(View v) {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, 199);
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
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inJustDecodeBounds = true;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUrl), null, opt);
                opt.inSampleSize = 10;
                opt.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUrl), null, opt);

                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            } catch (Exception e) {
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bmp);
        } else if (resultCode == RESULT_OK && requestCode == 199) {
            imgUrl = data.getData();
            Bitmap bmp = null;
            try {
                bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imgUrl), null, null);
            } catch (Exception e) {
            }
            ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bmp);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}