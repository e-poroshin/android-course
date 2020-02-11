package com.example.mediaplayer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.Serializable;
import java.util.List;

public class MusicService extends Service {

    private final IBinder binder = new ServiceBinder();

    private List<AudioFile> files;
    private int position;
    private int path;

    private final String LOG_TAG = "myLogs";
    private final String CHANNEL_ID = "myChannel";
    private MediaPlayer mediaPlayer;
    private NotificationManager notificationManager;

    private int pausePosition = 0;

    public MusicService() {
    }

    public class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "MusicService onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "MusicService onStartCommand");
        files = (List<AudioFile>)intent.getSerializableExtra(MainActivity.KEY_FILE);
        position = intent.getIntExtra(MainActivity.KEY_POSITION, 0);

        runNotification(files.get(position).getTitle());

        path = files.get(position).getPath();
        startMusic(path);

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "MusicService onBind");
        return binder;
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                pausePosition = mediaPlayer.getCurrentPosition();
            }
        }
    }

    public void resumeMusic() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(pausePosition);
                mediaPlayer.start();
            }
        }
    }

    public void startMusic(int path) {
        mediaPlayer = MediaPlayer.create(this, path);

        if (mediaPlayer != null) {
            mediaPlayer.setVolume(50, 50);
            mediaPlayer.start();
        }
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playNextTrack() {
        if (position < files.size() - 1) {
            position++;
            path = files.get(position).getPath();
            startMusic(path);
            runNotification(files.get(position).getTitle());
        }
    }

    public void playPreviousTrack() {
        if (position > 0) {
            position--;
            path = files.get(position).getPath();
            startMusic(path);
            runNotification(files.get(position).getTitle());
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "MusicService onDestroy");
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } finally {
                mediaPlayer = null;
            }
        }
    }

    public int getMediaPlayerCurrentPosition() {
        int currentPosition = 0;
        if (mediaPlayer != null) {
            currentPosition = mediaPlayer.getCurrentPosition();
        }
        return currentPosition;
    }

    public int getMediaPlayerDuration() {
        int duration = 0;
        if (mediaPlayer != null) {
            duration = mediaPlayer.getDuration();
        }
        return duration;
    }

    public void seekMediaPlayerTo(int progress) {
        mediaPlayer.seekTo(progress);
    }

    public void runNotification(String title) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel", NotificationManager.IMPORTANCE_LOW);
            channel.setDescription("MyChannelDescription");
            channel.enableVibration(false);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent playbackIntent = new Intent(getApplicationContext(), MainActivity.class);
        playbackIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        playbackIntent.putExtra("KEY_FILES", (Serializable) files);
        playbackIntent.putExtra("KEY_POSITION", position);
        PendingIntent playbackPendingIntent = PendingIntent.getActivity(this, 0, playbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_audiotrack_blue_24dp)
                .setContentTitle("Audio player")
                .setContentText(title)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentIntent(playbackPendingIntent)
                .setSound(null);

        Notification notification = builder.build();
        startForeground(1, notification);
    }

    public boolean isMediaplayerPlaing() {
        if (mediaPlayer!= null) {
            if (mediaPlayer.isPlaying()) {
                return true;
            }
        }
        return false;
    }

}
