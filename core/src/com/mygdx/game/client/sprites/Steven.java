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
import com.badlogic.gdx.utils.Array;

import javax.swing.Box;

public class Steven extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, LEFTING, RIGHTING, GROWING, DEAD , PASS,FINISH};
    public State currentState;
    public State previousState;

    public World world;
    public Body player;
    String username;

    private float stateTimer;
    Array<TextureRegion> frames;
    private TextureRegion stevenStand;
    private Animation<TextureRegion> stevenLeft;
    private Animation<TextureRegion> stevenRight;
    private TextureRegion stevenJump;
    private TextureRegion stevenDead;

    private SpriteBatch batch;
    private Texture texture;


    private PlayScreen screen;

    public Steven(PlayScreen screen, final String username) {
        this.screen = screen;
        this.world = screen.getWorld();
        this.username = username;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        stevenLeft = new Animation(0.333f, screen.getAtlas().findRegion("left"));
        stevenRight = new Animation(0.1f, screen.getAtlas().findRegion("right"));
        stevenStand = new TextureRegion(screen.getAtlas().findRegion("stand"));
        defineSteven();
        setBounds(0,0, 40 / Box2D.PPM, 52 / Box2D.PPM);
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
        bodyDef.position.set(x / Box2D.PPM, y / Box2D.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / Box2D.PPM, height / Box2D.PPM);

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
