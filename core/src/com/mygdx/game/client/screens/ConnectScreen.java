package com.mygdx.game.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.client.Box2D;

public class ConnectScreen implements Screen {

    private final Stage stage;
    private final Table root;

    public ConnectScreen() {
        this.stage = new Stage();
        this.stage.getViewport().setCamera(Box2D.getInstance().getCamera());
        this.root = new Table();
        this.root.setBounds(0, 0, 800 / Box2D.PPM, 600 / Box2D.PPM);

        this.root.add(new Image(new Texture(Gdx.files.internal("badlogic.jpg"))));
        this.stage.addActor(this.root);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        this.stage.draw();
        this.stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
