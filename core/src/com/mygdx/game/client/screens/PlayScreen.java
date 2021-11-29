package com.mygdx.game.client.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.handlers.B2WorldHandler;
import com.mygdx.game.client.sprites.Steven;

public class PlayScreen implements Screen {
    //Reference to our Game, used to set Screens
    private Box2D game;
    private TextureAtlas atlas;

    // Basic playscreen variables
    private OrthographicCamera camera;
    private Viewport viewport;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2D variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private B2WorldHandler worldHandler;

    // Sprites
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

        player = new Steven(this);
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

        if(player.player.getPosition().y >= 850 && player.player.getPosition().x >= 430)
            player.currentState = Steven.State.DEAD;
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

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
}
