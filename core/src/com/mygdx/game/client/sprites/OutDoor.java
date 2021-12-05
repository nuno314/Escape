package com.mygdx.game.client.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.screens.PlayScreen;
import com.mygdx.game.client.utils.Interactive;

public class OutDoor extends Interactive {
    public OutDoor(PlayScreen srreen, MapObject object) {
        super(srreen, object);
        fixture.setUserData(this);
        setCategoryFilter(Box2D.DOOR_BITCH_BIT);


    }

    @Override
    public void OnFootHit(Steven steven) {

    }

    @Override
    public void OnBodyHit(Steven steven) {
        Gdx.app.log("DOOR: ","COLLISION");
        steven.isPassed=true;

    }
}
