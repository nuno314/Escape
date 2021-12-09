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
        if (knobY > 0.4 && player.getIsGround() == true) {
            player.getBody().applyForceToCenter(0,150, true);
        }

        player.getBody().setLinearVelocity(horizontalForce , player.getBody().getLinearVelocity().y);
    }
}
