package com.touchizen.double3;

import android.content.Context;
import android.media.MediaPlayer;

public class Sound {
    MediaPlayer mPlayer;

    Sound(Context context, int id) {
        mPlayer = MediaPlayer.create(context,id);
    }

    public void play() {
        mPlayer.seekTo(0);
        mPlayer.start();
    }

    public void stop() {
        mPlayer.stop();
        mPlayer.release();
    }
}
