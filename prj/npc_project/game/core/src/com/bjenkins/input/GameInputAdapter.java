package com.bjenkins.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GameInputAdapter extends InputAdapter{

    @Override
    public boolean keyDown(int keyCode) {
        float moveAmount = 1.0f;
        if(Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) moveAmount = 10.0f;

        // movement
        if(keyCode == Input.Keys.LEFT)  posX -= moveAmount;
        if(keyCode == Input.Keys.RIGHT) posX += moveAmount;
        if(keyCode == Input.Keys.DOWN)  posY -= moveAmount;
        if(keyCode == Input.Keys.UP)    posY += moveAmount;


        // camera
        if(keyCode == Input.Keys.W) camera.translate(0,  moveAmount);
        if(keyCode == Input.Keys.S) camera.translate(0, -moveAmount);
        if(keyCode == Input.Keys.A) camera.translate(-moveAmount, 0);
        if(keyCode == Input.Keys.D) camera.translate( moveAmount, 0);

        camera.update();

        return true;
    }
    @Override
    public boolean scrolled(int amount) {
        float zoomAmount = (float) amount / 2;
        Gdx.app.log("CameraZoom", String.format("%f", camera.zoom));
        if(camera.zoom + zoomAmount >= 1 && camera.zoom + zoomAmount <= 5) camera.zoom += zoomAmount;
        camera.update();
        return true;
    }

}
