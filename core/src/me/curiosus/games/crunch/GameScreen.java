package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends ScreenAdapter {

    public static final float WORLD_WIDTH = 1024f;
    public static final float WORLD_HEIGHT = 768f;

    private TiledMap map;
    private OrthographicCamera camera;
    private Viewport viewport;
    private List<Wall> walls;
    private Player player;
    private List<Blank> blanks;
    private List<Vector2> path;
    private List<Vector2> path2;

    private List<Bullet> bullets;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;


    @Override
    public void show() {
        map = new TmxMapLoader().load("core/assets/map.tmx");
        camera = new OrthographicCamera();
        viewport = new FillViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();
        Gdx.input.setInputProcessor(new CameraInput(camera));
        walls = new ArrayList<>();
        spriteBatch = new SpriteBatch();

        path = new ArrayList<>();
        path2 = new ArrayList<>();

        parseMapForObjects();
        bullets = new ArrayList<>();
        player = new Player(new Vector2(128f, 128f), new Vector2(64f, 64f), camera, walls);
        Gun gun = new Gun(player.getPosition(), bullets);
        player.addGun(gun);
        shapeRenderer = new ShapeRenderer();
        spriteBatch.setProjectionMatrix(camera.combined);


        blanks = new ArrayList<>();
        Vector2 blankStartingPosition = path.get(0);
        Blank blank = new Blank(blankStartingPosition, new Vector2(64, 64), walls, path, player, camera);
        blanks.add(blank);


        Vector2 blankStartingPosition2 = path2.get(0);
        Blank blank2 = new Blank(blankStartingPosition2, new Vector2(64, 64), walls, path2, player, camera);
        blanks.add(blank2);

        camera.update();

    }

    @Override
    public void render(float delta) {
        update();
        clearScreen();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.begin();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);

        drawWalls();

        for (Blank blank : blanks) {
            blank.draw(shapeRenderer);
        }


        shapeRenderer.end();

        spriteBatch.begin();

        spriteBatch.setProjectionMatrix(camera.combined);
        for (Bullet bullet : bullets) {
            bullet.draw(spriteBatch);
        }

        for (Blank blank : blanks) {
            blank.draw(spriteBatch);
        }


        player.draw(spriteBatch);
        spriteBatch.end();

    }

    private void drawWalls() {
        for (Wall wall : walls) {
            shapeRenderer.setColor(Color.PURPLE);
            shapeRenderer.rect(wall.getPosition().x, wall.getPosition().y, wall.getDimension().x, wall.getDimension().y);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    @Override
    public void dispose() {
        map.dispose();
    }

    private void update() {
        Vector3 positionOfCamera = camera.position;
        positionOfCamera.x = player.getPosition().x;
        positionOfCamera.y = player.getPosition().y;
        camera.position.set(positionOfCamera);
        camera.update();
        for (Bullet bullet : bullets) {
            bullet.update();
        }
        for (Blank blank : blanks) {
            blank.update();
        }


        player.update();
    }

    private Wall createWall(int x, int y, int width, int height) {
        Wall wall = new Wall(new Vector2(x, y), new Vector2(width, height));
        return wall;
    }

    private void parseMapForObjects() {
        MapObjects mapObjects = map.getLayers().get("collision-layer").getObjects();
        for (MapObject mapObject : mapObjects) {
            if (mapObject.getProperties().containsKey("wall")) {
                RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
                Rectangle rectangle = rectangleMapObject.getRectangle();
                float x = rectangle.x;
                float y = rectangle.y;
                Wall wall = createWall((int) x, (int) y, (int) rectangle.width, (int) rectangle.height);
                walls.add(wall);
            }

        }


        createPointLayer("point-layer", path);
        createPointLayer("point-layer2", path2);

    }

    private void createPointLayer(String pointLayer, List<Vector2> pth) {
        MapObjects mapObjects = map.getLayers().get(pointLayer).getObjects();
        for (MapObject mapObject : mapObjects) {
            RectangleMapObject rectangleMapObject = (RectangleMapObject) mapObject;
            Vector2 position = new Vector2();
            position.x = rectangleMapObject.getRectangle().getX();
            position.y = rectangleMapObject.getRectangle().getY();
            pth.add(position);
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
