package me.curiosus.games.crunch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class Gun {

    private Vector2 position;
    private Vector2 dimension;
    private Vector2 verticalDimension;
    private Vector2 horizontalDimension;
    private List<Bullet> bullets;


    public Gun(Vector2 pos, List<Bullet> bullets) {
        position = pos;
        this.bullets = bullets;
//        verticalDimension = new Vector2(2, 8);
        horizontalDimension = new Vector2(8, 2);
        dimension = horizontalDimension;

    }

    public Vector2 getDimension() {
        return dimension;
    }



    public void fire(Vector3 target) {
        Bullet bullet = new Bullet(position, target);
        bullets.add(bullet);
    }
}
