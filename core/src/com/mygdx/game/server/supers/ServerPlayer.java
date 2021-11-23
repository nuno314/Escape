package com.mygdx.game.server.supers;

public class ServerPlayer {
    private final String username;
    private int x;
    private int y;

    public ServerPlayer(String username) {
        this.username = username;
    }

    public void update() {

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
