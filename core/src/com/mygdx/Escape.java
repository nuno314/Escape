package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.mygdx.screens.ConnectScreen;
import com.mygdx.screens.HowToPlayScreen;
import com.mygdx.screens.InputNameScreen;
import com.mygdx.screens.LoadingScreen;
import com.mygdx.screens.PlayScreen;
import com.mygdx.screens.RankScreen;
import com.mygdx.screens.RoomListScreen;
import com.mygdx.screens.RoomScreen;
import com.mygdx.screens.SettingScreen;
import com.mygdx.screens.UpdateLaterScreen;
import com.mygdx.utils.RoomItem;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.socket.client.Socket;

public class Escape extends Game {



    public static final float WIDTH = 576;
    public static final float HEIGHT = 1056;
    public static final float PPM = 100;

    public enum ScreenKey
        {
            INPUT_NAME,
            CONNECT,
            NEW_ROOM,
            ROOM_LIST,
            PLAY,
            RANK,
            SETTING,
            UPDATE_LATER,
            HOW_TO_PLAY,
            LOADING_SCREEN
        };
    private Map<ScreenKey, Screen> screens;
    private ConnectScreen connectScreen;
    private InputNameScreen inputNameScreen;
    private RoomScreen roomScreen;
    private RoomListScreen roomListScreen;
    private PlayScreen playScreen;
    private RankScreen rankScreen;
    private SettingScreen settingScreen;
    private UpdateLaterScreen updateLaterScreen;
    private HowToPlayScreen howToPlayScreen;
    private LoadingScreen loadingScreen;

    //add for collision
    public static final short DEFAULT_BIT=1;
    public  static final short STEVEN_BIT=2;
    public static final short TRAP_BIT=4;
    public static final short DESTROYED_BIT=8;
    public static final short STEVEN_DEAD=16;
    public static final short STEVEN_FOOT_BIT=32;
    public static final short DOOR_BIT =64;
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

        inputNameScreen = new InputNameScreen(this);
        roomScreen = new RoomScreen(this);
        connectScreen = new ConnectScreen(this);
        playScreen = new PlayScreen(this);
        roomListScreen = new RoomListScreen(this);
        rankScreen = new RankScreen(this);
        settingScreen = new SettingScreen(this);
        updateLaterScreen = new UpdateLaterScreen(this);
        loadingScreen = new LoadingScreen(this);

        screens = new HashMap<>();

        screens.put(ScreenKey.INPUT_NAME, inputNameScreen);
        screens.put(ScreenKey.NEW_ROOM, roomScreen);
        screens.put(ScreenKey.CONNECT, connectScreen);
        screens.put(ScreenKey.PLAY, playScreen);
        screens.put(ScreenKey.ROOM_LIST, roomListScreen);
        screens.put(ScreenKey.RANK, rankScreen);
        screens.put(ScreenKey.SETTING, settingScreen);
        screens.put(ScreenKey.UPDATE_LATER, updateLaterScreen);
        screens.put(ScreenKey.LOADING_SCREEN, loadingScreen);

        setScreen(ScreenKey.INPUT_NAME);
    }

    public void setScreen(ScreenKey key) {
        setScreen(screens.get(key));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

    public void setPlayerName(String name) {
        connectScreen.setPlayer(name);
    }

    public void setRoomList(Array<RoomItem> list) {
        roomListScreen.setRoomList(list);
    }

    public void setSelectedRoomID(String ID) {
        if (ID != null)
            roomListScreen.setSelectedRoomID(ID);
    }

    public void setRoomID(String id) {
        if (id != null)
            roomScreen.setRoomID(id);
    }
}
