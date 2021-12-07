package com.mygdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.sprites.Steven;

public class InputHandler {
    public static final InputHandler INSTANCE = new InputHandler();

    public static void inputUpdate(Body player, float delta) {
        int horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -= 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 2;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            player.applyForceToCenter(0,300, true);
        }

        player.setLinearVelocity(horizontalForce , player.getLinearVelocity().y);
    }
}
