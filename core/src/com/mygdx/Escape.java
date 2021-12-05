package com.mygdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.screens.PlayScreen;

public class Escape extends Game {
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

    public static Escape getInstance() {
        return (Escape) Gdx.app.getApplicationListener();
    }
}
