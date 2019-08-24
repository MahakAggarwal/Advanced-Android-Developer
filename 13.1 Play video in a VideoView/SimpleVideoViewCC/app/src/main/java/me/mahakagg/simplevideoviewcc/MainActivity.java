package me.mahakagg.simplevideoviewcc;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String VIDEO_SAMPLE = "https://developers.google.com/training/images/tacoma_narrows.mp4";
    private VideoView mVideoView;
    private int mCurrentPosition = 0;
    private static final String PLAYBACK_TIME = "play_time";
    private TextView mBufferingTextView;
    private boolean isVideoPlaying = false;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.videoview);
        mBufferingTextView = findViewById(R.id.buffering_textview);
        imageButton = findViewById(R.id.image_button);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME);
        }
    }

    private Uri getMedia(String mediaName) {
        if (URLUtil.isValidUrl(mediaName)) {
            // media name is an external URL
            return Uri.parse(mediaName);
        } else { // media name is a raw resource embedded in the app
            return Uri.parse("android.resource://" + getPackageName() + "/raw/" + mediaName);
        }
    }

    private void initializePlayer() {
        mBufferingTextView.setVisibility(VideoView.VISIBLE);
        Uri videoUri = getMedia(VIDEO_SAMPLE);
        // set video which VideoView will play
        mVideoView.setVideoURI(videoUri);
        if (mCurrentPosition > 0) {
            mVideoView.seekTo(mCurrentPosition);
        } else {
            // Skipping to 1 shows the first frame of the video.
            mVideoView.seekTo(1);
        }
        // start video
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mBufferingTextView.setVisibility(VideoView.INVISIBLE);
                if (mCurrentPosition > 0) {
                    mVideoView.seekTo(mCurrentPosition);
                } else {
                    mVideoView.seekTo(1);
                }
                mVideoView.start();
                isVideoPlaying = true;
            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "Playback completed", Toast.LENGTH_SHORT).show();
                mVideoView.seekTo(1);
                isVideoPlaying = false;
            }
        });
    }

    // stop video playback and release resources
    private void releasePlayer() {
        mVideoView.stopPlayback();
        isVideoPlaying = false;
    }

    // activity lifecycle methods with VideoView methods
    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Android Version N onwards multi window and picture in picture is supported so no need to pause
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mVideoView.pause();
            isVideoPlaying = false;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYBACK_TIME, mVideoView.getCurrentPosition());
    }

    public void playPauseButton(View view) {
        // pause the video
        if (isVideoPlaying){
            imageButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
            mVideoView.pause();
            isVideoPlaying = false;
        }
        else{
            // play the video
            imageButton.setImageResource(R.drawable.ic_pause_black_24dp);
            mVideoView.start();
            isVideoPlaying = true;
        }
    }

    public void fastForwardButton(View view) {
        // fast forward 10 seconds
        mVideoView.seekTo(mVideoView.getCurrentPosition() + 10000);
    }
}