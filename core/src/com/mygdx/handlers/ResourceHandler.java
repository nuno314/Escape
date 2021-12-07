package com.mygdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceHandler {


    public final static ResourceHandler INSTANCE = new ResourceHandler();

//    Texture walkSheet = new Texture(Gdx.files.internal("steven2.png"));
//
//    TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth()/9, walkSheet.getHeight());
//
//    TextureRegion[] walkFrames = new TextureRegion[4];
//    int index = 0;
//    for (int i = 0; i < 1;i++) {
//        for (int j = 0; j <  4; j++) {
//            walkFrames[index++] = tmp[i][j];
//        }
//    }

    public final static TextureAtlas atlas1 = new TextureAtlas("data/steven.atlas");
    public final static TextureAtlas atlas2 = new TextureAtlas("data/steven2.atlas");
    public final static Animation<TextureRegion> left1 = new Animation(0.333f, atlas1.findRegion("left"));
    public final static Animation<TextureRegion> right1 = new Animation(0.1f, atlas1.findRegion("right"));
    public final static TextureRegion stand1 = new TextureRegion(atlas1.findRegion("stand"));

    public final static Animation<TextureRegion> left2 = new Animation(0.333f, atlas2.findRegion("left"));
    public final static Animation<TextureRegion> right2 = new Animation(0.1f, atlas2.findRegion("right"));
    public final static TextureRegion stand2 = new TextureRegion(atlas2.findRegion("stand"));

}
