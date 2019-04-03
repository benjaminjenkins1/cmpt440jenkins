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

public class npc_project extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Animation<AtlasRegion> rotateUpAnimation;
	private float elapsedTime = 0;
	private float posX, posY;
	private OrthographicCamera camera;
	
	@Override
	public void create () {
		camera = new OrthographicCamera(1280, 720);
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

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			public boolean keyDown(int keyCode) {
				float moveAmount = 1.0f;
				if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) moveAmount = 10.0f;

				// movement
				if(keyCode == Keys.LEFT)  posX -= moveAmount;
				if(keyCode == Keys.RIGHT) posX += moveAmount;
				if(keyCode == Keys.DOWN)    posY -= moveAmount;
				if(keyCode == Keys.UP)  posY += moveAmount;


				// camera
				if(keyCode == Keys.W) camera.translate(0,  moveAmount);
				if(keyCode == Keys.S) camera.translate(0, -moveAmount);
				if(keyCode == Keys.A) camera.translate(-moveAmount, 0);
				if(keyCode == Keys.D) camera.translate( moveAmount, 0);

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
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		elapsedTime += Gdx.graphics.getDeltaTime();
		batch.draw(rotateUpAnimation.getKeyFrame(elapsedTime, true), posX, posY);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		textureAtlas.dispose();
	}
}
