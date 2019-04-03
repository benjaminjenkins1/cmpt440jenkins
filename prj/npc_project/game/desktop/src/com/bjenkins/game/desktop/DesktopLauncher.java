package com.bjenkins.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bjenkins.game.npc_project;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "npc_project";
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new npc_project(), config);
	}
}
