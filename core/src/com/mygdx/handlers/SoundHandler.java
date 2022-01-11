package com.mygdx.handlers;

import com.badlogic.gdx.audio.Music;
import com.mygdx.Escape;

public class SoundHandler {

    private static Music music = Escape.manager.get("audio/music/Escape_music.ogg", Music.class);

    public static boolean isVolumeOn = true;

    public static void turnOn(){

        music.setLooping(true);
        music.setVolume(0.1f);

        music.play();
    }

    public static void turnOff(){

        music.setLooping(true);
        music.setVolume(0);

        music.play();
    }
}
