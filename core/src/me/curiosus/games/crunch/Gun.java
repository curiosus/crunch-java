package me.curiosus.games.crunch;

import com.badlogic.gdx.math.Vector2;

public class Gun {

    private Vector2 position;
    private Vector2 dimension;
    private Vector2 verticalDimension;
    private Vector2 horizontalDimension;


    public Gun() {
        verticalDimension = new Vector2(2, 8);
        horizontalDimension = new Vector2(8, 2);
        dimension = horizontalDimension;

    }

    public Vector2 getDimension() {
        return dimension;
    }


    public void update(Direction direction) {
        if (direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
            dimension = verticalDimension;
        } else {
            dimension = horizontalDimension;
        }
    }
}
