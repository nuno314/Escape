package com.mygdx.game.client.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.client.Box2D;
import com.mygdx.game.client.screens.PlayScreen;

public class Hud {
    public Stage stage;
    public Viewport viewport;

    private Integer worldTimer;
    private  float timeCount;
    private  static Integer score=0;

    private Label countDownLabel;
    private static Label scoreLabel;
    private Label timeLabel;
    private Label levelLabel;
    private Label worldLabel;
    private Label stevenLabel;
    private  final Integer LIMIT_TIME = 60;
    public static int level=1;
    private final int MAX_LEVEL = 10;

    public Hud(SpriteBatch sb){
        worldTimer = LIMIT_TIME;
        timeCount = 0;

        viewport = new FitViewport(com.mygdx.game.client.Box2D.WIDTH, Box2D.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countDownLabel = new Label(String.format("%03d",worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d",score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel= new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(""+level, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel= new Label("LEVEL", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stevenLabel = new Label("STEVEN", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        table.add(stevenLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);

        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countDownLabel).expandX();

        stage.addActor(table);


    }
    public void update(float dt){
        timeCount+=dt;
        if(timeCount>=1){
            worldTimer--;
            countDownLabel.setText(String.format("%03d",worldTimer));
            timeCount=0;
        }


    }
    public static void addScore(int value){
        score+=value;
        scoreLabel.setText(String.format("%06d",score));
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

    public boolean isLastLevel() {
        return level==MAX_LEVEL;
    }
}
