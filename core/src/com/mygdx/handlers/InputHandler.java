package com.mygdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.sprites.Steven;

public class InputHandler {
    public static final InputHandler INSTANCE = new InputHandler();

    public static void inputUpdateTouchpad(Steven player, float dt, float knobX, float knobY) {
        int horizontalForce = 0;
        System.out.println("X" + knobX);
        System.out.println("Y" + knobY);

        if (knobX < -0.4){
            horizontalForce -= 2;
        }
        if (knobX > 0.4) {
            horizontalForce += 2;
        }
        if (knobY > 0.4 && player.getIsGround() && !player.isOnTrap()) {
            player.getBody().applyForceToCenter(0,180, true);
        }

        player.getBody().setLinearVelocity(horizontalForce , player.getBody().getLinearVelocity().y);
    }
}
