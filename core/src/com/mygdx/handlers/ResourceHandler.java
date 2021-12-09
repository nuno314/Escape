package com.mygdx.handlers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.Escape;

public class ResourceHandler {


    private final static ResourceHandler INSTANCE = new ResourceHandler();

    public static TextureAtlas atlas1, atlas2;

    public static TextureRegion stand1, stand2;

    public static Animation<TextureRegion> left1, left2, right1, right2;

    public ResourceHandler() {
        atlas1 = Escape.manager.get("data/steven.atlas");
        atlas2 = Escape.manager.get("data/steven2.atlas");

        stand1 = new TextureRegion(atlas1.findRegion("stand"));
        stand2 = new TextureRegion(atlas1.findRegion("stand"));

        left1 = new Animation(0.333f, atlas1.findRegion("left"));
        right1 = new Animation(0.1f, atlas1.findRegion("right"));
        stand1 = new TextureRegion(atlas1.findRegion("stand"));

        left2 = new Animation(0.333f, atlas2.findRegion("left"));
        right2 = new Animation(0.1f, atlas2.findRegion("right"));
        stand2 = new TextureRegion(atlas2.findRegion("stand"));
    }

    public static ResourceHandler getINSTANCE() {
        return INSTANCE;
    }
}
