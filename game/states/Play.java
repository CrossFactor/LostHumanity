package game.states;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;

public class Play extends BasicGameState {
	Image worldMap;
	Image worldMapWalls;
	boolean quit = false;
	int direction = 1;
	int area = 1;
	int mapX = 0;
	int mapY = 0;
	String job = "slayer";
	Hero hero = Slayer.slayer;
	Music music;

	public Play(int state) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#enter(org.newdawn.slick.
	 * GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		// sound.play();
		music.loop();
		hero.faceDown();
	}

	public void leave() throws SlickException {
		// sound.stop();
		// Songs.playBgm.stop();
	}
	
	public void setHeroSprites() throws SlickException{
		SpriteSheet moveUp = new SpriteSheet("res/" + job + "/out/moveUp.png", 45, 80);
	SpriteSheet moveDown = new SpriteSheet("res/" + job + "/out/moveDown.png", 45, 80);
	SpriteSheet moveLeft = new SpriteSheet("res/" + job + "/out/moveLeft.png", 45, 80);
	SpriteSheet moveRight = new SpriteSheet("res/" + job + "/out/moveRight.png", 45, 80);
	SpriteSheet idleLeft = new SpriteSheet("res/" + job + "/attack/idleLeft.png", 300, 200);
	SpriteSheet idleRight = new SpriteSheet("res/" + job + "/attack/idleRight.png", 300, 200);
	SpriteSheet attackLeft = new SpriteSheet("res/" + job + "/attack/attackLeft.png", 300, 200);
	SpriteSheet attackRight = new SpriteSheet("res/" + job + "/attack/attackRight.png", 300, 200);
	SpriteSheet battleMoveLeft = new SpriteSheet("res/" + job + "/attack/moveLeft.png", 300, 200);
	SpriteSheet battleMoveRight = new SpriteSheet("res/" + job + "/attack/moveRight.png", 300, 200);
	List<SpriteSheet> move = new ArrayList<SpriteSheet>();
	List<SpriteSheet> idle = new ArrayList<SpriteSheet>();
	List<SpriteSheet> attack = new ArrayList<SpriteSheet>();
	List<SpriteSheet> battleMove = new ArrayList<SpriteSheet>();
	move.add(moveUp);
	move.add(moveDown);
	move.add(moveLeft);
	move.add(moveRight);
	idle.add(idleLeft);
	idle.add(idleRight);
	attack.add(attackLeft);
	attack.add(attackRight);
	battleMove.add(battleMoveLeft);
	battleMove.add(battleMoveRight);
	hero.setSpriteSheets(move, idle, attack, battleMove);
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {// tiledmap
		music = new Music("sounds/one/bgm1.wav");
		setHeroSprites();
		worldMap = new Image("res/backgrounds/whole map/floor.png");
		worldMapWalls = new Image("res/backgrounds/whole map/walls.png");
		hero.faceDown();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		worldMap.draw(mapX, mapY);
		worldMapWalls.draw(mapX, mapY);
		hero.getAnimation().draw(hero.getxPosOut(), hero.getyPosOut());
		g.drawString("X:" + hero.getxPosOut() + "\nY:" + hero.getyPosOut(), 10, 100);
		g.drawString("mapX:" + mapX + "\nmapY:" + mapY, 10, 200);
		g.drawString("Direction: " + direction, 10, 150);
		g.drawString("MouseX:" + Mouse.getX() + "\nMouseY:" + Mouse.getY(), 10, 250);
		g.drawString("Area: " + area, 10, 300);

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
		hero.moveOut(gc, sbg, delta, input, worldMapWalls);
		hero.findBattle(music, sbg);
		switch (area) {
		case 1:
			mapX = 0;
			mapY = 0;
			if (hero.getyPosOut() >= 457 && (hero.getxPosOut() >  368 && hero.getxPosOut() <  382)) {
				area++;
				hero.setyPosOut(6);
			}
			break;
		case 2:
			mapX = 0;
			mapY = -600;
			if (hero.getxPosOut() >=  692 && (hero.getyPosOut() >  215 && hero.getyPosOut() <  235)) {
				area++;
				hero.setxPosOut(64);
			}
			break;
		case 3:
			mapX = -800;
			mapY = -600;
			if (hero.getyPosOut() >= 457 && (hero.getxPosOut() >  368 && hero.getxPosOut() <  382)) {
				area++;
				hero.setyPosOut(6);
			}
			break;
		case 4:
			mapX = -800;
			mapY = -1200;
			break;
		}

		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			quit = true;
		}

		if (quit == true) {
			if (input.isKeyDown(Input.KEY_R)) {
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M)) {
				sbg.enterState(0);
			}
			if (input.isKeyDown(Input.KEY_Q)) {
				System.exit(0);
			}
			if (input.isKeyDown(Input.KEY_E)) {
				sbg.enterState(2);
			}
		}
	}

	public int getID() {
		return 1;
	}
}
