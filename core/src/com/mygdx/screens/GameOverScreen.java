package com.mygdx.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;
import com.mygdx.scenes.Hud;

public class GameOverScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Escape game;

    private Skin skin;
    private Table table;
    private Label.LabelStyle font, font2;


    public GameOverScreen(final Escape game){

        this.game  = game;
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, new OrthographicCamera());

        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));


        font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        font2 = new Label.LabelStyle(new BitmapFont(), Color.RED);



//        stage.addActor(table);
    }
    @Override
    public void show() {
        stage = new Stage(viewport);
        Escape.manager.get("audio/sounds/GameOver.mp3", Sound.class).play();
        table = new Table();
        table.center();
        table.setFillParent(true);

        Label gameOverLabel = new Label("GAME OVER !!!!", font2);
        gameOverLabel.setFontScale(3);
        Label playAgainLabel = new Label("Click to Play Again", font);
        playAgainLabel.setFontScale(2);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f).row();
        Button home = new Button(skin, "home_off");
        Button play = new Button(skin, "start");

        home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(Escape.ScreenKey.CONNECT);
            }
        });

        play.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(Escape.ScreenKey.PLAY);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        table.add(play).padTop(500).row();
        table.add(home).bottom().expand();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }
}
