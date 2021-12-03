package com.mygdx.game.client;

import static com.mygdx.game.client.utils.Constants.PPM;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.kryonet.Client;
import com.mygdx.game.client.screens.ConnectScreen;
import com.mygdx.game.client.screens.PlayScreen;
import com.mygdx.game.client.utils.TiledObjectUtil;
import com.mygdx.game.server.ServerFoundation;

public class Box2D extends Game {
	public static final float WIDTH = 576;
	public static final float HEIGHT = 1056;
	public static final float PPM = 100;
	private Viewport viewport;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;

	public SpriteBatch batch;
	private Texture tex;

	private Client client;

//	public  Bodydef finishDoor;
	@Override
	public void create() {
		ServerFoundation.main(null);

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false,800, 600);
		batch = new SpriteBatch();


//		setScreen(getClient);
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
	}

	public World getWorld() {
		return world	;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}
	public static Box2D getInstance() {
		return (Box2D) Gdx.app.getApplicationListener();
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
