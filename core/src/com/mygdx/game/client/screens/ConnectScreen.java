package com.mygdx.game.client.screens;

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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.client.Box2D;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.client.handlers.LabelHandler;
import com.mygdx.game.client.network.ConnectStateListener;

public class ConnectScreen implements Screen {
    public static final ConnectScreen INSTANCE = new ConnectScreen();
    private final Stage stage;
    private final Table root;

    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    private OrthographicCamera camera;
    private Viewport viewport;

    private final TextField ipAddressLabel;
    private final TextField portLabel;
    private final TextField usernameLabel;

    private final TextButton connectButton;

    private final Label errorLabel;

    public ConnectScreen() {


        this.stage = new Stage();
        this.stage.getViewport().setCamera(Box2D.getInstance().getCamera());

        this.root = new Table();
        this.root.setBounds(0, 0, 800, 600);

        final Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        this.ipAddressLabel = new TextField("IP-Address", skin);
        this.portLabel = new TextField("Port", skin);
        this.usernameLabel = new TextField("Username", skin);

        this.connectButton = new TextButton("Connect", skin);
        this.connectButton.addListener(new ClickListener() {
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

               final Client client = new Client();

               client.addListener(new ConnectStateListener());

//               try {
//                   client.start();
//                   client.connect(15000, ipAddressLabel.getText(), Integer.parseInt((portLabel.getText())));
//               } catch (Exception e){
//                   errorLabel.setText(e.getMessage());
//                   return super.touchDown(event, x, y, pointer, button);
//               }

               Box2D.getInstance().setClient(client);

               return super.touchDown(event, x, y, pointer, button);
           }
        });

        this.errorLabel = LabelHandler.INSTANCE.createLabel(null, 16, Color.RED);
        this.stage.addActor(this.root);

        this.setToDefault();
    }

    public void setToDefault() {
        this.root.clear();
        this.root.add(this.ipAddressLabel).width(250).row();
        this.root.add(this.portLabel).width(250).padTop(25).row();
        this.root.add(this.usernameLabel).width(250).padTop(50).row();
        this.root.add(this.connectButton).size(250,50).padTop(100).row();
        this.root.add(this.errorLabel).padTop(50);
        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Box2D.WIDTH, Box2D.HEIGHT, camera);

        viewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
        Gdx.input.setInputProcessor(stage);

        Button play = new Button(skin, "play");
        Button create = new Button(skin, "create");
        Button find = new Button(skin, "find");
        Button home = new Button(skin, "home");
        Button rank = new Button(skin, "rank_off");
        Button setting = new Button(skin, "setting_off");

        play.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayScreen(Box2D.getInstance()));
            }
        });

        rank.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new RankScreen());
            }
        });

        setting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new SettingScreen());
            }
        });

        Table menu = new Table();
        menu.left();
        menu.add(home);
        menu.add(rank);
        menu.add(setting);

        Table root = new Table();
        root.setBackground(skin.getDrawable("background"));
        root.setFillParent(true);
        root.top();
        root.add(play).padTop(100).row();
        root.add(create).padTop(100).row();
        root.add(find).padTop(100).row();
        root.add(menu).expand().bottom();

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
        dispose();
    }

    @Override
    public void dispose() {
        skin.dispose();
    }

    public Label getErrorLabel() {
        return errorLabel;
    }
}

