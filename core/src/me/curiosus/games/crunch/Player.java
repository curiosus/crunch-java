package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Player {

    private Body body;
    private Vector2 dimension;
    private Vector2 velocity;
    private float speed;

    private Gun gun;

    private Direction currentDirection;
    private PlayerInputProcessor mouseProcessor;
    private Vector3 clickPoint;

    private OrthographicCamera camera;

    public Player(Body body, Vector2 dimension, OrthographicCamera camera) {
        this.camera = camera;
        this.body = body;
        this.dimension = dimension;
        velocity = new Vector2(0, 0);
        speed = 100f;
        this.body.setUserData("player");
        currentDirection = Direction.EAST;
        mouseProcessor = new PlayerInputProcessor(camera);
//        Gdx.input.setInputProcessor(mouseProcessor);
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getDimension() {
        return dimension;
    }


    public void update() {

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(),  Gdx.input.getY(), 0);
            clickPoint = camera.unproject(touchPos);





//            int x = mouseProcessor.getX();
//            int y =  mouseProcessor.getY();
//            Vector3 touchPos = new Vector3(x - 32f, y - 32f, 0);
//            clickPoint = camera.unproject(touchPos);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.y = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.y = -speed;
        } else {
            velocity.y = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.x = speed;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.x = -speed;
        } else {
            velocity.x = 0;
        }

        //TODO Expand on this to include 360 degrees (2PI Radians) instead of just 4our directions.
        if (Math.abs(velocity.x) > (Math.abs(velocity.y))) {
            if (velocity.x > 0) {
                currentDirection = Direction.EAST;
            } else if (velocity.x < 0) {
                currentDirection = Direction.WEST;
            }
        } else if (Math.abs(velocity.y) > (Math.abs(velocity.x))) {
            if (velocity.y > 0) {
                currentDirection = Direction.SOUTH;
            } else if (velocity.y < 0) {
                currentDirection = Direction.NORTH;
            }
        }

        float rot = rotation(currentDirection);
        body.setTransform(body.getPosition(), rot);
        body.setLinearVelocity(velocity);

        System.out.println("player at " + body.getPosition());

    }

    public void draw(SpriteBatch batch) {

    }

    public Body getBody() {
        return body;
    }

    public Gun getGun() {
        return gun;
    }

    public void addGun(Gun g) {
        gun = g;
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 1, new Vector2(body.getPosition().x / 4, body.getPosition().y / 24), 0);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    private float rotation(Direction dir) {
        float rot;
        if (dir == Direction.NORTH) {
            rot = (float) -Math.PI / 2.0f;
        } else if (dir == Direction.SOUTH) {
            rot = (float) (Math.PI / 2.0f);
        } else if (currentDirection == Direction.WEST) {
            rot = (float) Math.PI;
        } else {
            rot = 0.0f; //Note this handles the EAST case as well as something unexpected.
        }
        return rot;
    }

    public Vector3 getClickPoint() {
        return clickPoint;
    }

}
