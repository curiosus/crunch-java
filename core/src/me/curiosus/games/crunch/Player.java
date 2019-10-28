package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    private Vector3 clickPoint;
    private OrthographicCamera camera;
    private List<Wall> walls;
    private float angle;
    private Sprite sprite;


    public Player(Vector2 position, Vector2 dimension, OrthographicCamera camera, List<Wall> walls) {
        this.camera = camera;
        this.dimension = dimension;
        this.position = position;
        this.walls = walls;
        velocity = new Vector2(0, 0);
        proposedPosition = new Vector2(0, 0);
        speed = 2f;
        sprite = new Sprite(new Texture(Gdx.files.internal("core/assets/player.png")));
        sprite.setSize(dimension.x, dimension.y);
        sprite.setPosition(position.x, position.y);

    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getDimension() {
        return dimension;
    }


    public void update() {

        float originX = sprite.getOriginX() + sprite.getX();
        float originY = sprite.getOriginY() + sprite.getY();
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        Vector3 thing = new Vector3(mouseX, mouseY, 0);
        camera.unproject(thing);
        angle = MathUtils.atan2(thing.y - originY, thing.x - originX) * MathUtils.radiansToDegrees;
        if (angle < 0) {
            angle += 360f;
        }
        angle *= -1;


        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            clickPoint = camera.unproject(touchPos);
            gun.fire(clickPoint);

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



        proposedPosition.x = position.x + velocity.x * speed;
        proposedPosition.y = position.y + velocity.y * speed;

        if (isCollision(proposedPosition)) {
            //do nothing
        } else {
            position.x += velocity.x * speed;
            position.y += velocity.y * speed;
        }

        sprite.setPosition(position.x, position.y);




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


    public void draw(SpriteBatch batch) {
        sprite.setRotation(angle);
        sprite.draw(batch);
    }

    public void addGun(Gun g) {
        gun = g;
    }


}
