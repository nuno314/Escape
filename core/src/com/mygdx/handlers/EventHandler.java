package com.mygdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.mygdx.utils.RoomItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class EventHandler {

    public static Socket socket;

    public static String id;
    public static String name;
    public static boolean isPlayer1 = false;
    public static boolean isPlayer2 = false;

    public static void connectSocket() {
        try {
            System.out.println("Success");
//            socket = IO.socket("https://escape-nodejs-server.herokuapp.com/");
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
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    id = data.getString("id");
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
