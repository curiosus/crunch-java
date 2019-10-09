package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Bullet {

    private Vector2 startPosition;
    private Vector3 target;
    private Vector2 velocity;
    private Vector2 position;
    private Vector2 acceleration;

    public Bullet(Vector2 startPosition, Vector3 target) {
        this.startPosition = startPosition;
        this.target = target;
        position = new Vector2(startPosition.x, startPosition.y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.ellipse(position.x, position.y, 10, 10);

    }

    public void update() {
        target = target.sub(position.x, position.y, 0);
        acceleration.add(target.x / 1000, target.y / 1000);
        velocity.add(acceleration);
        position.add(velocity);


    }


}
