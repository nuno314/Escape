package com.mygdx.game.client.sprites;

import static com.mygdx.game.client.utils.Constants.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.client.screens.PlayScreen;

public class Ninja extends Sprite {
    public World world;
    public Body player;

    private SpriteBatch batch;
    private Texture texture;


    private PlayScreen screen;

    public Ninja(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();

        player = createBox(80, 120, 25, 55, false);
        batch = new SpriteBatch();
        texture = new Texture("data/ninja1.png");


    }

    public Body createBox(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();
        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }
}
