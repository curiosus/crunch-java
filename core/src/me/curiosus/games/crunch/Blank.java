package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.List;

public class Blank {


    private Body body;
    private List<Body> walls;
    private List<Vector2> path;
    private Direction currentDirection;
    private float speed;
    private Vector2 velocity;
    private Vector2 target;
    private int indexOfTarget;
    float maxDiff;



    public Blank(Body body, List<Body> walls, List<Vector2> path) {
        this.body = body;
        this.walls = walls;
        this.path = path;
        body.setUserData("blank");
        currentDirection = Direction.WEST;
        speed = 180f;
        this.body.setUserData("blank");
        velocity = new Vector2();
        maxDiff = 10f;

    }



    public void update() {

        indexOfTarget = indexOfTarget < path.size() ? indexOfTarget : 0;
        target = path.get(indexOfTarget);

        if (targetAcquired(body.getPosition(), target)) {
            indexOfTarget++;
            indexOfTarget = indexOfTarget < path.size() ? indexOfTarget : 0;
            target = path.get(indexOfTarget);
        }

        if (body.getPosition().x - target.x > 0f) {
            velocity.x = -1f;
        } else if (body.getPosition().x - target.x < 0f) {
            velocity.x = 1f;
        } else {
            velocity.x = 0f;
        }

        if (body.getPosition().y - target.y > 0f) {
           velocity.y = -1;
        } else if (body.getPosition().y - target.y < 0f) {
           velocity.y = 1f;
        } else {
            velocity.y = 0f;
        }


        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);
        if (body.getUserData().equals("blank1")) {
//            System.out.println("Blank at " + body.getPosition().x + " " + body.getPosition().y);
        }

    }

    public void draw(SpriteBatch batch) {

    }

    private boolean targetAcquired(Vector2 blank, Vector2 target) {
        float xDiff = blank.x - target.x;
        float yDiff = blank.y - target.y;
        return (Math.abs(xDiff) <= maxDiff && Math.abs(yDiff) <= maxDiff);
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

    public Body getBody() {
        return body;
    }
}
