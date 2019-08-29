package me.curiosus.games.crunch;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraInput extends InputAdapter {

    private OrthographicCamera camera;


    public CameraInput(OrthographicCamera cam) {
        this.camera = cam;
    }

    @Override
    public boolean scrolled(int amount) {
        camera.zoom += amount * .2f;
        return true;
    }
}
