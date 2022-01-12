package com.mygdx.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.Escape;
import com.mygdx.handlers.EventHandler;
import com.mygdx.handlers.ResourceHandler;
import com.mygdx.screens.PlayScreen;

public class Steven extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, LEFTING, RIGHTING, DEAD , PASS,FINISH};
    public State currentState;
    public State previousState;

    public World world;
    public Body player;
    String username;

    private float stateTimer;
    private TextureRegion stevenStand;
    private Animation<TextureRegion> stevenLeft;
    private Animation<TextureRegion> stevenRight;

    public boolean isCollied=false;
    public boolean isPassed=false;
    public boolean isGround;
    public boolean isCollision=false;
    public boolean isOnGround =false;

    Vector2 previousPosition;

    public Steven(final String username) {
        this.world = PlayScreen.getWorld();
        this.username = username;
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;

        previousPosition = new Vector2(getX(), getY());

//        if (EventHandler.isPlayer1) {
            stevenLeft = ResourceHandler.left1;
            stevenRight = ResourceHandler.right1;
            stevenStand = ResourceHandler.stand1;
//        }

        if (EventHandler.isPlayer2) {
            stevenLeft = ResourceHandler.left2;
            stevenRight = ResourceHandler.right2;
            stevenStand = ResourceHandler.stand2;
        }

        defineSteven();
        setBounds(0,0, 40 / com.mygdx.Escape.PPM, 52 / com.mygdx.Escape.PPM);
        setRegion(stevenStand);
    }

    public void update(float dt) {
        //add for collision
        if (isCollied==true){
            reDefineSteven();
        }

        setRegion(getFrame(dt));
        setPosition(player.getPosition().x - getWidth() / 2, player.getPosition().y - getHeight()/2);
    }

    public void updateTouchpad(float dt) {

        //add for collision
        if (isCollied){
            reDefineSteven();
        }

        setRegion(getFrame(dt));
        setPosition(player.getPosition().x - getWidth() / 2, player.getPosition().y - getHeight()/2);
    }

    public boolean getIsGround() {
        return isGround;
    }

    public boolean isOnTrap() {
        return isCollied;
    }

    public void defineSteven() {
        int x = 250, y = 120;
        int width = 20;
        int height = 26;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x / com.mygdx.Escape.PPM, y / com.mygdx.Escape.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / com.mygdx.Escape.PPM, height / com.mygdx.Escape.PPM);

        FixtureDef fdef = new FixtureDef();

        fdef.filter.categoryBits= Escape.STEVEN_BIT;
        fdef.filter.maskBits= Escape.DEFAULT_BIT| Escape.DOOR_BIT | Escape.TRAP_BIT
                    | Escape.GROUND_BIT; //bit co the va cham
        fdef.shape=shape;
        player.setLinearDamping(0.5f);
        player.createFixture(fdef).setUserData(this);

        shape.dispose();

        //CREATE FOOT:
        PolygonShape foot=new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]= new Vector2(-17,-20).scl(1/ Escape.PPM);
        vertices[1]=new Vector2(17,-20).scl(1/ Escape.PPM);
        vertices[2]= new Vector2(-17,-28).scl(1/ Escape.PPM);
        vertices[3]=new Vector2(17,-28).scl(1/ Escape.PPM);
        foot.set(vertices);
        fdef.shape=foot;
        fdef.filter.categoryBits= Escape.STEVEN_FOOT_BIT;

        player.createFixture(fdef).setUserData(this);
    }

    //add for collision
    public void reDefineSteven(){
        Vector2 currentPos=player.getPosition();
        world.destroyBody(player);
        int width = 20;
        int height = 26;

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(currentPos.sub(0.1f, 0));
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        player = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/ Escape.PPM, height/ Escape.PPM);

        FixtureDef fdef=new FixtureDef();

        fdef.filter.categoryBits= Escape.STEVEN_BIT;
        fdef.filter.maskBits= Escape.DEFAULT_BIT| Escape.DOOR_BIT | Escape.TRAP_BIT
                        | Escape.GROUND_BIT; //bit co the va cham
        fdef.shape=shape;
        player.setLinearDamping(0.5f);
        player.createFixture(fdef).setUserData(this);
        shape.dispose();

        //CREATE FOOT:
        PolygonShape foot=new PolygonShape();
        Vector2[] vertices=new Vector2[4];
        vertices[0]= new Vector2(-17,-20).scl(1/ Escape.PPM);
        vertices[1]=new Vector2(17,-20).scl(1/ Escape.PPM);
        vertices[2]= new Vector2(-22,-28).scl(1/ Escape.PPM);
        vertices[3]=new Vector2(22,-28).scl(1/ Escape.PPM);
        foot.set(vertices);
        fdef.shape=foot;
        fdef.filter.categoryBits= Escape.STEVEN_FOOT_BIT;

        player.createFixture(fdef).setUserData(this);

        isCollied=false;
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

    public boolean hasMoved() {

        if (previousPosition.x != getX() || previousPosition.y != getY()) {
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }

    public void draw(Batch batch) {

        super.draw(batch);
    }

    public Body getBody() {
        return player;
    }

    public String getUsername() {
        return this.username;
    }
}
