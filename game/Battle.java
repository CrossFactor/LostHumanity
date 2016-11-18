package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.characters.heroes.*;

import java.util.Random;

public class Battle extends BasicGameState {
	Hero hero = Slayer.slayer;
	Image battleMap;
	float playerPositionX = 400;
	float playerPositionY = 400;
	private int direction;
	Music music;

	public Battle(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		// sound.play();
		music.loop();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music = new Music("sounds/back3.wav");
		battleMap = new Image("res/backgrounds800x600/2.png");
		hero.battleFaceRight();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		battleMap.draw(0, 0);
		hero.getAnimation().draw(hero.getxPosBattle(), hero.getyPosBattle());
		g.drawString("Hero attacking: " + hero.isAttacking(), 50, 100);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		hero.getAnimation().update(delta);
		hero.moveBattle(gc, sbg, delta, input);
		if (input.isKeyDown(Input.KEY_M)) {
			sbg.enterState(1);
		}
	}

	public int getID() {
		return 2;
	}
}
