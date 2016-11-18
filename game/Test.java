package game;

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
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;

public class Test extends BasicGameState {
	Image worldMap;
	boolean quit = false;
	float playerPositionX = 0;
	float playerPositionY = 0;
	int direction = 1;
	String job = "slayer";
	Hero hero = Slayer.slayer;
	boolean isObject;
	
	Color c;
//	try //try solving this using separate thread and booleans
//	{
//		org.newdawn.slick.Sound sound = new org.newdawn.slick.Sound("bin\\res\\sounds\\bgm1.wav");
//	}catch(Exception e)
//	{
//		e.printStackTrace();
// 	}

	public Test(int state) {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		//sound.play();
	}

	public void leave() throws SlickException {
		//sound.stop();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {// tiledmap
		worldMap = new Image("res/tests/wall1.png");
		
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		worldMap.draw(playerPositionX, playerPositionY);
		g.drawString("Is object? " + isObject, 150, 100);
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
		c = worldMap.getColor(Mouse.getX(), Mouse.getY());
		isObject = c.a != 0f;
		

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
		}
	}
	
	private void moveHero(GameContainer gc, StateBasedGame sbg, int delta, Input input){
		Boolean isMoving = false; // 0 up 1 down 2 left 3 right
		// Sound.bg.loop();
		hero.getAnimation().update(delta);
		if (input.isKeyDown(Input.KEY_W)) { // 206
			if (playerPositionY < 206) {
				hero.moveUp();
				playerPositionY += delta * .125f;
				isMoving = true;
				direction = 0;
			}
		}

		else if (input.isKeyDown(Input.KEY_S)) {// -162
			if (playerPositionY > -162) {
				hero.moveDown();
				playerPositionY -= delta * .125f;
				isMoving = true;
				direction = 1;
			}
		}

		else if (input.isKeyDown(Input.KEY_A)) { // 404
			if (playerPositionX < 403) {
				hero.moveLeft();
				playerPositionX += delta * .125f;
				isMoving = true;
				direction = 2;
			}
		}

		else if (input.isKeyDown(Input.KEY_D)) {// -381
			if (playerPositionX > -381) {
				hero.moveRight();
				playerPositionX -= delta * .125f;
				isMoving = true;
				direction = 3;
			}
		}

		if (isMoving == true) {
			int battle = (new Random()).nextInt(6999) + 1;
			if (battle == 6) {
				sbg.enterState(2);
			}
		}

		if (isMoving == false) {
			switch (direction) {
			case 0:
				hero.faceUp();
				break;
			case 1:
				hero.faceDown();
				break;
			case 2:
				hero.faceLeft();
				break;
			case 3:
				hero.faceRight();
				break;
			}
		}
	}

	public int getID() {
		return 3;
	}
}

