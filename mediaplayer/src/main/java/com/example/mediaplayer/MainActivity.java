package com.example.mediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PlayListFragment.OnSelectedTrackListener {

    public static final String KEY_FILE = "KEY_FILE";
    public static final String KEY_POSITION = "KEY_POSITION";
    private final String LOG_TAG = "myLogs";

    private boolean isBound = false;
    private ServiceConnection sConn;
    private MusicService musicService;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "MainActivity onCreate");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, PlayListFragment.newInstance(), PlayListFragment.FRAGMENT_TAG)
                .commit();

        intent = new Intent(this, MusicService.class);

        sConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected");
                musicService = ((MusicService.ServiceBinder) service).getService();
                isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
                musicService = null;
                isBound = false;
            }
        };
        onNewIntent(getIntent());
    }

    @Override
    public void onSelectTrack(List<AudioFile> files, int position) {
        PlaybackFragment playbackFragment = PlaybackFragment.newInstance(files, position);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, playbackFragment, PlaybackFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();

        if (musicService != null) {
            if (!musicService.isMediaplayerPlaing()) {
                runService(files, position);
            } else {
                musicService.stopMusic();
                runService(files, position);
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey("KEY_FILES")) {
            List<AudioFile> files = (List<AudioFile>) extras.getSerializable("KEY_FILES");
            int position = extras.getInt("KEY_POSITION");

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .replace(R.id.fragmentContainer, PlayListFragment.newInstance(), PlayListFragment.FRAGMENT_TAG)
                    .add(R.id.fragmentContainer, PlaybackFragment.newInstance(files, position))
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void runService(List<AudioFile> files, int position) {
        intent.putExtra(KEY_FILE, (Serializable) files);
        intent.putExtra(KEY_POSITION, position);
        startService(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        bindService(intent, sConn, BIND_AUTO_CREATE);
        isBound = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(sConn);
            isBound = false;
        }
    }
}
