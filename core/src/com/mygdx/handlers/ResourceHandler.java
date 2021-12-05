package com.mygdx.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ResourceHandler {

    public final static ResourceHandler INSTANCE = new ResourceHandler();

    public final static TextureAtlas atlas = new TextureAtlas("data/steven.atlas");

    public final static Animation<TextureRegion> left1 = new Animation(0.333f, atlas.findRegion("left"));
    public final static Animation<TextureRegion> right1 = new Animation(0.1f, atlas.findRegion("right"));
    public final static TextureRegion stand1 = new TextureRegion(atlas.findRegion("stand"));
}
