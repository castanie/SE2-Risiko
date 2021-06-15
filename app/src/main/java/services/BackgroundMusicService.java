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
    public static MediaPlayer  player;
    private int length = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "OnCreate executes");

        if(player!= null)
        {
            player.setLooping(true);
            player.setVolume(100,100);
        }
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
        super.onDestroy();
        if(player != null)
        {
            try{
                player.stop();
                player.release();
            }finally {
                player = null;
            }
        }
    }

    public void resumeMusic(){
        if(!player.isPlaying()){
            player.seekTo(length);
            player.start();
        }
    }

    public void pauseMusic(){
        if(player.isPlaying()){
            player.pause();
            length = player.getCurrentPosition();
        }
    }


    public void stopMusic()
    {
        player.stop();
        player.release();
        player = null;
    }
}
