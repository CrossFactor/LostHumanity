package game;

import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Input;

import game.characters.GameCharacter;

public class Attack implements Runnable {

	Animation b;
	GameCharacter c;

	public Attack(GameCharacter c) {
		this.c = c;
	}

	public void run() {
	}

}
