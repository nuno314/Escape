package com.mygdx.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.screens.PlayScreen;
import com.mygdx.sprites.Steven;

public abstract class Interactive {
    protected World world;
    protected TiledMap map;
    protected Body body;
    protected MapObject object;
    //protected Shape shape;
    protected Fixture fixture;
    protected Rectangle bounds;
    // protected Shape shape;

    public Interactive(PlayScreen srreen, MapObject object){
        this.world= srreen.getWorld();
        this.map= srreen.getMap();
        this.object=object;

        Shape shape = null;
        if (object instanceof PolygonMapObject) {
            shape = TiledObjectUtil.createPolygonShape((PolygonMapObject) object);

        }

        FixtureDef fdef=new FixtureDef();
        BodyDef bdef=new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        //bdef.position.set()
        body = world.createBody(bdef);


        fdef.shape=shape;
        fixture=body.createFixture(fdef);
        //shape.dispose();


    }

    public abstract void OnFootHit(Steven steven);
    public abstract void OnBodyHit(Steven steven);
    public abstract void EndContact(Steven steven);

    public void setCategoryFilter(short filterBit){
        Filter filter= new Filter();
        filter.categoryBits=filterBit;
        fixture.setFilterData(filter);
    }
}
