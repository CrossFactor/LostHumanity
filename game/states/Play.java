package game.states;

import java.io.Serializable;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Game;
import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;
import game.util.Debug;
import game.util.Songs;

public class Play extends BasicGameState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image[] map;
	Image[] mapWalls;
	Image[] mapTrans;
	Image[] mapSpawn;
	Image blankFloor;
	static Boolean initMap = true;
	int direction = 1;
	static int level = 1;
	static int scene = 0;
	float mapX = 0;
	float mapY = 0;
	float shiftX = 0;
	float shiftY = 0;
	float heroX = 0;
	float heroY = 0;
	Hero hero = Slayer.slayer;

	public static void nextLevel() {
		level++;
		initMap = true;
	}

	public static void nextScene() {
		scene++;
	}

	public Play(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hero.faceDown();
		Songs.playBgm();
		hero.faceDown();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {// tiledmap
		if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		} else if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		} else if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		}
		mapX = hero.getxPosOut() + 400;
		mapY = hero.getyPosOut() + 300;
		map = new Image[] { new Image("res/backgrounds/levels/level1/floor.png"),
				new Image("res/backgrounds/levels/level2/floor.png"),
				new Image("res/backgrounds/levels/level3/floor.png"),
				new Image("res/backgrounds/levels/level4/floor.png") };
		mapWalls = new Image[] { new Image("res/backgrounds/levels/level1/wall.png"),
				new Image("res/backgrounds/levels/level2/wall.png"),
				new Image("res/backgrounds/levels/level3/wall.png"),
				new Image("res/backgrounds/levels/level4/wall.png") };
		mapTrans = new Image[] { new Image("res/backgrounds/levels/level1/transition.png"),
				new Image("res/backgrounds/levels/level2/transition.png"),
				new Image("res/backgrounds/levels/level3/transition.png"),
				new Image("res/backgrounds/levels/level4/transition.png") };
		mapSpawn = new Image[] { new Image("res/backgrounds/levels/level1/spawn.png"),
				new Image("res/backgrounds/levels/level2/spawn.png"),
				new Image("res/backgrounds/levels/level3/spawn.png"),
				new Image("res/backgrounds/levels/level4/spawn.png") };
		hero.faceDown();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map[level].draw(mapX, mapY);
		mapWalls[level].draw(mapX, mapY);
		hero.getAnimation().draw(heroX, heroY);
		if (Debug.debugMode == true) {
			g.drawString("X:" + hero.getxPosOut() + "\nY:" + hero.getyPosOut(), 10, 100);
			g.drawString("mapX:" + mapX + "\nmapY:" + mapY, 10, 200);
			g.drawString("Direction: " + direction, 10, 150);
			g.drawString("MouseX:" + Mouse.getX() + "\nMouseY:" + (600 - Mouse.getY()), 10, 250);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		heroX = 400 - (hero.getWidthOut() / 2);
		heroY = 300 - (hero.getHeightOut() / 2);
		mapX = -(hero.getxPosOut())- (hero.getWidthOut() / 2) + 400;
		mapY = -(hero.getyPosOut()) - (hero.getHeightOut() / 2) + 300;
		hero.getAnimation().update(delta);
		hero.moveOut(gc, sbg, delta, input, mapWalls[level], mapTrans[level]);
		findBattle(sbg);
		mapLogic();                           
		if (Debug.debugMode) {
			if (input.isKeyDown(Input.KEY_B)) {
				sbg.enterState(4);
			}
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(Game.pause);
		}
	}

	public void findBattle(StateBasedGame sbg) throws SlickException {
		if (hero.findBattle()) {
			Songs.battleBgm();
			sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 500));
		}
	}

	public void mapLogic() {
		if (initMap) {
			initMaps();
		}

	}

	public void initMaps() {
		mapX = 0;
		mapY = 0;
		for (int y = 0; y <= 600; y++) {
			for (int x = 0; x <= 800; x++) { // Change this once we implement
												// spawns other than (0, 0)
												// coordinates for map
				Color c = mapSpawn[level].getColor(x, y);
				Boolean spawnPointHere = c.a != 0f;
				if (spawnPointHere) {
					hero.setxPosOut(x - 20);
					hero.setyPosOut(y - 70);
				}
			}
		}
		initMap = false;
	}

	public int getID() {
		return 3;
	}
}
