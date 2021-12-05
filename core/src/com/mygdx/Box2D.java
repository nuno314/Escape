package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.screens.PlayScreen;

public class Box2D extends Game {
    public static final float WIDTH = 576;
    public static final float HEIGHT = 1056;
    public static final float PPM = 100;

    private OrthographicCamera camera;

    //	public  Bodydef finishDoor;
    @Override
    public void create() {


        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 600);

        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }



    public OrthographicCamera getCamera() {
        return camera;
    }

    public static Box2D getInstance() {
        return (Box2D) Gdx.app.getApplicationListener();
    }
}
