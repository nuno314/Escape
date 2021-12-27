package com.mygdx.screens;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;

public class RoomScreen implements Screen {

    private final Escape game;
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private String roomID;
    private static String player1;
    private static String player2;
    private Boolean p1Joined;
    private Boolean p2Joined;
    private Label p1Status;
    private Label p2Status;
    private final Label.LabelStyle font;
    private Table root;

    public RoomScreen(Escape game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        SpriteBatch batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        p1Joined = FALSE;
        p2Joined = FALSE;
        font = new Label.LabelStyle(new BitmapFont(), Color.PURPLE);

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Button back = new Button(skin, "home_off");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);
        root.add(back).expand().bottom();

        stage.addActor(root);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player1 != null && !p1Joined) {
            p1Status = new Label(player1, font);
            p1Status.setVisible(TRUE);
            p1Joined = TRUE;
        }
        if (player2 != null) {
            p2Status = new Label(player2, font);
            p2Status.setVisible(TRUE);
            p2Joined = TRUE;
        }
        root.add(p1Status).padTop(150).left();
        root.add(p2Status).padTop(150).right();
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

    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public static void setPlayer1(String player) {
        player1 = player;
    }

    public static void setPlayer2(String player) {
        player2 = player;
    }
}
