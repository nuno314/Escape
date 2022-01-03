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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private Table root;
    private final Skin skin;
    private final Skin terraSkin;
    private SpriteBatch batch;

    private Array<RoomItem> roomList;

    public RoomListScreen(Escape game) {
        this.game = game;
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        root = new Table();

        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));
        terraSkin = new Skin(Gdx.files.internal("skin/terra/terra-mother-ui.json"));
    }

    @Override
    public void show()  {

        stage = new Stage(viewport, batch);
        if (roomList != null)
            renderRoomList(roomList);
        Gdx.input.setInputProcessor(stage);
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
        root.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private void addRoomItem(Table root, Array<RoomItem> list) {

        for (int i = 0; i < list.size; i++) {
            root.add(list.get(i)).pad(20).padTop(0);
            root.row();
        }
    }

    private void renderRoomList(Array<RoomItem> roomList)
    {

        Button back = new Button(skin, "home_off");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });



        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);

        Image image = new Image(terraSkin, "label-title");
        root.add(image).row();
        addRoomItem(root, roomList);

//        root.add(back).expand().bottom();
        root.add(back).row();
        stage.addActor(root);
    }

    public void setRoomList(Array<RoomItem> roomList) {
        this.roomList = roomList;
    }
}
