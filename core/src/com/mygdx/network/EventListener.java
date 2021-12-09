package com.mygdx.network;

import com.badlogic.gdx.Gdx;
import com.mygdx.Escape;
import com.mygdx.handlers.PlayerHandler;
import com.mygdx.screens.ConnectScreen;
import com.mygdx.screens.PlayScreen;
import com.mygdx.sprites.Steven;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EventListener {

    public static final EventListener INSTANCE = new EventListener();
    private static int order;
    private static Socket socket;
    public EventListener() {

    }

    public static void connectSocket() {
        try {
            socket = IO.socket("http://localhost:8080");
            socket.connect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void configSocketEvents() {

        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener()  {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
                String username = ConnectScreen.usernameLabel.getText();
                PlayerHandler.INSTANCE.addPlayer(new Steven(username));
                Escape.getINSTANCE().setScreen(PlayScreen.INSTANCE);

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
//        }).on("playerDisconnected", new Emitter.Listener() {
//            @Override
//            public void call(Object... args) {
//                JSONObject data = (JSONObject) args[0];
//                try {
//                    String id = data.getString("id");
//
//                } catch (JSONException e) {
//                    Gdx.app.log("SocketIO", "Error getting New Player ID");
//                }
//            }
        });
    }
}
