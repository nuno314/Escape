package com.mygdx.game.client.handlers;

import java.util.LinkedList;
import com.mygdx.game.client.sprites.Steven;
public class PlayerHandler {

    public static final PlayerHandler INSTANCE = new PlayerHandler();

    private final LinkedList<Steven> players = new LinkedList<>();

    public Steven getPlayerByUsername(final String username) {
        for (int i = 0; i < this.players.size(); i++) {
            final Steven player = this.players.get(i);


        }
    };
}
