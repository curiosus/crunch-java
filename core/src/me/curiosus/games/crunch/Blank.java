package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;

import java.util.List;

public class Blank {



    private Body body;
    private List<Body> walls;
    private List<Vector2> destinations;
    private Direction currentDirection;
    private Vector2 currentDestination;
    private float speed;


    public Blank(Body body, List<Body> walls, List<Vector2> destinations) {
        this.body = body;
        this.walls = walls;
        this.destinations = destinations;
        body.setUserData("blank");
        currentDirection = Direction.WEST;
        currentDestination = destinations.get(0);
        speed = 80f;
        this.body.setUserData("blank");

    }


    public void update() {
        float diffX = Math.abs(body.getPosition().x - currentDestination.x);

        if (diffX < 10.0f) {
            float diffY = Math.abs(body.getPosition().y - currentDestination.y);
            if (diffY < 10.0f) {
                System.out.println("Found it");
                currentDestination = nextDestination(currentDestination);
            } else if (body.getPosition().y - currentDestination.y > 0) {
                currentDirection = Direction.NORTH;
            } else if (body.getPosition().y - currentDestination.y < 0) {
                currentDirection = Direction.SOUTH;
            }
        } else if (body.getPosition().x - currentDestination.x > 0) {
            currentDirection = Direction.WEST;
        } else if (body.getPosition().x - currentDestination.x < 0) {
            currentDirection = Direction.EAST;
        }

        RayCastCallback callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getBody().getUserData().equals("wall")) {
                    if (currentDirection == Direction.WEST) {
                        if (body.getPosition().y < (GameScreen.WORLD_HEIGHT / 2.0f)) {
                            currentDirection = Direction.SOUTH;
                        } else {
                            currentDirection = Direction.NORTH;
                        }
                    } else if (currentDirection == Direction.EAST) {
                        if (body.getPosition().y < (GameScreen.WORLD_HEIGHT / 2.0f)) {
                            currentDirection = Direction.SOUTH;
                        } else {
                            currentDirection = Direction.NORTH;
                        }
                    } else if (currentDirection == Direction.NORTH) {
                        if (body.getPosition().x < (GameScreen.WORLD_WIDTH / 2.0f)) {
                            currentDirection = Direction.EAST;
                        } else {
                            currentDirection = Direction.WEST;
                        }
                    } else if (currentDirection == Direction.SOUTH) {
                        if (body.getPosition().x < (GameScreen.WORLD_WIDTH / 2.0f)) {
                            currentDirection = Direction.EAST;
                        } else {
                            currentDirection = Direction.WEST;
                        }
                    }
                }
                return 0.0f;
            }
        };

        Vector2 rayCastLine;
        if (currentDirection == Direction.WEST) {
            rayCastLine = new Vector2(body.getPosition().x - 128, body.getPosition().y);
        } else if (currentDirection == Direction.EAST) {
            rayCastLine = new Vector2(body.getPosition().x + 128, body.getPosition().y);
        } else if (currentDirection == Direction.NORTH) {
            rayCastLine = new Vector2(body.getPosition().x, body.getPosition().y - 128);
        } else if (currentDirection == Direction.SOUTH) {
            rayCastLine = new Vector2(body.getPosition().x, body.getPosition().y + 128);
        } else {
            rayCastLine = new Vector2(body.getPosition());
        }

        body.getWorld().rayCast(callback, body.getPosition(), rayCastLine);

        float rot;
        if (currentDirection == Direction.NORTH) {
            rot = (float) -Math.PI / 2.0f;
        } else if (currentDirection == Direction.SOUTH) {
            rot = (float) (Math.PI / 2.0f);
        } else if (currentDirection == Direction.WEST) {
            rot = (float) Math.PI;
        } else {
            rot = 0.0f; //Note this handles the EAST case as well as something unexpected.
        }

//        sprite.setRotation(float) Math.toDegrees(rot); TODO use when adding sprites.

        Vector2 linearVelocity;

        if (currentDirection == Direction.WEST) {
            linearVelocity = new Vector2(-1 * speed, 0);
        } else if (currentDirection == Direction.EAST) {
            linearVelocity = new Vector2(1 * speed, 0);
        } else if (currentDirection == Direction.NORTH) {
            linearVelocity = new Vector2(0, -1 * speed);
        } else if (currentDirection == Direction.SOUTH) {
            linearVelocity = new Vector2(0, 1 * speed);
        } else {
            linearVelocity = new Vector2(0, 0);
        }


        body.setLinearVelocity(linearVelocity);

    }

    public void draw(SpriteBatch batch) {

    }

    private Vector2 nextDestination(Vector2 currentDest) {

        int idx = destinations.indexOf(currentDest);
        idx++;
        if (idx >= destinations.size()) {
            idx = 0;
        }
        return destinations.get(idx);
    }
}
