package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class Player {

    private Vector2 position;
    private Vector2 dimension;
    private Vector2 velocity;
    private Vector2 proposedPosition;
    private float speed;
    private Gun gun;
    private Direction currentDirection;
    private Vector3 clickPoint;
    private OrthographicCamera camera;
    private List<Wall> walls;


    public Player(Vector2 position, Vector2 dimension, OrthographicCamera camera, List<Wall> walls) {
        this.camera = camera;
        this.dimension = dimension;
        this.position = position;
        this.walls = walls;
        velocity = new Vector2(0, 0);
        proposedPosition = new Vector2(0, 0);

        speed = 2f;
        currentDirection = Direction.EAST;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getDimension() {
        return dimension;
    }


    public void update() {


        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            clickPoint = camera.unproject(touchPos);
            System.out.println("Click " + clickPoint.x + " " + clickPoint.y);
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

        //TODO Expand on this to include 360 degrees (2PI Radians) instead of just 4our directions. Maybe.
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

        proposedPosition.x = position.x + velocity.x * speed;
        proposedPosition.y = position.y + velocity.y * speed;

        if (isCollision(proposedPosition)) {
            //do nothing
        } else {
            position.x += velocity.x * speed;
            position.y += velocity.y * speed;
        }



    }

    private boolean isCollision(Vector2 pos) {
        for (Wall wall : walls) {
            if (pos.x < wall.getPosition().x + wall.getDimension().x
                    && pos.x + dimension.x > wall.getPosition().x
                    && pos.y < wall.getPosition().y + wall.getDimension().y
                    && pos.y + dimension.y > wall.getPosition().y) {
                return true;
            }
        }

        return false;
    }


    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(position.x, position.y, dimension.x, dimension.y);
    }

    public void draw(SpriteBatch batch) {

    }


    public Gun getGun() {
        return gun;
    }

    public void addGun(Gun g) {
        gun = g;
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
