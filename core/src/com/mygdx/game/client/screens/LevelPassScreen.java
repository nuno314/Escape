package com.mygdx.game.client.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.scenes.Hud;

public class LevelPassScreen implements Screen {
    private Viewport viewport;
    private Stage stage;
    private Game game;
    private Hud hud;

    public LevelPassScreen(Game game, Integer point, Integer time) {

        this.game = game;
        viewport = new FitViewport(Box2D.WIDTH, Box2D.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((Box2D) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        Table table = new Table();
//            table.center();
        table.setFillParent(true);
        Label gameOverLabel = new Label("CONGRATULATION!!!!", font);
        Label continueLabel = new Label("Click to CONTINUE", font);
        Label score = new Label("Current Score: " + point.toString(), font);
        Label bonus = new Label("Remaining time: " + time.toString(), font);
        int finalScore = point + time * 10;
        Label totalScore = new Label("TOTAL SCORE: " + finalScore, font);
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

        Box2D.manager.get("audio/sounds/PassLevel.mp3", Sound.class).play();


    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            Hud.level++;
            game.setScreen(new PlayScreen((Box2D) game));
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
