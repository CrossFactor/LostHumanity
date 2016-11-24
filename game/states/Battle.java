package game.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.characters.heroes.*;
import game.characters.monsters.Monster;
import game.characters.monsters.Teru;

public class Battle extends BasicGameState {
	Hero hero = Slayer.slayer;
	Image battleMap;
	Music music;
	Vector<Monster> enemies;

	public Battle(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		
		int enemyCount = new Random().nextInt(2) + 1;
		
		enemies = new Vector<Monster>();
		
		for (int i = 0; i < enemyCount; i++) {
			addRandomEnemy();
		}
		
		hero.battleFaceRight();
		hero.reset();
		// sound.play();
		music.loop();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music = new Music("sounds/one/back3.wav");
		battleMap = new Image("res/backgrounds800x600/2.png");
		hero.battleFaceRight();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		battleMap.draw(0, 0);
		hero.render(gc, sbg, g);
//		for (Monster m : enemies){
//			m.render(gc, sbg, g);
//		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		hero.update(gc, sbg, delta);
//		for (Monster m : enemies){
//			m.update(gc, sbg, delta);
//		}
	}

	private void addRandomEnemy() throws SlickException {
		int enemyType = new Random().nextInt(1);
		Monster m = (enemyType == 0) ? new Teru() : new Teru();
		m.setMonsterSheets();
		enemies.add(m);
	}

	public int getID() {
		return 2;
	}
}
