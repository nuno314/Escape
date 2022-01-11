package com.mygdx.screens;

import static java.lang.Boolean.FALSE;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;
import com.mygdx.handlers.EventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ConnectScreen implements Screen {

    private Socket socket;

    private final Escape game;

    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    Button how_to_play;
    Button create;
    Button find;
    Button play_now;

    Button home;
    Button rank;
    Button setting;


    public String playerName;

    public ConnectScreen(final Escape game) {

        this.game = game;

        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        SpriteBatch batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        EventHandler.connectSocket();
        EventHandler.configSocketEvents();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        final TextArea name = new TextArea(playerName, skin);

        how_to_play = new Button(skin, "how_to_play");
        create = new Button(skin, "create");
        find = new Button(skin, "find");
        play_now = new Button(skin, "play");

        home = new Button(skin, "home");
        rank = new Button(skin, "rank_off");
        setting = new Button(skin, "setting_off");

        how_to_play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new HowToPlayScreen());
                game.setScreen(Escape.ScreenKey.HOW_TO_PLAY);
            }
        });

        play_now.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(game));
                game.setScreen(Escape.ScreenKey.PLAY);
            }
        });

        create.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject player = new JSONObject();
                            player.put("ID", EventHandler.id);
                            player.put("name", EventHandler.name);

                            EventHandler.socket.emit("create_room", player);

                            Gdx.app.log("New room, player 1: ", EventHandler.name);
                            EventHandler.isPlayer1 = true;
                            game.setScreen(Escape.ScreenKey.NEW_ROOM);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        find.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            game.setScreen(Escape.ScreenKey.LOADING);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        rank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.RANK);
            }
        });

        setting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.SETTING);
            }
        });

        Table menu = new Table();
        menu.left();
        menu.add(home);
        menu.add(rank);
        menu.add(setting);

        Table root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);
        root.top();
        root.add(name).padTop(150).row();
        root.add(how_to_play).padTop(150).row();
        root.add(play_now).padTop(50).row();
        root.add(create).padTop(50).row();
        root.add(find).padTop(50).row();
        root.add(menu).expand().bottom();

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
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

    public void setPlayer(String player) {
        this.playerName = player;
    }
}
