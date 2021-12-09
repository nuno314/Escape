package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.screens.ConnectScreen;
import com.mygdx.screens.PlayScreen;

import org.json.JSONException;
import org.json.JSONObject;

import javax.naming.ldap.SortKey;
import javax.swing.text.View;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Escape extends Game {

    public static final float WIDTH = 576;
    public static final float HEIGHT = 1056;
    public static final float PPM = 100;

    //add for collision
    public static final short DEFAULT_BIT=1;
    public  static final short STEVEN_BIT=2;
    public static final short TRAP_BIT=4;
    public static final short DESTROYED_BIT=8;
    public static final short STEVEN_DEAD=16;
    public static final short STEVEN_FOOT_BIT=32;
    public static final short DOOR_BITCH_BIT =64;
    public static final short GROUND_BIT =128;



    public static AssetManager manager;

    private Socket socket;
    @Override
    public void create() {
        //Set sound for my game
        manager = new AssetManager();
        manager.load("audio/music/Escape_music.ogg", Music.class);
        manager.load("audio/sounds/Trap.mp3", Sound.class);
        manager.load("audio/sounds/GameOver.mp3", Sound.class);
        manager.load("audio/sounds/PassLevel.mp3", Sound.class);
        manager.load("audio/sounds/FinishGame.mp3", Music.class);
        manager.finishLoading();

        setScreen(new ConnectScreen(this));
//        setScreen(new PlayScreen(this));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

}
