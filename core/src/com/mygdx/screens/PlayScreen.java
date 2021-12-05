package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
<<<<<<< HEAD
import com.mygdx.sprites.Steven;
import com.mygdx.screens.GameOverScreen;

import javax.swing.Box;
import com.mygdx.game.client.handlers.B2WorldHandler;
import com.mygdx.game.client.scenes.Hud;
import com.mygdx.game.client.sprites.OutDoor;
import com.mygdx.game.client.sprites.Steven;
import com.mygdx.game.client.sprites.Trap;
import com.mygdx.game.client.utils.WorldContactListener;
=======
import com.mygdx.Escape;
import com.mygdx.handlers.B2WorldHandler;
import com.mygdx.sprites.Steven;
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc

public class PlayScreen implements Screen {
    private static final PlayScreen INSTANCE = new PlayScreen();

    private static World world;
    //Reference to our Game, used to set Screens
<<<<<<< HEAD
    private Box2D game;
    private TextureAtlas atlas;
    private static Hud hud;

=======
    private final Escape game;
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc

    // Basic playscreen variables
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Tiled map variables
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    // Escape variables

    private final Box2DDebugRenderer b2dr;
    private final B2WorldHandler worldHandler;

    // Sprites
    private SpriteBatch batch;
    private Steven player;

<<<<<<< HEAD
    private final String[] pathMapGame={"","data/Escape.tmx","data/map_2.tmx"};
    int currentLevel;
    private Music music;

    public PlayScreen(Box2D game) {
        this.game = game;
        atlas = new TextureAtlas("data/steven.atlas");
=======
    public PlayScreen() {
        this.game = Escape.getINSTANCE();
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc

        this.camera = game.getCamera();

        viewport = game.getViewport();

        mapLoader = new TmxMapLoader();
<<<<<<< HEAD
        currentLevel = Hud.level;
//          currentLevel = (Hud.level+1)%2 +1;

        map = mapLoader.load(pathMapGame[currentLevel]);

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Box2D.PPM);
=======
        map = mapLoader.load("data/Escape.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Escape.PPM);
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        world = new World(new Vector2(0, -10), false);

        b2dr = new Box2DDebugRenderer();
        worldHandler = new B2WorldHandler(this);

<<<<<<< HEAD
        //add for collision
        for (MapObject object: map.getLayers().get("Traps").getObjects()){
            new Trap(this,object);
        }

        for (MapObject object: map.getLayers().get("OutDoors").getObjects()){
            new OutDoor(this,object);
        }

        player = new Steven(this, "NUNO");

        batch = new SpriteBatch();
        //add for collision
        world.setContactListener(new WorldContactListener());

        hud = new Hud(game.batch);

//        Music for game
        music = Box2D.manager.get("audio/music/Escape_music.ogg", Music.class);
//      music = Box2D.manager.get("audio/sounds/Trap.mp3",Music.class);

        music.setLooping(true);
        music.setVolume(0.7f);
        music.play();

    }
=======
        player = new Steven("NUNO", 1);

        batch = new SpriteBatch();
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc


    }

    @Override
    public void show() {

    }

    public void update(float dt) {

        world.step(1f/ 60f, 6, 2);

        player.update(dt);
        hud.update(dt);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();
        renderer.setView(camera);

<<<<<<< HEAD
//        if(player.player.getPosition().y >= 850 / Box2D.PPM && player.player.getPosition().x >= 430 / Box2D.PPM)
//            player.currentState = Steven.State.PASS;
        if(player.isPassed)
            player.currentState = Steven.State.PASS;
        if(hud.getWorldTimer() <=0)
=======
        if(player.player.getPosition().y >= 850 / Escape.PPM && player.player.getPosition().x >= 430 / Escape.PPM)
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc
            player.currentState = Steven.State.DEAD;


    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        player.draw(batch);
        //player.update(delta);
        batch.end();

        game.batch.setProjectionMatrix(camera.combined);


        game.batch.begin();
        player.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        b2dr.setDrawBodies(false);

        if(gameOver()){
<<<<<<< HEAD

            game.setScreen(new GameOverScreen(game));
=======
            game.setScreen(new GameOverScreen());
>>>>>>> 476496ae517ad5fa600b389b08631266d3cb1fbc
            dispose();
        }
        if(isPassLevel()){

            game.setScreen(new LevelPassScreen(game,hud.getScore(),hud.getWorldTimer()));
            dispose();
        }

    }

    public boolean gameOver(){
        return player.currentState == Steven.State.DEAD;
    }

    public boolean isPassLevel(){
        return player.isPassed;
    }
    public boolean isFinish(){
        return player.currentState == Steven.State.FINISH ;
    }

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

    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        music.dispose();

    }

    public static World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public static PlayScreen getINSTANCE() {
        return INSTANCE;
    }


}

