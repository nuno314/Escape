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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.client.utils.TiledObjectUtil;

public class Box2D extends Game {
	private boolean DEBUG = false;
	private final float SCALE = 1;

	private Viewport viewport;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private Box2DDebugRenderer b2dr;
	private World world;
	private Body player, platform;

	private SpriteBatch batch;
	private Texture tex;

	@Override
	public void create() {
		float WIDTH = 576;
		float HEIGHT = 1056;

		camera = new OrthographicCamera(WIDTH, HEIGHT);

		viewport = new FitViewport(WIDTH, HEIGHT, camera);

		world = new World(new Vector2(0f, -15f), true);
		b2dr = new Box2DDebugRenderer();

		player = createBox(80, 120, 25, 55, false);

		batch = new SpriteBatch();
		tex = new Texture("data/ninja1.png");

		map = new TmxMapLoader().load("data/Escape.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get("Walls").getObjects());
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();
		batch.begin();
		batch.draw(tex, player.getPosition().x * PPM - (tex.getWidth() / 2),player.getPosition().y * PPM - (tex.getHeight() / 2));
		batch.end();

		b2dr.render(world, camera.combined);
		b2dr.setDrawBodies(false);
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false);
		viewport.update(width, height);
	}

	@Override
	public void dispose() {
		world.dispose();
		b2dr.dispose();
		batch.dispose();
	}

	private void update(float delta) {
		world.step(1 / 60f, 6, 2);

		inputUpdate(delta);
		cameraUpdate(delta);
		renderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);
	}

	public void inputUpdate(float delta) {
		int horizontalForce = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			horizontalForce -= 1;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			horizontalForce += 1;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {

				player.applyForceToCenter(0,700, true);
		}

		player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);
	}
	public void cameraUpdate(float delta) {
//		Vector3 position = camera.position;
//		position.x = player.getPosition().x * PPM;
//		position.y = player.getPosition().y * PPM;

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
	}

	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef def = new BodyDef();
		if (isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x / PPM, y / PPM);
		def.fixedRotation = true;
		pBody = world.createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return pBody;
	}

	public World getWorld() {
		return world	;
	}
}
