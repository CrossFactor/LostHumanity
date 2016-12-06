package game.util;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class Songs {
	public static Music bgm;

	public static void menuBgm() throws SlickException {
		bgm = new Music("sounds/one/mainMenuMusic.ogg");
		bgm.loop();
	}

	public static void playBgm() throws SlickException {
		bgm = new Music("sounds/one/bgm1.ogg");
		bgm.loop();
	}

	public static void battleBgm() throws SlickException {
		bgm = new Music("sounds/one/battle.ogg");
		bgm.loop();
	}

	public static void pauseBgm() throws SlickException {
		bgm = new Music("sounds/one/pauseMusic.ogg");
		bgm.loop();
	}

	public static void gameOverBgm() throws SlickException {
		bgm = new Music("sounds/one/gameOverMusic.ogg");
		bgm.play();
	}

	public static void creditsBgm() throws SlickException {
		bgm = new Music("sounds/one/creditsMusic.ogg");
		bgm.loop();
	}

	public static void playSceneOneBgm() throws SlickException {
		bgm = new Music("sounds/one/sceneOneBgm.ogg");
		bgm.play();
	}

	public static void stop() {
		bgm.stop();
	}

	public static void pause() {
		bgm.pause();
	}

	public static void resume() {
		bgm.resume();
	}

	public static void victoryBgm() throws SlickException {
		bgm = new Music("sounds/one/victoryMusic.ogg");
		bgm.play();
	}

}
