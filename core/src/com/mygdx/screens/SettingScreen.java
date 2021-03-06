package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;
import com.mygdx.handlers.SoundHandler;

import javax.swing.ButtonGroup;
import javax.swing.plaf.basic.BasicBorders;

public class SettingScreen implements Screen {

    private final Escape game;
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public SettingScreen(Escape game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        SpriteBatch batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Button home = new Button(skin, "home_off");
        Button rank = new Button(skin, "rank_off");
        Button setting = new Button(skin, "setting");
        Button no = new Button(skin, "no");
        Button yes = new Button(skin, "yes");
        Label music = new Label("Music", font);
        final Label sound = new Label("Yes", font);

        no.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sound.setText("No");
                SoundHandler.turnOff();
            }
        });

        yes.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sound.setText("Yes");
                SoundHandler.turnOn();
            }
        });

        home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new ConnectScreen(game));
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        rank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new RankScreen(game));
                game.setScreen(Escape.ScreenKey.RANK);
            }
        });

        Table menu = new Table();
        menu.left();
        menu.add(home);
        menu.add(rank);
        menu.add(setting);

        Table panel = new Table();
        panel.left();
        panel.add(yes);
        panel.add(no);

        Table root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);
        root.top();
        root.add(music).expandX().padTop(300).row();
        root.add(sound).padTop(30).row();
        root.add(panel).padTop(50).row();
        root.add(menu).expand().bottom();

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        skin.dispose();
    }
}
