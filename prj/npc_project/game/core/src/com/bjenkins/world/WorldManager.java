package com.bjenkins.world;

import com.badlogic.gdx.physics.box2d.World;


import com.bjenkins.input.GameInputAdapter;

public class WorldManager {

    public GameInputAdapter gameInputAdapter;
    public World world;
    public GameObject[] gameObjects;

    public WorldManager(float gravX, float gravY) {
        this.world = new World(new Vector2(gravX, gravY), true);

    }

    public update() {

    }

    public interpolate(float alpha) {
        for(GameObject gameObject : gameObjects) {
            GameObject.step();
        }
    }






}
