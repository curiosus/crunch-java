package me.curiosus.games.crunch;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Bullet {

    private Vector2 worldDimension;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 dimension;
    private float mass;
    private Vector2 target;
    private Vector2 frc;


    public Bullet(Vector2 startPosition, Vector3 targetV3) {
        position = startPosition;
        target = new Vector2(targetV3.x, targetV3.y);
        position = new Vector2(startPosition.x, startPosition.y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        mass = 10f;
        frc = new Vector2(20, 20);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.ellipse(position.x, position.y, 10, 10);
    }

    public void update() {
        applyForce(frc);
        target = target.sub(position.x, position.y);
        target.nor();
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.setZero();
    }

    public void applyForce(Vector2 force) {
        //f=ma
        //a = f/m
        force.x = force.x / mass;
        force.y = force.y / mass;
        acceleration.add(force);

    }


}
