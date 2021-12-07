package com.mygdx.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.Escape;
import com.mygdx.screens.PlayScreen;
import com.mygdx.utils.Interactive;

public class Ground extends Interactive {
    public Ground(PlayScreen srreen, MapObject object) {
        super(srreen, object);
        fixture.setUserData(this);
        setCategoryFilter(Escape.GROUND_BIT);
    }

    @Override
    public void OnFootHit(Steven steven) {
        Gdx.app.log("GROUND: ","COLLISION");
        steven.isGround=true;
    }

    @Override
    public void OnBodyHit(Steven steven) {

    }
}
