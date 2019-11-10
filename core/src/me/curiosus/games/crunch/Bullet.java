package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import jdk.nashorn.internal.ir.annotations.Ignore;

public class Bullet {

    private Vector2 worldDimension;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 dimension;
    private float mass;
    private Vector2 target;
    private Vector2 force;
    private Sprite sprite;


    public Bullet(Vector2 startPosition, Vector3 targetV3) {
        target = new Vector2(targetV3.x, targetV3.y);
        position = new Vector2(startPosition.x, startPosition.y);
        sprite = new Sprite(new Texture(Gdx.files.internal("core/assets/bullet.png")));
        sprite.setSize(10, 10);

        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 0);
        mass = 10f;
        force = new Vector2(20, 4);

        target.sub(position);
        target.nor();
        target.scl(.5f);
        acceleration = target;
    }

    @Ignore
    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.ellipse(position.x, position.y, 10, 10);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }

    public void update() {
        velocity.add(acceleration);
        position.add(velocity);
    }



}
