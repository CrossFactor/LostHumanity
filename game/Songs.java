package game;

import org.newdawn.slick.Music;

public class Songs {
	public static Music playBgm;

	public Songs() {
		try {
			//playBgm = new Music("sounds/bgm1.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
