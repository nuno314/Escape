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
    private final Stage stage;
    private final OrthographicCamera camera;
    private final Viewport viewport;

    private final Array<RoomItem> roomList;
    private Table root;
    private final Skin skin;
    private final Skin terraSkin;

    private ScrollPane scrollPane;
    private Stack stack;

    private Table stickyHeader;

    public RoomListScreen(Escape game) {
        this.game = game;

        SpriteBatch batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

        roomList = new Array<>();

        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));
        terraSkin = new Skin(Gdx.files.internal("skin/terra/terra-mother-ui.json"));
    }

    @Override
    public void show()  {
        EventHandler.socket.on("room_list", onRoomList);

//        Gdx.app.log("FIND BUTTON", "CLICKED");
//        Gdx.app.log("XScreen", String.valueOf(viewport.getScreenWidth()));
//        Gdx.app.log("YScreen", String.valueOf(viewport.getScreenHeight()));
//        Gdx.app.log("XWorld", String.valueOf(viewport.getWorldWidth()));
//        Gdx.app.log("YWorld", String.valueOf(viewport.getWorldHeight()));
//
//        Button back = new Button(skin, "home_off");
//        back.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(Escape.ScreenKey.CONNECT);
//            }
//        });
//
//        root = new Table();
//        root.setBackground(skin.getDrawable("background"));
//        root.setFillParent(true);



//        Image image = new Image(terraSkin, "label-title");
//        root.add(image).row();

//        EventHandler.socket.on("room_list", onRoomList);

//        root.add(back).expand().bottom();
//        stage.addActor(root);
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
        stage.dispose();
    }

    private void addRoomItem(Table root, Array<RoomItem> list) {
        for (int i = 0; i < list.size; i++) {
            root.add(list.get(i)).pad(20).padTop(0);
            root.row();
        }
    }

    private final Emitter.Listener onRoomList = new Emitter.Listener() {

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
                    RoomItem newRoom = new RoomItem(roomID,p1Name, p2Name, p1ID, p2ID, terraSkin);
                    roomList.add(newRoom);
                }

                Gdx.app.log("ONROOMLIST", "DONE");
                for (int i = 0 ;i< roomList.size;i++) {
                    Gdx.app.log("ROOM",roomList.get(i).getRoomID());
                }

                renderRoomList();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private void renderRoomList()
    {

        Button back = new Button(skin, "home_off");
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        Button refresh = new Button(skin, "play");
        refresh.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                EventHandler.socket.emit("find_room");
            }
        });

        root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);

        Image image = new Image(terraSkin, "label-title");
        root.add(image).row();
        addRoomItem(root, roomList);

        root.add(back).expand().bottom();
        stage.addActor(root);
    }

}
