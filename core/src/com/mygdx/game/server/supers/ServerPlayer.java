package com.mygdx.game.server.supers;

import com.esotericsoftware.kryonet.Connection;

public class ServerPlayer {
    private final String username;
    private final Connection connection;

    private boolean moveUp, moveDown, moveLeft, moveRigtht;

    private float x;
    private float y;

    public ServerPlayer(String username, Connection connection) {
        this.username = username;
        this.connection = connection;
    }

    public void update() {

    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
