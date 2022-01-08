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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;
import com.mygdx.handlers.EventHandler;
import com.mygdx.utils.RoomItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class RoomScreen implements Screen {

    private final Escape game;
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private String roomID;
    private String p1Name;
    private String p2Name;
    private Boolean p1Joined;
    private Boolean p2Joined;
    private Label p1Status;
    private Label p2Status;
    private final Label.LabelStyle font;
    private Table root;

    private Button start;
    private Button back;

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

        start = new Button(skin, "play");

//        start.setDisabled(TRUE);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        back = new Button(skin, "home_off");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventHandler.socket.emit("leave_room", EventHandler.id);
                Gdx.app.log("LEAVE", "CLICKED");
                EventHandler.isPlayer1 = false;
                EventHandler.isPlayer2 = false;
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("START", "CLICKED");
            }
        });

        root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);

        if (EventHandler.isPlayer1) {
            renderPlayer1(EventHandler.name);
            EventHandler.socket.once("p2_join", onP2Join);
        }

        if (EventHandler.isPlayer2) {
            renderPlayer2(EventHandler.name);
            EventHandler.socket.once("p1_join", onP1Join);
        }

        root.add(start).expand().bottom().padBottom(150).row();
        root.add(back).bottom();
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

    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    private final Emitter.Listener onP2Join = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];

            try {
                p2Joined = true;
                p2Name = data.getString("p2Name");
                Gdx.app.log("P1 CLIENT, P2 JOIN", p2Name);
                renderPlayer2(p2Name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final Emitter.Listener onP1Join = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            Gdx.app.log("P2 CLIENT, P1 JOIN", p1Name);
            try {
                p1Joined = true;
                p1Name = data.getString("p1Name");
                Gdx.app.log("P2 CLIENT, P1 JOIN", p1Name);
                renderPlayer1(p1Name);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private final void renderPlayer1(String name) {
        Label nameLbl = new Label(name, font);
        root.add(nameLbl).pad(50);
    }

    private final void renderPlayer2(String name) {
        Gdx.app.log("RENDER", name);
        Label nameLbl = new Label(name, font);
        root.add(nameLbl).pad(50);
        p2Joined = true;
    }


}
