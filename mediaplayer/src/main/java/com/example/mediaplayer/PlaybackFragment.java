package com.example.mediaplayer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.BIND_AUTO_CREATE;

public class PlaybackFragment extends Fragment {

    public static final String FRAGMENT_TAG = PlaybackFragment.class.getName();

    private final String LOG_TAG = "myLogs";
    private List<AudioFile> files;
    private int position;

    private boolean isBound = false;
    private ServiceConnection sConn;
    private MusicService musicService;
    private Timer timer;

    private ImageButton buttonPlay;
    private ImageButton buttonPrevious;
    private ImageButton buttonNext;
    private TextView textViewTitle;
    private SeekBar seekBarProgress;

    private State state;

    public static PlaybackFragment newInstance(List<AudioFile> files, int position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.KEY_FILE, (Serializable) files);
        bundle.putInt(MainActivity.KEY_POSITION, position);

        PlaybackFragment fragment = new PlaybackFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_playback, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt(MainActivity.KEY_POSITION);
            try {
                files = (List<AudioFile>) bundle.getSerializable(MainActivity.KEY_FILE);
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        buttonPlay = view.findViewById(R.id.buttonPlay);
        buttonPrevious = view.findViewById(R.id.buttonPrevious);
        buttonNext = view.findViewById(R.id.buttonNext);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        seekBarProgress = view.findViewById(R.id.seekBarProgress);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(LOG_TAG, "PlaybackFragment onServiceConnected");
                musicService = ((MusicService.ServiceBinder) service).getService();

                if (musicService != null) {
                    Log.d(LOG_TAG, "maxProgress = " + musicService.getMediaPlayerDuration());
                    if (musicService.isMediaplayerPlaing()) {
                        buttonPlay.setImageResource(R.drawable.ic_pause_blue_24dp);
                    } else {
                        buttonPlay.setImageResource(R.drawable.ic_play_arrow_blue_24dp);
                    }
                    runTimer();
                }
                isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(LOG_TAG, "PlaybackFragment onServiceDisconnected");
                musicService = null;
                isBound = false;
                timer.cancel();
            }
        };

        textViewTitle.setText(files.get(position).getTitle());
        state = State.PLAY;

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isBound) return;

                if (musicService != null) {
                    if (state.equals(State.PLAY)) {
                        Log.d(LOG_TAG, "state - pause");
                        musicService.pauseMusic();
                        buttonPlay.setImageResource(R.drawable.ic_play_arrow_blue_24dp);
                        state = State.PAUSE;
                    } else {
                        Log.d(LOG_TAG, "state - play");
                        musicService.resumeMusic();
                        buttonPlay.setImageResource(R.drawable.ic_pause_blue_24dp);
                        state = State.PLAY;
                    }
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousTrack();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTrack();
            }
        });

        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicService.seekMediaPlayerTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (musicService != null) {
            musicService.resumeMusic();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBound) {
            getActivity().unbindService(sConn);
            isBound = false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().bindService(new Intent(getActivity(),MusicService.class), sConn, BIND_AUTO_CREATE);
        isBound = true;
    }

    private void runTimer() {

        Log.d(LOG_TAG, "MediaPlayerDuration = " + musicService.getMediaPlayerDuration());

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (musicService != null) {
                    seekBarProgress.setMax(musicService.getMediaPlayerDuration());
                    seekBarProgress.setProgress(musicService.getMediaPlayerCurrentPosition());
                    if (musicService.getMediaPlayerCurrentPosition() >= musicService.getMediaPlayerDuration() - 1000) {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    nextTrack();
                                }
                            });
                        }
                    }
                }
            }
        }, 0, 1000);
    }

    private void nextTrack() {
        timer.cancel();
        if (musicService != null) {
            musicService.stopMusic();
            if (position < files.size() - 1) {
                position++;
                buttonPlay.setImageResource(R.drawable.ic_pause_blue_24dp);
                textViewTitle.setText(files.get(position).getTitle());
                seekBarProgress.setMax(musicService.getMediaPlayerDuration());
                musicService.playNextTrack();
            } else {
                Log.d(LOG_TAG, "NOnextTrack(), position = " + position);
                buttonPlay.setImageResource(R.drawable.ic_play_arrow_blue_24dp);
                Toast.makeText(getContext(), "Воспроизведение окончено", Toast.LENGTH_SHORT).show();
                musicService.stopMusic();
            }
        }
        runTimer();
    }

    private void previousTrack() {
        timer.cancel();
        if (musicService != null) {
            if (position > 0) {
                musicService.stopMusic();
                position--;
                textViewTitle.setText(files.get(position).getTitle());
                buttonPlay.setImageResource(R.drawable.ic_pause_blue_24dp);
                musicService.playPreviousTrack();
            } else {
                Toast.makeText(getContext(), "Это первая композиция", Toast.LENGTH_SHORT).show();
            }
        }
        runTimer();
    }
}
