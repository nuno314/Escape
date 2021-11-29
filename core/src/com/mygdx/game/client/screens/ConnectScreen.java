package com.mygdx.game.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.client.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.client.handlers.LabelHandler;

public class ConnectScreen implements Screen {

    private final Stage stage;
    private final Table root;

    private final TextField ipAddressLabel;
    private final TextField portLabel;
    private final TextField usernameLabel;

    private final TextButton connectButton;

    private final Label errorLabel;

    public ConnectScreen() {
        this.stage = new Stage();
        this.stage.getViewport().setCamera(Box2D.getInstance().getCamera());

        this.root = new Table();
        this.root.setBounds(0, 0, 800, 600);

        final Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        this.ipAddressLabel = new TextField("IP-Address", skin);
        this.portLabel = new TextField("Port", skin);
        this.usernameLabel = new TextField("Username", skin);

        this.connectButton = new TextButton("Connect", skin);

        this.errorLabel = LabelHandler.INSTANCE.createLabel("This is test error", 16, Color.RED);
        this.stage.addActor(this.root);

        this.setToDefault();
    }

    public void setToDefault() {
        this.root.clear();
        this.root.add(this.ipAddressLabel).width(250).row();
        this.root.add(this.portLabel).width(250).padTop(25).row();
        this.root.add(this.usernameLabel).width(250).padTop(50).row();
        this.root.add(this.connectButton).size(250,50).padTop(100).row();
        this.root.add(this.errorLabel).padTop(50);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
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
