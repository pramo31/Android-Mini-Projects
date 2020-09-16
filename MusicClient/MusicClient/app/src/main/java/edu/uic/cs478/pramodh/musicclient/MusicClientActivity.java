package edu.uic.cs478.pramodh.musicclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.List;

import edu.uic.cs478.pramodh.musicapi.MusicAPI;
import edu.uic.cs478.pramodh.musicapi.Song;

public class MusicClientActivity extends AppCompatActivity {
    protected static final String TAG = MusicClientActivity.class.getName();
    private boolean mIsBound = false;

    private MusicAPI mMusicApiService;

    ListView lstView;
    MediaPlayer player;

    ToggleButton retrieveButton;
    RelativeLayout selectedSongRow;
    Button serviceButton;
    Button statusButton;
    TextView statusText;
    ImageButton mediaButton;

    //Song selectedSong;
    //String playingSongUrl;
    String selectedSongUrl;

    final static String actionPlay = "PLAY";
    final static String actionPause = "PAUSE";
    final static String mediaAction = "PLAYER_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveButton = findViewById(R.id.get_songs_button);
        selectedSongRow = findViewById(R.id.song_row_selected);
        serviceButton = findViewById(R.id.service_button);
        statusButton = findViewById(R.id.status_button);
        statusText = findViewById(R.id.status_text);
        mediaButton = findViewById(R.id.media_button_main);


        final Intent playIntent = new Intent(getApplicationContext(), MediaPlayerService.class);
        playIntent.putExtra(mediaAction, actionPlay);

        final Intent pauseIntent = new Intent(getApplicationContext(), MediaPlayerService.class);
        pauseIntent.putExtra(mediaAction, actionPause);

        player = new MediaPlayer();
        player.setAudioAttributes(new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaButton.setImageDrawable(getApplication().getResources().getDrawable(android.R.drawable.ic_media_play, null));
                player.seekTo(0);
            }
        });

        serviceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button mButton = (Button) v;
                String mText = mButton.getText().toString();

                if (!mIsBound) {
                    connectService();
                } else {
                    unbindService(mConnection);
                    disconnectViewChange();
                }
            }
        });

        retrieveButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mIsBound) {
                    if (isChecked) {

                        try {
                            List<Song> songs = mMusicApiService.getAllSongs();
                            lstView = findViewById(R.id.song_index);
                            MusicListAdapter adapter = new MusicListAdapter(getApplicationContext(), R.layout.song_individual, songs);
                            lstView.setAdapter(adapter);
                            for (int i = 0; i < songs.size(); i++) {
                                Log.i(TAG, songs.get(i).getTitle());
                            }

                            lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    try {
                                        startService(pauseIntent);
                                        mediaButton.setImageDrawable(getApplicationContext().getResources().getDrawable(android.R.drawable.ic_media_play, null));
                                        MediaPlayerService.selectedSong = mMusicApiService.getSong(position);
                                        MediaPlayerService.selectedSongUrl = mMusicApiService.getSongUrl(position);
                                        setupSelectedSongView();
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (RemoteException e) {
                            disconnectViewChange();
                            e.printStackTrace();
                        }

                    } else {
                        lstView.setAdapter(null);
                    }
                } else {
                    if (!isChecked) {
                        lstView.setAdapter(null);
                    } else {
                        buttonView.setChecked(false);
                        Toast.makeText(getApplicationContext(), "Connect to Music Central to retrieve Songs", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        mediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!player.isPlaying()) {
                    startService(playIntent);
                    mediaButton.setImageDrawable(getApplicationContext().getResources().getDrawable(android.R.drawable.ic_media_pause, null));
                } else {
                    startService(pauseIntent);
                    mediaButton.setImageDrawable(getApplicationContext().getResources().getDrawable(android.R.drawable.ic_media_play, null));
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MediaPlayerService.selectedSong != null) {
            setupSelectedSongView();
            final Intent musicServiceIntent =
                    new Intent(getApplicationContext(), MediaPlayerService.class);
            startService(musicServiceIntent);
            mediaButton.setImageDrawable(this.getResources().getDrawable(android.R.drawable.ic_media_pause, null));
        } else {
            selectedSongRow.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIsBound) {
            unbindService(this.mConnection);
            disconnectViewChange();
        }
    }

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder iService) {
            mMusicApiService = MusicAPI.Stub.asInterface(iService);
            connectViewChange();
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            mMusicApiService = null;
            Toast.makeText(getApplicationContext(), "Disconnected from Music Central Service Connect again please!", Toast.LENGTH_LONG).show();
            disconnectViewChange();
        }

        @Override
        public void onBindingDied(ComponentName className) {
            disconnectViewChange();
        }
    };

    private void connectService() {
        Intent i = new Intent(MusicAPI.class.getName());
        ResolveInfo info = getPackageManager().resolveService(i, 0);
        if (info != null) {
            i.setComponent(new ComponentName(info.serviceInfo.packageName, info.serviceInfo.name));
            boolean isBound = bindService(i, mConnection, Context.BIND_AUTO_CREATE);
            if (isBound) {
                Log.i(TAG, "Successfully connected to Music Central");
            } else {
                Log.i(TAG, "Unsuccessful in connecting to music central");
            }
        } else {
            Toast.makeText(getApplicationContext(), "Music central is currently not in service", Toast.LENGTH_LONG).show();
        }
    }

    private void disconnectViewChange() {
        mIsBound = false;
        serviceButton.setText(R.string.connect);
        statusText.setText(R.string.disconnected_status);
        statusButton.setBackground(getApplicationContext().getDrawable(R.drawable.red_round_button));
        retrieveButton.setChecked(false);
        if (lstView != null) {
            lstView.setAdapter(null);
        }
    }

    private void connectViewChange() {
        mIsBound = true;
        serviceButton.setText(R.string.disconnect);
        statusText.setText(R.string.connected_status);
        statusButton.setBackground(getApplicationContext().getDrawable(R.drawable.green_round_button));
    }

    private void setupSelectedSongView() {
        ImageView thumbnail = findViewById(R.id.bitmap_main);
        thumbnail.setImageBitmap(MediaPlayerService.selectedSong.getImage());

        TextView artistText = findViewById(R.id.artist_name_main);
        artistText.setText(MediaPlayerService.selectedSong.getArtist());

        TextView titleText = findViewById(R.id.song_title_main);
        titleText.setText(MediaPlayerService.selectedSong.getTitle());

        selectedSongRow.setVisibility(View.VISIBLE);
    }
}
