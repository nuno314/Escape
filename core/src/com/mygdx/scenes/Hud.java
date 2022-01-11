package com.mygdx.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.handlers.EventHandler;
import com.mygdx.Escape;

public class Hud {
    public static Stage stage;
    public static Viewport viewport;

    private static Integer worldTimer;
    private static float timeCount;
    private static Integer score=0;
    private static SpriteBatch sb;

    private static Label countDownLabel;
    private static Label scoreLabel;
    private static Label timeLabel;
    private static Label levelLabel;
    private static Label worldLabel;
    private static Label stevenLabel;
    private final Integer LIMIT_TIME = 60;
    public static int level=1;
    private final int MAX_LEVEL = 10;

    public Hud(SpriteBatch sb){
        worldTimer = LIMIT_TIME;
        timeCount = 0;
        this.sb = sb;
        viewport = new FitViewport(Escape.WIDTH, Escape.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, this.sb);

//        Table table = new Table();
//        table.top();
//        table.setFillParent(true);
//
//        countDownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        timeLabel= new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        levelLabel = new Label(""+level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        worldLabel= new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        stevenLabel = new Label("STEVEN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
//        table.add(stevenLabel).expandX().padTop(10);
//        table.add(worldLabel).expandX().padTop(10);
//        table.add(timeLabel).expandX().padTop(10);
//
//        table.row();
//        table.add(scoreLabel).expandX();
//        table.add(levelLabel).expandX();
//        table.add(countDownLabel).expandX();
//
//        stage.addActor(table);
    }
    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countDownLabel.setText(String.format("%03d",worldTimer));
            timeCount = 0;
        }
    }
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d",score));
    }

    public static void reset() {
        worldTimer = 60;
        timeCount = 0;

        stage.dispose();
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel= new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(""+level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel= new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stevenLabel = new Label(EventHandler.name, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(stevenLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);
    }

    public Integer getWorldTimer() {
        return worldTimer;
    }

    public static Integer getScore(){
        return score;
    }

    public static void setScore(int x){
        score=x;
    }

    public static void  minusTime(int value){
        worldTimer -= value;
    }

    public boolean isLastLevel() {
        return level==MAX_LEVEL;
    }
}
