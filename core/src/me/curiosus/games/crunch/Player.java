package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class Player {

    private Vector2 position;
    private Vector2 dimension;
    private Vector2 velocity;
    public Vector2 previousPosition;
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
        previousPosition = new Vector2(position.x, position.y);
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

        previousPosition.x = position.x;
        previousPosition.y = position.y;

        if (!isCollision()) {
            position.x += velocity.x * speed;
            position.y += velocity.y * speed;
        }



    }

    private boolean isCollision() {
        for (Wall wall : walls) {
            Rectangle rectangle = new Rectangle(wall.getPosition().x, wall.getPosition().y, wall.getDimension().x, wall.getDimension().y);
            float xPos = position.x;
            float yPos = position.y;

            if (currentDirection.equals(Direction.EAST)) {
                xPos += dimension.x;
            } else if (currentDirection.equals(Direction.WEST)) {
                xPos -= dimension.x;
            }

            if (currentDirection.equals(Direction.NORTH)) {
                yPos -= dimension.y;
            } else if (currentDirection.equals(Direction.SOUTH)) {
                yPos += dimension.y;
            }


            Rectangle playerRectangle = new Rectangle(xPos, yPos, dimension.x, dimension.y);

            if (rectangle.contains(playerRectangle)) {
                System.out.println(rectangle);
                System.out.println(playerRectangle);
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
