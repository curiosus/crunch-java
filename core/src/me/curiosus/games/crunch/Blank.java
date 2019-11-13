package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
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
    private Vector2 destination;
    private int indexOfDestination;
    private float maxDiff;

    private Gun gun;
    private Player player;

    enum Behavior {
        PATROL,
        PURSUE
    }

    Behavior behavior = Behavior.PATROL;


    public Blank(Vector2 position, Vector2 dimension, List<Wall> walls, List<Vector2> path, Player player) {
        this.position = position;
        this.dimension = dimension;
        this.walls = walls;
        this.path = path;
        this.player = player;
        currentDirection = Direction.WEST;
        speed = 1.8f;
        velocity = new Vector2();
        maxDiff = 10f;

    }


    public void update() {

        behavior = searchForTarget();


        if (behavior.equals(Behavior.PATROL)) {

            indexOfDestination = indexOfDestination < path.size() ? indexOfDestination : 0;
            destination = path.get(indexOfDestination);

            if (destinationAcquired(position, destination)) {
                indexOfDestination++;
                indexOfDestination = indexOfDestination < path.size() ? indexOfDestination : 0;
                destination = path.get(indexOfDestination);
            }

            if (position.x - destination.x > 0f) {
                velocity.x = -1f;
            } else if (position.x - destination.x < 0f) {
                velocity.x = 1f;
            } else {
                velocity.x = 0f;
            }

            if (position.y - destination.y > 0f) {
                velocity.y = -1;
            } else if (position.y - destination.y < 0f) {
                velocity.y = 1f;
            } else {
                velocity.y = 0f;
            }

//        System.out.println("Blank " + position.x + " " + position.y);
            position.x += velocity.x * speed;
            position.y += velocity.y * speed;
        }

    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.PINK);
        shapeRenderer.rect(position.x, position.y, dimension.x, dimension.y);
    }

    public void draw(SpriteBatch batch) {

    }

    public void addGun(Gun g) {
        gun = g;
    }

    private boolean destinationAcquired(Vector2 blank, Vector2 destination) {
        float xDiff = blank.x - destination.x;
        float yDiff = blank.y - destination.y;
        return (Math.abs(xDiff) <= maxDiff && Math.abs(yDiff) <= maxDiff);
    }

    private Behavior searchForTarget() {
        Circle circle = new Circle(position.x, position.y, 200);
        if (circle.contains(player.getPosition())) {
            System.out.println("target acquired");
        } else {
            System.out.println("                              \n\n\n\n\n");
        }

        return Behavior.PATROL;
    }




}
