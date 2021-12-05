package com.mygdx.sprites;

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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.Escape;
import com.mygdx.handlers.ResourceHandler;
import com.mygdx.screens.PlayScreen;
import com.badlogic.gdx.utils.Array;

public class Steven extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, LEFTING, RIGHTING, GROWING, DEAD };
    public State currentState;
    public State previousState;

    public World world;
    public Body player;
    String username;

    private float stateTimer;
    private TextureRegion stevenStand;
    private Animation<TextureRegion> stevenLeft;
    private Animation<TextureRegion> stevenRight;
    private TextureRegion stevenJump;
    private TextureRegion stevenDead;

    private SpriteBatch batch;
    private Texture texture;

    private PlayScreen screen;

    public Steven(final String username, int order) {
        this.screen = PlayScreen.getINSTANCE();
        this.world = PlayScreen.getWorld();
        this.username = username;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        if (order == 1) {
            stevenLeft = ResourceHandler.left1;
            stevenRight = ResourceHandler.right1;
            stevenStand = ResourceHandler.stand1;
        }
        //else (order == 2)

        defineSteven();
        setBounds(0,0, 40 / Escape.PPM, 52 / Escape.PPM);
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
        bodyDef.position.set(x / Escape.PPM, y / Escape.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Escape.PPM, height / Escape.PPM);

       // player.setLinearDamping(0.5f);
        player.createFixture(shape, 0);
        shape.dispose();
    }

    public void inputUpdate(float delta) {
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



    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
            case LEFTING:
                region = stevenLeft.getKeyFrame(stateTimer, true);
                break;
            case RIGHTING:
                region = stevenRight.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                region = stevenStand;
                break;
            default:
                region = stevenStand;
                break;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;

        return region;
    }

    public State getState() {

        if (player.getLinearVelocity().x > 0)
            return State.RIGHTING;
        else if (player.getLinearVelocity().x < 0)
            return State.LEFTING;
        return State.STANDING;
    }

    public void draw(Batch batch) {

        super.draw(batch);
    }

    public String getUsername() {
        return this.username;
    }
}
