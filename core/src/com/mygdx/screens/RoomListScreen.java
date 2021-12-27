package com.mygdx.screens;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.Game;
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

public class RoomListScreen implements Screen {

    private final Escape game;
    private final Stage stage;
    private final Skin skin;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Array<RoomItem> roomList;

    public RoomListScreen(Escape game) {
        this.game = game;
        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        SpriteBatch batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        roomList = new Array<>();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Gdx.app.log("FIND BUTTON", "CLICKED");

        EventHandler.socket.on("room_list", onRoomList);

        Button back = new Button(skin, "home_off");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        Table root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);
        root.add(back).expand().bottom();

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

    private final Emitter.Listener onRoomList = new Emitter.Listener() {
        final Array<RoomItem> roomList = new Array<>();

        @Override
        public void call(Object... args) {
            JSONArray data = (JSONArray) args[0];

            try {
                for (int i = 0; i < data.length(); i++) {
                    JSONObject room = data.getJSONObject(i);
                    String roomID = room.getString("roomID");
                    String p1ID = room.getString("p1ID");
                    String p2ID = room.getString("p2ID");
                    String p1Name = room.getString("p1Name");
                    String p2Name = room.getString("p2Name");
                    RoomItem newRoom = new RoomItem(roomID,p1Name, p2Name, p1ID, p2ID);
                    roomList.add(newRoom);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

}
