package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.Escape;
import com.mygdx.scenes.Hud;
import com.mygdx.handlers.EventHandler;

import org.json.JSONObject;

public class LevelPassScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Escape game;
    private SpriteBatch batch;
    private Skin skin;


    public LevelPassScreen(Escape game, Integer point, Integer time) {

        this.game = game;
        batch = new SpriteBatch();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(), Color.PURPLE);

        Table table = new Table();
        table.setFillParent(true);
        Label gameOverLabel = new Label("CONGRATULATION!!!!", font2);
        gameOverLabel.setFontScale(3);
        Label continueLabel = new Label("Click to CONTINUE", font2);
        Label score = new Label("Current Score: " + point.toString(), font);
        Label bonus = new Label("Remaining time: " + time.toString(), font);
        int finalScore = point + time * 10;
        Label totalScore = new Label("TOTAL SCORE: " + finalScore, font);
        totalScore.setFontScale(2);
        Hud.addScore(finalScore);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(score).expandX().padTop(10f);
        table.row();
        table.add(bonus).expandX().padTop(10f);
        table.row();
        table.add(totalScore).expandX().padTop(10f);
        table.row();
        table.add(continueLabel).expandX().padTop(10f);
        stage.addActor(table);
        table.setBackground(skin.getDrawable("background"));

    }


    @Override
    public void show() {
        Escape.manager.get("audio/sounds/PassLevel.mp3", Sound.class).play();

//        JSONObject levelPass = new JSONObject();
//
//        EventHandler.socket.emit("level_pass", )
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Hud.level++;
            game.setScreen(Escape.ScreenKey.PLAY);
            dispose();
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}