package com.mygdx.game.client.sprites;

import static com.mygdx.game.client.utils.Constants.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
        stevenStand = new TextureRegion(screen.getAtlas().findRegion("stand"));
        defineSteven();
        setBounds(0,0, 40, 52);
        setRegion(stevenStand);

    }

    public void update(float dt) {
        inputUpdate(dt);
        setRegion(getFrame(dt));
        setPosition(player.getPosition().x - getWidth() / 2, player.getPosition().y - getHeight()/2);
    }

    public void defineSteven() {
        int x = 120;
        int y = 120;
        int width = 20;
        int height = 26;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

       // player.setLinearDamping(0.5f);
        player.createFixture(shape, 0);
        shape.dispose();
    }

    public void inputUpdate(float delta) {
        int horizontalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            horizontalForce -= 100;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 100;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

            player.applyForceToCenter(0,1000000, true);
        }

        player.setLinearVelocity(horizontalForce , player.getLinearVelocity().y);
    }



    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case STANDING:
                region = stevenStand;
            default:
                region = stevenStand;
                break;
        }

        previousState = currentState;

        return region;
    }

    public State getState() {
        return State.STANDING;
    }

    public void draw(Batch batch) {

        super.draw(batch);
    }


}
