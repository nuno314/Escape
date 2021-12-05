package com.mygdx.game.client.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.scenes.Hud;
import com.mygdx.game.client.screens.PlayScreen;
import com.mygdx.game.client.utils.Interactive;

public class Trap extends Interactive {


    public Trap(PlayScreen srreen, MapObject object) {
        super(srreen, object);
        fixture.setUserData(this);
        setCategoryFilter(Box2D.TRAP_BIT);
    }

    @Override
    public void OnFootHit(Steven steven) {
        Gdx.app.log("TRAP: ","COLLISION");
        steven.isCollied=true;
        Box2D.manager.get("audio/sounds/Trap.mp3", Sound.class).play();

        if(Hud.getScore()>=10)
            Hud.addScore(-10);
    }

    @Override
    public void OnBodyHit(Steven steven) {

    }
}
