package com.mygdx.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;

import com.mygdx.Escape;
import com.mygdx.scenes.Hud;
import com.mygdx.screens.PlayScreen;
import com.mygdx.utils.Interactive;

public class Trap extends Interactive {


    public Trap(PlayScreen srreen, MapObject object) {
        super(srreen, object);
        fixture.setUserData(this);
        setCategoryFilter(Escape.TRAP_BIT);
    }

    @Override
    public void OnFootHit(Steven steven) {
        Gdx.app.log("TRAP: ","COLLISION");
        steven.isCollied=true;
        Escape.manager.get("audio/sounds/Trap.mp3", Sound.class).play();
//
//        if(Hud.getScore()>=10)
//            Hud.addScore(-10);

        Hud.minusTime(2);
    }

    @Override
    public void OnBodyHit(Steven steven) {

    }

    @Override
    public void EndContact(Steven steven) {
        //steven.isGround=false;
    }
}
