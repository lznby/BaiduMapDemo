package com.lznby.baidumapdemo.util;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.IBinder;


/**
 * 播放警报服务
 */
public class MusicService extends Service{

    public static MediaPlayer mediaPlayer = new MediaPlayer();
    private AssetManager assetManager;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();//开始播放
                }
                // 监听音频播放完的代码，实现音频的自动循环播放
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer arg0) {
                        if(!mediaPlayer.isPlaying()){
                            mediaPlayer.start();//开始播放
                        }
                        mediaPlayer.setLooping(true);
                    }
                });
                stopSelf(startId);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        initMediaPlayer();//初始化MediaPlayer

        super.onCreate();
    }

    private void initMediaPlayer(){
        try {
            assetManager = getAssets();
            AssetFileDescriptor fileDescriptor = assetManager.openFd("music/music.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),
                    fileDescriptor.getStartOffset());
            mediaPlayer.prepare();//让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
