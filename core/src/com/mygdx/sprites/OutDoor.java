package com.mygdx.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.Escape;
import com.mygdx.screens.PlayScreen;
import com.mygdx.utils.Interactive;

public class OutDoor extends Interactive {
    public OutDoor(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(Escape.DOOR_BITCH_BIT);
    }

    @Override
    public void OnFootHit(Steven steven) {

    }

    @Override
    public void OnBodyHit(Steven steven) {
        steven.isPassed=true;
    }

    @Override
    public void EndContact(Steven steven) {

    }
}
