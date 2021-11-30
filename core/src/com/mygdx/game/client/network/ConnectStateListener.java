package com.mygdx.game.client.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.screens.ConnectScreen;
import com.mygdx.game.client.screens.PlayScreen;

public class ConnectStateListener extends Listener {
    @Override
    public void connected(Connection connection) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Box2D.getInstance().setScreen(PlayScreen.INSTANCE);
            }
        });
        super.connected(connection);
    }

    @Override
    public void disconnected(Connection connection) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                Box2D.getInstance().setScreen(ConnectScreen.INSTANCE);
                ConnectScreen.INSTANCE.getErrorLabel().setText("Connection lost!");
            }
        });
        super.disconnected(connection);
    }
}
