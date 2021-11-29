package com.mygdx.game.server.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.global.JoinRequestEvent;
import com.mygdx.game.server.handlers.PlayerHandler;
import com.mygdx.game.server.supers.ServerPlayer;

public class JoinListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof JoinRequestEvent) {
            final JoinRequestEvent joinRequestEvent = (JoinRequestEvent) object;

            final ServerPlayer serverPlayer = new ServerPlayer(joinRequestEvent.username, connection);
            PlayerHandler.INSTANCE.getPlayers().add(serverPlayer);
        }
        super.received(connection, object);
    }
}
