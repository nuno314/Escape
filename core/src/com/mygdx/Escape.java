package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
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
    private static final Escape INSTANCE = new Escape();
    public static final float WIDTH = 576;
    public static final float HEIGHT = 1056;
    public static final float PPM = 100;

    private final static OrthographicCamera camera = new OrthographicCamera();
    private final static Viewport viewport = new FitViewport(Escape.WIDTH / Escape.PPM, Escape.HEIGHT / Escape.PPM, camera);

    private Socket socket;
    @Override
    public void create() {
        //setScreen(new ConnectScreen());
        //setScreen(new PlayScreen());
        connectSocket();
        configSocketEvents();
        setScreen(PlayScreen.getINSTANCE());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static Escape getINSTANCE() {
        return INSTANCE;
    }

    public static Viewport getViewport() {
        return viewport;
    }
    void connectSocket() {
        try {
            System.out.println("Success");
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void configSocketEvents() {
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener()  {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
                //player1 = new Steven();
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting ID");
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    String id = data.getString("id");
                    Gdx.app.log("SocketIO", "New Player Connect: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "Error getting New Player ID");
                }
            }
        });
    }
}
