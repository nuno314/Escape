package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.Escape;

public class RoomItem extends Table {
    private final String roomID;
    private final String player1;
    private final String player2;
    private final String p1ID;
    private final String p2ID;

    private Escape game;

    public RoomItem(String roomID, String player1, String player2, String p1ID, String p2ID, Skin skin) {
        this.roomID = roomID;
        this.player1 = player1;
        this.player2 = player2;
        this.p1ID = p1ID;
        this.p2ID = p2ID;

        Label roomIDlbl = new Label(roomID, skin);
        Label player1lbl = new Label(player1, skin);
        TextButton join = new TextButton("join", skin);
        join.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                 game.setScreen(Escape.ScreenKey.NEW_ROOM);
            }
        });
        this.add(roomIDlbl).pad(20).size(40);
        this.add(player1lbl).height(40).width(150);
        this.add(join).height(40).width(40);
        this.setTouchable(Touchable.enabled);
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("I got clicked!");
            }
        });



//        this.setWidth(320f);
//        this.setHeight(300f);
//        super.add(new Label(player1, skin));
    }


    public String getRoomID() {
        return roomID;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getP1ID() {
        return p1ID;
    }

    public String getP2ID() {
        return p2ID;
    }

    public void setGame(Escape game) {
        this.game = game;
    }
}
