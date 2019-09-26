package me.curiosus.games.crunch;

import com.badlogic.gdx.math.Vector2;

public class Wall {

    private Vector2 position;
    private Vector2 dimension;

    public Wall(Vector2 position, Vector2 dimension) {
        this.position = position;
        this.dimension = dimension;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getDimension() {
        return dimension;
    }
}
