package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Box2D;
import com.mygdx.handlers.B2WorldHandler;
import com.mygdx.sprites.Steven;
import com.mygdx.screens.GameOverScreen;

import javax.swing.Box;

public class PlayScreen implements Screen {

    public static final PlayScreen INSTANCE = new PlayScreen(Box2D.getInstance());

    //Reference to our Game, used to set Screens
    private final Box2D game;
    private final TextureAtlas atlas;


    // Basic playscreen variables
    private final OrthographicCamera camera;
    private final Viewport viewport;

    // Tiled map variables
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    // Box2D variables
    private final World world;
    private final Box2DDebugRenderer b2dr;
    private final B2WorldHandler worldHandler;

    // Sprites
    private SpriteBatch batch;
    private Steven player;

    public PlayScreen(Box2D game) {
        this.game = game;
        atlas = new TextureAtlas("data/steven.atlas");

        camera = new OrthographicCamera();

        viewport = new FitViewport(Box2D.WIDTH / Box2D.PPM, Box2D.HEIGHT / Box2D.PPM, camera);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("data/Escape.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Box2D.PPM);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        world = new World(new Vector2(0, -10), false);

        b2dr = new Box2DDebugRenderer();

        worldHandler = new B2WorldHandler(this);

        player = new Steven(this, "NUNO");

        batch = new SpriteBatch();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void update(float dt) {

        world.step(1f/ 60f, 6, 2);

        player.update(dt);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();
        renderer.setView(camera);

        if(player.player.getPosition().y >= 850 / Box2D.PPM && player.player.getPosition().x >= 430 / Box2D.PPM)
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

        b2dr.setDrawBodies(false);

        if(gameOver()){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
    }

    public boolean gameOver(){
        return player.currentState == Steven.State.DEAD ;
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
    }

    public World getWorld() {
        return world;
    }

    public TiledMap getMap() {
        return map;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}

