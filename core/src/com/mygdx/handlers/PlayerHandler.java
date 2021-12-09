package com.mygdx.handlers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.sprites.Steven;

import java.util.LinkedList;

public class PlayerHandler {

    public static final PlayerHandler INSTANCE = new PlayerHandler();

    private final LinkedList<Steven> players = new LinkedList<>();

    public Steven getPlayerByUsername(final String username) {
        for (int i = 0; i < players.size(); i++) {
            final Steven player = players.get(i);

            if (player.getUsername().equals(username))
                return player;
        }
        return null;
    }

    public void render(final Batch batch) {
        for(int i = 0; i < this.players.size(); i++) {
            this.players.get(i).draw(batch);
        }
    }

    public void update(final float delta) {
        for(int i = 0; i < this.players.size(); i++) {
            this.players.get(i).update(delta);
        }
    }

    public void addPlayer(final Steven player) {
        players.add(player);
    }

    public void removePlayer(final Steven player) {
        players.remove(player);
    }

    public LinkedList<Steven> getPlayers() {
        return players;
    }
}
