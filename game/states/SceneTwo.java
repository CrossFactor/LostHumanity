package game.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Game;
import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;
import game.util.ChatBox;
import game.util.Songs;
import game.util.Sounds;

public class SceneTwo extends BasicGameState {
	Animation portal;
	Image mapFloor, mapWall, mapSpawn;
	Hero hero = Slayer.slayer;
	Image slayerTalk, mysteryTalk;
	ChatBox chatbox;
	Boolean enterToContinue = false;
	Boolean renderSlayer = false;
	Boolean renderPortal = true;
	Long time = 0L;
	int event = -1;
	int x, y;

	public SceneTwo(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		mapFloor = new Image("res/backgrounds/levels/level1/floor.png");
		mapWall = new Image("res/backgrounds/levels/level1/wall.png");
		mapSpawn = new Image("res/backgrounds/levels/level1/spawn.png");
		slayerTalk = new Image("res/dialogue/slayerTalk.png");
		mysteryTalk = new Image("res/dialogue/mysteryTalk.png");
		hero.faceDown();
		for (int y = 0; y <= 600; y++) {
			for (int x = 0; x <= 800; x++) {
				Color c = mapSpawn.getColor(x, y);
				Boolean spawnPointHere = c.a != 0f;
				if (spawnPointHere) {
					this.x = x;
					this.y = y;
				}
			}
		}
		SpriteSheet portol = new SpriteSheet("res/effects/portal.png", 120, 160);
		portal = new Animation(portol, 300);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hero.setxPosOut(x - 25);
		hero.setyPosOut(y - 75);
		Songs.playBgm();
		event++;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		mapFloor.draw(0, 0);
		mapWall.draw(0, 0);
		if (renderSlayer) {
			hero.getAnimation().draw(hero.getxPosOut(), hero.getyPosOut());
		}
		if (renderPortal) {
			portal.draw(hero.getCenterXOut() - 15, hero.getCenterYOut());
		}
		switch (event) {
		case 0:
			break;
		case 1:
			break;
		case 2:
			renderPortal = false;
			renderSlayer = true;
			chatbox = new ChatBox(slayerTalk, "...");
			chatbox.render(g);
			break;
		case 3:
			chatbox = new ChatBox(slayerTalk, "So this is the tower...");
			chatbox.render(g);
			break;
		case 4:
			hero.faceDown();
			chatbox = new ChatBox(slayerTalk, "...");
			chatbox.render(g);
			break;
		case 10:
			chatbox = new ChatBox(mysteryTalk, "Is anyone there?");
			chatbox.render(g);
			break;
		case 11:
			chatbox = new ChatBox(slayerTalk, "A voice? Hatoko?");
			chatbox.render(g);
			break;
		case 12:
			chatbox = new ChatBox(mysteryTalk, "If you can hear me, follow my voice!");
			chatbox.render(g);
			break;
		case 13:
			chatbox = new ChatBox(mysteryTalk, "It's dangerous in here so be careful");
			chatbox.render(g);
			break;
		case 14:
			chatbox = new ChatBox(mysteryTalk, "Grab the sword by the statue, you'll need it");
			chatbox.render(g);
			break;
		case 15:
			chatbox = new ChatBox(mysteryTalk, "This tower is filled with creatures that will kill you");
			chatbox.render(g);
			break;
		case 16:
			chatbox = new ChatBox(mysteryTalk, "If you can make it up here, it'll be safer");
			chatbox.render(g);
			break;
		case 17:
			chatbox = new ChatBox(slayerTalk, "How do I know if I can trust you?");
			chatbox.render(g);
			break;
		case 18:
			chatbox = new ChatBox(mysteryTalk, "I sense that you will climb the tower anyway", "allow me to help");
			chatbox.render(g);
			break;
		case 19:
			chatbox = new ChatBox(mysteryTalk, "Oh and if you happen to.. not die..",
					"grab some souls of the monsters you killed");
			chatbox.render(g);
			break;
		case 20:
			chatbox = new ChatBox(mysteryTalk, "You'll need it");
			chatbox.render(g);
			break;
		case 21:
			chatbox = new ChatBox(mysteryTalk, "I'll be waiting a few floors up. Good luck!");
			chatbox.render(g);
			break;
		case 22:
			chatbox = new ChatBox(slayerTalk, "...");
			chatbox.render(g);
			break;
		case 23:
			chatbox = new ChatBox(slayerTalk, "A sword, huh?");
			chatbox.render(g);
			break;
		case 27:
			chatbox = new ChatBox(slayerTalk, "Big and sharp...");
			chatbox.render(g);
			break;
		case 28:
			chatbox = new ChatBox(slayerTalk, "I like it");
			chatbox.render(g);
			break;
		case 30:
			chatbox = new ChatBox(slayerTalk, "I'll save you, Hatoko");
			chatbox.render(g);
			break;
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		hero.getAnimation().update(delta);
		switch (event) {
		case 0:
			event++;
			break;
		case 1:
			sbg.enterState(this.getID(), new FadeOutTransition(Color.white, 1200),
					new FadeInTransition(Color.white, 2000));
			break;
		case 2:
			pressEnter(gc);
			break;
		case 3:
			pressEnter(gc);
			break;
		case 4:
			pressEnter(gc);
			break;
		case 5:
			if (hero.getyPosOut() < 160) {
				hero.moveDown(delta);
			} else {
				hero.stopMoving();
				event++;
			}
			break;
		case 6:
			delay(delta, 800);
			break;
		case 7:
			if (hero.getxPosOut() > 160) {
				hero.moveLeft(delta);
			} else {
				hero.stopMoving();
				event++;
			}
			break;
		case 8:
			delay(delta, 800);
			break;
		case 9:
			if (hero.getxPosOut() < 270) {
				hero.moveRight(delta);
			} else {
				hero.stopMoving();
				event++;
			}
			break;
		case 10:
			enterToContinue = true;
			break;
		case 24:
			enterToContinue = false;
			if (hero.getyPosOut() < 315) {
				hero.moveDown(delta);
			} else {
				hero.stopMoving();
				event++;
			}
			break;
		case 25:
			if (hero.getxPosOut() < 400 - ((float) hero.getWidthOut() / 2)) {
				hero.moveRight(delta);
			} else {
				hero.stopMoving();
				hero.faceUp();
				event++;
			}
			break;
		case 26:
			delay(delta, 400);
			break;
		case 27:
			enterToContinue = true;
			break;
		case 29:
			enterToContinue = false;
			if (hero.getyPosOut() < 450) {
				hero.moveDown(delta);
			} else {
				hero.stopMoving();
				event++;
			}
			break;
		case 30:
			if(gc.getInput().isKeyPressed(Input.KEY_ENTER)  || gc.getInput().isKeyPressed(Input.KEY_K)){
				sbg.enterState(Game.play, new FadeOutTransition(), new EmptyTransition());
			}
			
		}
		if (enterToContinue) {
			pressEnter(gc);
		}
	}

	public void delay(int delta, int ms) {
		time += delta;
		if (time >= ms) {
			event++;
			time = 0L;
		}
	}

	public void pressEnter(GameContainer gc) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_K)) {
			Sounds.selectSound();
			event++;
		}
	}

	@Override
	public int getID() {
		return 12;
	}

}
