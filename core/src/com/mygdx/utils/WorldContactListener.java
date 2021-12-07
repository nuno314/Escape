package com.mygdx.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.Escape;
import com.mygdx.sprites.Steven;
import com.mygdx.utils.Interactive;

public class WorldContactListener implements ContactListener {
    Fixture fixA;
    Fixture fixB;
    @Override
    public void beginContact(Contact contact) {
         fixA=contact.getFixtureA();
         fixB=contact.getFixtureB();
        Object f=fixA.getUserData(); //trap
        Object f1=fixB.getUserData();

        //Gdx.app.log("f: ",f+"");
        // Gdx.app.log("f1: ",f1+"");
        int cdef= fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cdef)
        {
            case Escape.GROUND_BIT | Escape.STEVEN_FOOT_BIT:
            case Escape.TRAP_BIT | Escape.STEVEN_FOOT_BIT:
                if (fixA.getFilterData().categoryBits == Escape.STEVEN_FOOT_BIT) {
                    ((Interactive) fixB.getUserData()).OnFootHit((Steven) fixA.getUserData());
                } else {
                    ((Interactive) fixA.getUserData()).OnFootHit((Steven) fixB.getUserData());
                }

                break;
            case Escape.DOOR_BITCH_BIT | Escape.STEVEN_BIT:
                Gdx.app.log("door", "collide");
                if (fixA.getFilterData().categoryBits == Escape.STEVEN_BIT) {
                    Gdx.app.log("steven", "");
                } else if (fixA.getFilterData().categoryBits== Escape.DOOR_BITCH_BIT){
                    ((Interactive)fixA.getUserData()).OnBodyHit((Steven) fixB.getUserData());
                    Gdx.app.log("steven", "is door");
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {
        int cdef= fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
        switch (cdef){
            case Escape.GROUND_BIT| Escape.STEVEN_FOOT_BIT:
                if (fixA.getFilterData().categoryBits == Escape.STEVEN_FOOT_BIT) {
                    ((Interactive) fixB.getUserData()).EndContact((Steven) fixA.getUserData());
                } else {
                    ((Interactive) fixA.getUserData()).EndContact((Steven) fixB.getUserData());
                }
                break;
            case Escape.TRAP_BIT|Escape.STEVEN_FOOT_BIT:
                Gdx.app.log("END: ","TRAP");
                break;
            default:
                Gdx.app.log("defu end: ","");
                break;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
