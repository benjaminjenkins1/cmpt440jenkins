package com.bjenkins.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import com.bjenkins.world.WorldManager;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class npc_project extends ApplicationAdapter {

	private WorldManager worldManager;

	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation<AtlasRegion> rotateUpAnimation;
	private float elapsedTime = 0;
	private float posX, posY;
	private OrthographicCamera camera;
	private Box2DDebugRenderer debugRenderer;
	private TiledMap tiledMap;
	private TiledMapRenderer tiledMapRenderer;

	@Override
	public void create () {

		worldManager = new WorldManager();

		world = new World(new Vector2(0,0), true);
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
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();
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

