package com.mygdx.game.client.sprites;

import static com.mygdx.game.client.utils.Constants.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.screens.PlayScreen;

import java.lang.reflect.Array;

public class Steven extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body player;

    private TextureRegion stevenStand;
    private Animation stevenRun;
    private TextureRegion stevenJump;
    private TextureRegion stevenDead;

    private SpriteBatch batch;
    private Texture texture;


    private PlayScreen screen;

    public Steven(PlayScreen screen) {
        this.screen = screen;
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;

//        stevenRun = new Animation(1 / 15f, screen.getAtlas().findRegions("left"));
//        stevenStand = new TextureRegion(screen.getAtlas().findRegion("stand"));
        defineSteven();
    }

    public void update(float dt) {
        //setPosition(player.getPosition().x - getWidth(), player.getPosition().y);
    }

    public void defineSteven() {
        int x = 5500;
        int y = 5500;
        int width = 3000;
        int height = 3000;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        player.createFixture(shape, 1.0f);
        //shape.dispose();
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
