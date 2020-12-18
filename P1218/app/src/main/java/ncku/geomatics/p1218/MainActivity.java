package ncku.geomatics.p1218;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    Uri songUri = null;
    MediaPlayer mediaPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);
        ((TextView) findViewById(R.id.textView)).setText(songUri.toString());

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, songUri);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
        }

    }

    public void onClick(View v) {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT);
        if (v.getId() == R.id.button) {
            it.setType("audio/*");
            startActivityForResult(it, 456);
        } else if (v.getId() == R.id.button2) {
            it.setType("video/*");
            startActivityForResult(it, 789);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ((TextView) findViewById(R.id.textView)).setText(uri.toString());
            if (requestCode == 456) {

            } else if (requestCode == 789) {

            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
    }
}