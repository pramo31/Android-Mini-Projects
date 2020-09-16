package edu.uic.cs478.pramodh.musicclient;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

import edu.uic.cs478.pramodh.musicapi.Song;

public class MediaPlayerService extends Service {

    private final static MediaPlayer mPlayer = new MediaPlayer();
    private int mStartID;

    static Song selectedSong;
    static String playingSongUrl;
    static String selectedSongUrl;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer.setLooping(false);
        mPlayer.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf(mStartID);
            }
        });

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mStartID = startId;
        String action = intent.getStringExtra(MusicClientActivity.mediaAction);
        if (action.equals(MusicClientActivity.actionPlay)) {


            if (playingSongUrl != null && playingSongUrl.equals(selectedSongUrl)) {
                mPlayer.seekTo(mPlayer.getCurrentPosition());
                mPlayer.start();
            } else {
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                mPlayer.reset();
                try {
                    mPlayer.setDataSource(selectedSongUrl);
                    mPlayer.prepare();
                    mPlayer.start();
                    playingSongUrl = selectedSongUrl;
                } catch (IllegalArgumentException | IllegalStateException | IOException e) {
                    Toast.makeText(getApplicationContext(), "Unable to play song at this moment. Please try again later", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        } else {
            mPlayer.pause();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mPlayer.stop();
        mPlayer.release();

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
