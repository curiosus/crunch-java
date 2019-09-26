package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Blank {

    private Vector2 position;
    private Vector2 dimension;

    private List<Wall> walls;
    private List<Vector2> path;
    private Direction currentDirection;
    private float speed;
    private Vector2 velocity;
    private Vector2 target;
    private int indexOfTarget;
    float maxDiff;


    public Blank(Vector2 position, Vector2 dimension, List<Wall> walls, List<Vector2> path) {
        this.position = position;
        this.dimension = dimension;
        this.walls = walls;
        this.path = path;
        currentDirection = Direction.WEST;
        speed = 1.8f;
        velocity = new Vector2();
        maxDiff = 10f;

    }


    public void update() {

        indexOfTarget = indexOfTarget < path.size() ? indexOfTarget : 0;
        target = path.get(indexOfTarget);

        if (targetAcquired(position, target)) {
            indexOfTarget++;
            indexOfTarget = indexOfTarget < path.size() ? indexOfTarget : 0;
            target = path.get(indexOfTarget);
        }

        if (position.x - target.x > 0f) {
            velocity.x = -1f;
        } else if (position.x - target.x < 0f) {
            velocity.x = 1f;
        } else {
            velocity.x = 0f;
        }

        if (position.y - target.y > 0f) {
            velocity.y = -1;
        } else if (position.y - target.y < 0f) {
            velocity.y = 1f;
        } else {
            velocity.y = 0f;
        }

//        System.out.println("Blank " + position.x + " " + position.y);
        position.x += velocity.x * speed;
        position.y += velocity.y * speed;


    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(position.x, position.y, dimension.x, dimension.y);
    }

    public void draw(SpriteBatch batch) {

    }

    private boolean targetAcquired(Vector2 blank, Vector2 target) {
        float xDiff = blank.x - target.x;
        float yDiff = blank.y - target.y;
        return (Math.abs(xDiff) <= maxDiff && Math.abs(yDiff) <= maxDiff);
    }


}
