package services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import at.aau.risiko.R;

public class BackgroundMusicService extends Service {
    private static final String TAG = "MusicService";
    MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.i(TAG, "OnCreate executes");

        player = MediaPlayer.create(this,R.raw.music);
        player.setLooping(true);
        player.setVolume(100,100);
        player.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "OnStartCommand executed!");
        player.start();
        return Service.START_STICKY;
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }
}
