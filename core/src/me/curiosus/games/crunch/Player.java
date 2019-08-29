package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {

    private Body body;
    private Vector2 dimension;
    private Vector2 velocity;
    private float speed;

    public Player(Body body, Vector2 dimension) {
        this.body = body;
        this.dimension = dimension;
        velocity = new Vector2(0, 0);
        speed = 100f;
        this.body.setUserData("player");
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public Vector2 getDimension() {
       return dimension;
    }


    public void update() {

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
            velocity.x  = 0;
        }

        body.setLinearVelocity(velocity);

    }

    public void draw(SpriteBatch batch) {

    }
}
