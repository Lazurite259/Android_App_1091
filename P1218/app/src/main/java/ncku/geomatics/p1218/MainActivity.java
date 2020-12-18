package ncku.geomatics.p1218;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
        implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    Uri songUri = null;
    Uri videoUri = null;
    MediaPlayer mediaPlayer = null;
    VideoView vdv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.song);
        ((TextView) findViewById(R.id.textView)).setText(songUri.toString());
        videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);

        try {
            isReady = false;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, songUri);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
        }

        vdv = findViewById(R.id.videoView);
        vdv.setVideoURI(videoUri);
        MediaController mediaController = new MediaController(this);
        vdv.setMediaController(mediaController);
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

    int pos = 0;
    Button btnStart;

    public void start(View v) {
        btnStart = findViewById(R.id.buttonStart);
        if (isReady && btnStart.getText().toString().equals("播放")) {
            mediaPlayer.start();
            btnStart.setText("暫停");
        } else if (btnStart.getText().toString().equals("暫停")) {
            mediaPlayer.pause();
            pos = mediaPlayer.getCurrentPosition();
            btnStart.setText("繼續");
        } else if (btnStart.getText().toString().equals("繼續")) {
            mediaPlayer.seekTo(pos);
            mediaPlayer.start();
            btnStart.setText("暫停");
        }
    }

    public void stop(View v) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            btnStart.setText("播放");
        }
    }

    public void backward(View v) {
        pos = mediaPlayer.getCurrentPosition();
        pos -= 10000;
        if (pos < 0) {
            pos = 0;
        }
        mediaPlayer.seekTo(pos);
    }

    public void frontward(View v) {
        if(mediaPlayer.isPlaying()){
            pos = mediaPlayer.getCurrentPosition();
            pos += 10000;
            if (pos > mediaPlayer.getDuration()) {
                pos = mediaPlayer.getDuration();
            }
            mediaPlayer.seekTo(pos);
        }
        if(vdv.isPlaying()){
            pos = vdv.getCurrentPosition();
            pos += 10000;
            if (pos > vdv.getDuration()) {
                pos = vdv.getDuration();
            }
            vdv.seekTo(pos);
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
                vdv.setVideoURI(uri);
                MediaController mediaController = new MediaController(this);
                vdv.setMediaController(mediaController);
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

    boolean isReady = false;

    @Override
    public void onPrepared(MediaPlayer mp) {
//        mediaPlayer.start();
        isReady = true;
    }
}