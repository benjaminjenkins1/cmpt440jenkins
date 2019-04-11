package com.bjenkins.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Con


import com.badlogic.gdx.utils.TimeUtils;
import com.bjenkins.world.WorldManager;


public class npc_project extends ApplicationAdapter {


	static final float TIME_STEP = 1.0f / 60.0f;
	static final int VELOCITY_ITERATIONS = 8;
	static final int POSITION_ITERATIONS = 3;

	private float accumulator = 0;
	private float currentTime = 0;

	private WorldManager worldManager;

	private SpriteBatch batch;
	//private TextureAtlas textureAtlas;
	//private Animation<AtlasRegion> rotateUpAnimation;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	private double accumulator;
	private double currentTime;

	@Override
	public void create () {

		Box2D.init();

		worldManager = new WorldManager(0,0);

		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera(1280, 720);

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w, h);
		camera.zoom = 0.375f;
		camera.update();

		tiledMap = new TmxMapLoader().load("GameMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));

		AtlasRegion[] rotateUpFrames = new AtlasRegion[10];
		rotateUpFrames[0] = textureAtlas.findRegion("0001");
		rotateUpFrames[1] = textureAtlas.findRegion("0002");
		rotateUpFrames[2] = textureAtlas.findRegion("0003");
		rotateUpFrames[3] = textureAtlas.findRegion("0004");
		rotateUpFrames[4] = textureAtlas.findRegion("0005");
		rotateUpFrames[5] = textureAtlas.findRegion("0006");
		rotateUpFrames[6] = textureAtlas.findRegion("0007");
		rotateUpFrames[7] = textureAtlas.findRegion("0008");
		rotateUpFrames[8] = textureAtlas.findRegion("0009");
		rotateUpFrames[9] = textureAtlas.findRegion("0010");
		rotateUpAnimation = new Animation(0.1f, rotateUpFrames);

		Gdx.input.setInputProcessor(worldManager.gameInputAdapter);
	}

	@Override
	public void render () {

		double newTime = TimeUtils.millis() / 1000.0;
		double frameTime = Math.min(newTime	- currentTime, 0.25);
		float deltaTime = (float)frameTime;
		currentTime = newTime;
		while(accumulator >= TIME_STEP) {
			worldManager.world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;
			worldManager.update();
		}
		worldManager.interpolate(accumulator / step);

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		//batch.draw(rotateUpAnimation.getKeyFrame(elapsedTime, true), posX, posY);
		batch.end();

		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
	}
	
	@Override
	public void dispose () {
		worldManager.world.dispose();
		batch.dispose();
		textureAtlas.dispose();
	}

}

