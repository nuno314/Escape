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

public class FinishGameScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Escape game;
    private Hud hud;


    public  FinishGameScreen(Escape game, Integer point, Integer time) {

        this.game = game;
        SpriteBatch batch = new SpriteBatch();
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Skin skin = new Skin(Gdx.files.internal("skin/screen.json"), new TextureAtlas("skin/screen.pack"));

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.PURPLE);
        Label.LabelStyle font2 = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
//            table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("ESCAPE COMPLETE ", font);
        gameOverLabel.setFontScale(3);
        int finalScore = point + time * 10;
        Label totalScore = new Label("FINAL SCORE: " + finalScore, font2);
        totalScore.setFontScale(2);
        Hud.addScore(finalScore);

        table.add(gameOverLabel).expandX();
        table.row();
        table.row();
        table.add(totalScore).expandX().padTop(10f);
        stage.addActor(table);
        table.setBackground(skin.getDrawable("background"));

        Escape.manager.get("audio/sounds/FinishGame.mp3", Music.class).play();



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Hud.level = 1;
            Hud.setScore(0);
            game.setScreen(Escape.ScreenKey.CONNECT);
            Escape.manager.get("audio/sounds/FinishGame.mp3", Music.class).dispose();
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

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
