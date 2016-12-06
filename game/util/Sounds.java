package game.util;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds {
	public static Sound sound;

	public static void selectSound() throws SlickException {
		sound = new Sound("sounds/one/selectMenu.wav");
		sound.play();
	}
	
	public static void bellSound() throws SlickException {
		sound = new Sound("sounds/two/Bell.wav.wav");
		sound.play();
	}
	
	public static void hitSound() throws SlickException {
		sound = new Sound("sounds/one/hit.wav");
		sound.play();
	}
	
	public static void blockSound() throws SlickException {
		sound = new Sound("sounds/one/blockSound.wav");
		sound.play();
	}
}
