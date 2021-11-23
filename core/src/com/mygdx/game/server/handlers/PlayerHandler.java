package com.mygdx.game.server.handlers;

import com.mygdx.game.server.supers.ServerPlayer;

import java.util.LinkedList;

public class PlayerHandler {
    public static final PlayerHandler INSTANCE = new PlayerHandler();

    private final LinkedList<ServerPlayer> players;

    public  PlayerHandler() {
        this.players = new LinkedList<>();
    }

    public void update() {
        for (int i = 0; i < this.players.size(); i++) {
            this.players.get(i).update();
        }
    }
}
