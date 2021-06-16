package services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;

import at.aau.risiko.R;

public class BackgroundMusicService extends Service {
    private static final String TAG = "MusicService";
    static MediaPlayer player;
    private static  int length = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "OnCreate executes");

        player = MediaPlayer.create(this, R.raw.risiko);
        player.setLooping(true);
        player.setVolume(80,80);
        player.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "OnStartCommand executed!");
        player.start();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy ()
    {
        player.stop();
        player.release();
    }

    public static void pauseMusic()
    {
        if(player.isPlaying())
        {
            player.pause();
            length=player.getCurrentPosition();

        }
    }

    public static  void resumeMusic()
    {
        if(!player.isPlaying())
        {
            player.seekTo(length);
            player.start();
        }
    }





}
