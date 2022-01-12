package com.mygdx.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.Escape;

public class TiledObjectUtil {
    public static void parseTiledObjectLayer(World world, MapObjects objects) {
        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolygonMapObject) {
               shape = createPolygonShape((PolygonMapObject) object);
            } else {
                continue;
            }

            BodyDef bodyDef = new BodyDef();
            Body body = world.createBody(bodyDef);
            bodyDef.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(bodyDef);
            Fixture fixture  = body.createFixture(shape, 1.0f);
            fixture.setFriction(0);
            shape.dispose();
        }
    }


    public static ChainShape createPolygonShape(PolygonMapObject polygon) {
        float[] vertices = polygon.getPolygon().getTransformedVertices();

        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            worldVertices[i] = new Vector2(vertices[i * 2] / Escape.PPM, vertices[i * 2 + 1] / Escape.PPM);

        }

        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);

        return cs;
    }

}
