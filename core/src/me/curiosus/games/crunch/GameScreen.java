package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    private static final float WORLD_WIDTH = 1024f;
    private static final float WORLD_HEIGHT = 768;

    private TiledMap map;
    private OrthographicCamera camera;
    private Box2DDebugRenderer box2DDebugRenderer;
    private World world;
    private Viewport viewport;
    private List<Body> bodies;
    private Player player;


    @Override
    public void show() {
        map = new TmxMapLoader().load("core/assets/map.tmx");
        camera = new OrthographicCamera();
        viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        world = new World(new Vector2(0, 0), true);
        box2DDebugRenderer = new Box2DDebugRenderer();
        bodies = new ArrayList<>();
        parseMapForObjects();
        player = new Player(createBody(128, 128, 16, 16, false), new Vector2(16, 16));
    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        box2DDebugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void dispose() {
        box2DDebugRenderer.dispose();
        world.dispose();
        map.dispose();
    }

    private void update() {
        world.step(1.f / 60f, 6, 2);
        player.update();
        Vector3 positionOfCamera = camera.position;
        positionOfCamera.x = player.getPosition().x;
        positionOfCamera.y = player.getPosition().y;
        camera.position.set(positionOfCamera);
        camera.update();
    }

    private Body createBody(int x, int y, int width, int height, boolean isStatic) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        bodyDef.fixedRotation = true;
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        if (!isStatic) {
            fixtureDef.restitution = 1.5f;
            fixtureDef.density = .05f;
            fixtureDef.friction = 10f;
        }
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    private void parseMapForObjects() {
        MapObjects mapObjects = map.getLayers().get("collision-layer").getObjects();
        for (MapObject mapObject : mapObjects) {
            if (mapObject.getProperties().containsKey("wall")) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleMapObject.getRectangle();
                float x = (rectangle.x + rectangle.width / 2);
                float y = (rectangle.y + rectangle.height / 2);
                Body body = createBody((int) x, (int) y, (int) rectangle.width / 2, (int) rectangle.height / 2, true);
                body.setUserData("wall");
                bodies.add(body);
            }
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
