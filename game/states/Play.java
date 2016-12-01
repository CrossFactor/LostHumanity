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

import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;
import game.util.Debug;

public class Play extends BasicGameState implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image worldMap;
	Image worldMapWalls;
	Image map1, map2, map3, map4;
	Image map1Walls, map2Walls, map3Walls, map4Walls;
	Image blankFloor;
	boolean quit = false;
	int direction = 1;
	int area = 1;
	int stage = 1;
	int mapX = 0;
	int mapY = 0;
	String job = "slayer";
	Hero hero = Slayer.slayer;
	Music music;

	public Play(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hero.faceDown();
		// sound.play();
		music.loop();
		hero.faceDown();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
		quit = false;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {// tiledmap
		music = new Music("sounds/one/bgm1.ogg");
		if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		} else if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		} else if (hero instanceof Slayer) {
			((Slayer) hero).setHeroSprites();
		}
		map2 = new Image("res/backgrounds/levels/level2/floor.png");
		map2Walls = new Image("res/backgrounds/levels/level2/wall.png");
		blankFloor = new Image("res/backgrounds/blankTrans.png");
		worldMap = map2;
		worldMapWalls = map2Walls;
		hero.faceDown();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		worldMap.draw(mapX, mapY);
		worldMapWalls.draw(mapX, mapY);
		hero.getAnimation().draw(hero.getxPosOut(), hero.getyPosOut());
		if (Debug.debugMode == true) {
			g.drawString("X:" + hero.getxPosOut() + "\nY:" + hero.getyPosOut(), 10, 100);
			g.drawString("mapX:" + mapX + "\nmapY:" + mapY, 10, 200);
			g.drawString("Direction: " + direction, 10, 150);
			g.drawString("MouseX:" + Mouse.getX() + "\nMouseY:" + Mouse.getY(), 10, 250);
			g.drawString("Area: " + area, 10, 300);
		}

		if (quit == true) {
			g.drawString("Resume (R)", 250, 100);
			g.drawString("Main Menu (M)", 250, 150);
			g.drawString("Quit Game (Q)", 250, 200);
			if (quit == false) {
				g.clear();
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		hero.getAnimation().update(delta);
		hero.moveOut(gc, sbg, delta, input, worldMapWalls, mapX, mapY);
		findBattle(sbg);
		mapLogic();
		if (Debug.debugMode) {
			if (input.isKeyDown(Input.KEY_UP)) {
				mapY -= 1;
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				mapY += 1;
			}
			if (input.isKeyDown(Input.KEY_LEFT)) {
				mapX -= 1;
			}
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				mapX += 1;
			}
		}
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		}

		if (quit == true) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M)) {
				sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
			}
			if (input.isKeyDown(Input.KEY_Q)) {
				System.exit(0);
			}
			if (input.isKeyDown(Input.KEY_E)) {
				sbg.enterState(4);
			}
		}
	}

	public void findBattle(StateBasedGame sbg) {
		if (hero.findBattle()) {
			Battle.playMusic();
			sbg.enterState(4, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 500));
		}
	}

	public void mapLogic() {
		switch (stage) {
		case 1:
			stageOne();
			break;
		}
	}

	public void stageOne() {
		// if (input.isKeyDown(Input.KEY_W)) { // 206
		// Color c = worldMapWalls.getColor((int) xPosOut + 22, (int) yPosOut +
		// 65);
		// Boolean noCollision = c.a == 0f;
		// if (noCollision) {
		// moveUp(delta);
		// }
		// }
		if (hero.getyPosOut() + 75 < 0) { // up
			hero.setyPosOut(600 - (hero.getHeightOut() - 1));
			mapY += 600;
		}
		if (hero.getyPosOut() + 65 > 600) { // down
			hero.setyPosOut(-40);
			mapY -= 600;
		}
		if (hero.getxPosOut() + 6 < 0) { // left
			hero.setxPosOut(0);
			mapX = -800;
			mapY = -1200;
		}
	}

	public int getID() {
		return 3;
	}
}
