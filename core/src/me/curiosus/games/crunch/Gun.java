package me.curiosus.games.crunch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.List;

public class Gun {

    private Vector2 position;
    private Vector2 dimension;
    private Vector2 horizontalDimension;
    private List<Bullet> bullets;
    private long timeSinceLastFired;
    private final static long firingInterval = 1000L; //todo pros and cons of having this be a long vs. an int. See:  https://stackoverflow.com/questions/48783267/how-primitive-long-and-int-comparison-happens-in-java


    public Gun(Vector2 pos, List<Bullet> bullets) {
        position = pos;
        this.bullets = bullets;
        horizontalDimension = new Vector2(8, 2);
        dimension = horizontalDimension;
        timeSinceLastFired = 0L;

    }

    public void rotate(float angle) {
        position.rotate(angle);
    }

    public Vector2 getDimension() {
        return dimension;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void fire(Vector3 target) {
        System.out.println(timeSinceLastFired);
        if (firingInterval < System.currentTimeMillis() -  timeSinceLastFired) {
            Bullet bullet = new Bullet(position, target);
            timeSinceLastFired = System.currentTimeMillis(); //todo probably don't want all these system calls.
            bullets.add(bullet);

        }
    }
}
