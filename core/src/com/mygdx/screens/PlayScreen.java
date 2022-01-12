package com.mygdx.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.Escape;
import com.mygdx.handlers.EventHandler;
import com.mygdx.handlers.InputHandler;
import com.mygdx.handlers.SoundHandler;
import com.mygdx.scenes.Hud;
import com.mygdx.sprites.Ground;
import com.mygdx.sprites.OutDoor;
import com.mygdx.sprites.Trap;
import com.mygdx.utils.WorldContactListener;
import com.mygdx.sprites.Steven;

import com.mygdx.handlers.B2WorldHandler;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.emitter.Emitter;

public class PlayScreen implements Screen {

    private static World world;

    private static Hud hud;

    private final Escape game;

    // Basic playscreen variables
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Tiled map variables
    private final TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Escape variables

    private Box2DDebugRenderer b2dr;
    private B2WorldHandler worldHandler;

    // Sprites
    private SpriteBatch batch;
    private Steven player;

    private final String[] pathMapGame={"", "data/map_1.tmx","data/map_2.tmx",
                                            "data/map_3.tmx","data/map_4.tmx"
                                            ,"data/map_5.tmx","data/map_6.tmx","data/map_7.tmx"
                                            ,"data/map_8.tmx","data/map_9.tmx","data/map_10.tmx"};

    int currentLevel;
    private Music music;

    // Touchpad
    private Stage stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    String check;
    private float x1, x2, y1, y2;


    public PlayScreen(Escape game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        viewport = new FitViewport(Escape.WIDTH / Escape.PPM, Escape.HEIGHT / Escape.PPM, camera);

        mapLoader = new TmxMapLoader();
        currentLevel = Hud.level;

        map = mapLoader.load(pathMapGame[currentLevel]);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / com.mygdx.Escape.PPM);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

//        world = new World(new Vector2(0, -10), false);

//        b2dr = new Box2DDebugRenderer();
//        worldHandler = new B2WorldHandler(this);

        //add for collision
//        for (MapObject object: map.getLayers().get("Traps").getObjects()){
//            new Trap(this, object);
//        }
//
//        for (MapObject object: map.getLayers().get("Door").getObjects()){
//            new OutDoor(this, object);
//        }
//        for (MapObject object: map.getLayers().get("Walls").getObjects()){
//            new Ground(this, object);
//        }

        batch = new SpriteBatch();
        //add for collision
//        world.setContactListener(new WorldContactListener());

        hud = new Hud(batch);

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("data/touchBackground3.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(15, touchpadStyle);
        //setBounds(x,y,width,height)
        touchpad.setPosition(10, 2000);
        touchpad.setBounds(15, 15, 100, 100);

        //Create a Stage and add TouchPad

    }


    @Override
    public void show() {
        stage = new Stage();
        Hud.reset();
        currentLevel = Hud.level;
        map = mapLoader.load(pathMapGame[currentLevel]);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / com.mygdx.Escape.PPM);

        world = new World(new Vector2(0, -10), false);
        b2dr = new Box2DDebugRenderer();
        worldHandler = new B2WorldHandler(this);

        for (MapObject object: map.getLayers().get("Traps").getObjects()){
            new Trap(this, object);
        }

        for (MapObject object: map.getLayers().get("Door").getObjects()){
            new OutDoor(this, object);
        }
        for (MapObject object: map.getLayers().get("Walls").getObjects()){
            new Ground(this, object);
        }

        stage.addActor(touchpad);
        world.setContactListener(new WorldContactListener());
        Gdx.input.setInputProcessor(stage);

        player = new Steven(EventHandler.name);
    }

    public void update(float dt) {

        world.step(1f/ 60f, 6, 2);
        player.update(dt);
        Gdx.app.log("COORD x: ", String.valueOf(player.getX()));
        Gdx.app.log("COORD y: ", String.valueOf(player.getY()));

        try {
            JSONObject coord = new JSONObject();
            coord.put("roomID", EventHandler.roomID);
            coord.put("x", player.getX());
            coord.put("y", player.getY());
            if (EventHandler.isPlayer1)
                EventHandler.socket.emit("update_p1_coord", coord);
            else
                EventHandler.socket.emit("update_p2_coord", coord);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (EventHandler.isPlayer1)
            EventHandler.socket.on("update_p2_coord", onUpdateP2Coord);

        InputHandler.inputUpdateTouchpad(player, dt, touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
        player.updateTouchpad(dt);
        hud.update(dt);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();
        renderer.setView(camera);

        if(player.isPassed)
            player.currentState = Steven.State.PASS;
        if( hud.getWorldTimer() <= 0 || isOutOfMap())
            player.currentState = Steven.State.DEAD;
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // B2 World
        renderer.render();
        b2dr.render(world, camera.combined);
        b2dr.setDrawBodies(false);

        // Player
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        player.draw(batch);
        batch.end();

        // Hud
        batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        // Touchpad
        stage.act(Gdx.graphics.getDeltaTime());
        touchpad.setPosition(515, 15);
        touchpad.setSize(180, 200);
        stage.draw();
        check=player.isGround+"";

        // Event
        if (gameOver()){
            game.setScreen(Escape.ScreenKey.GAME_OVER);
//            dispose();
        }
        else if(isFinish()){
            game.setScreen(new FinishGameScreen(game,Hud.getScore(),hud.getWorldTimer()));
//            dispose();
        }
        else if(isPassLevel()){
            game.setScreen(new LevelPassScreen(game,Hud.getScore(),hud.getWorldTimer()));
//            dispose();
        }
        else{
            player.currentState= Steven.State.STANDING;
        }
    }

    public boolean gameOver(){
        return player.currentState == Steven.State.DEAD;
    }

    public boolean isPassLevel(){
        return player.isPassed;
    }
    public boolean isFinish(){
        return hud.isLastLevel() && player.isPassed;
    }

    public boolean isOutOfMap(){
        return  player.player.getPosition().x <0 || player.player.getPosition().x >5.053051;
    }
    public boolean onGround(){return player.isOnGround;}
    public boolean onTrap(){return player.isCollied;}

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false);
        viewport.update(width, height);
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
        map.dispose();
        renderer.dispose();
        stage.dispose();
//        world.dispose();
//        b2dr.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        music.stop();
    }

    public static World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    private final Emitter.Listener onUpdateP2Coord = new Emitter.Listener() {

        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                x2 = (float) data.get("x");
                y2 = (float) data.get("y");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

