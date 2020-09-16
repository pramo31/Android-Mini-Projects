package edu.uic.cs478.pramodh.musiccentral;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import edu.uic.cs478.pramodh.musicapi.MusicAPI;
import edu.uic.cs478.pramodh.musicapi.Song;

public class MusicAPIImpl extends Service {
    protected static final String TAG = MusicAPIImpl.class.getName();
    private static List<Song> mSongsList;

    @Override
    public void onCreate() {

        String[] mSongsTitle = getResources().getStringArray(R.array.songs_title);
        String[] mSongsArtist = getResources().getStringArray(R.array.songs_artist);
        String[] mSongsUrl = getResources().getStringArray(R.array.songs_url);

        mSongsList = new ArrayList<>();
        String bitmapPrefix = "song_bitmap_";
        for (int i = 0; i < mSongsTitle.length; i++) {
            mSongsList.add(new Song(BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(bitmapPrefix + (i + 1), "drawable", getPackageName())), mSongsTitle[i], mSongsArtist[i], mSongsUrl[i]));
        }
    }

    private final MusicAPI.Stub mBinder = new MusicAPI.Stub() {

        @Override
        public List<Song> getAllSongs() {
            synchronized (mBinder) {
                return mSongsList;
            }
        }

        @Override
        public Song getSong(int index) {
            synchronized (mBinder) {
                return mSongsList.get(index);
            }
        }

        @Override
        public String getSongUrl(int index) {
            synchronized (mBinder) {
                return mSongsList.get(index).getUrl();
            }
        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "A client is bound to the service!");
        return mBinder;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Destroying the service");
    }
}
