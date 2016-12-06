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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Game;
import game.characters.NPC;
import game.characters.heroes.Hero;
import game.characters.heroes.Slayer;
import game.util.ChatBox;
import game.util.Songs;
import game.util.Sounds;
import npcs.Hatoko;
import npcs.Witch;

public class SceneOne extends BasicGameState {
	Animation ball, portal;
	NPC gf, witch;
	Hero hero = Slayer.slayer;
	Boolean renderSlayer = false;
	Boolean renderGf = false;
	Boolean renderWitch = false;
	Boolean renderPortal = false;
	Boolean renderBall = false;
	Image slayerTalk, artemisiaTalk, witchTalk, gfTalk;
	ChatBox chatbox;
	Image bg;
	Long time = 0L;
	int event = -1;

	public SceneOne(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		Songs.pause();
		Songs.resume();
		event++;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gf = new Hatoko();
		witch = new Witch();
		((Hatoko) gf).setSpriteSheets();
		((Witch) witch).setSpriteSheets();
		hero.faceDown();
		witch.faceRight();
		gf.faceRight();
		renderSlayer = false;
		renderGf = false;
		renderWitch = false;
		renderPortal = false;
		renderBall = false;
		slayerTalk = new Image("res/dialogue/slayerTalk.png");
		artemisiaTalk = new Image("res/dialogue/artemisiaTalk.png");
		witchTalk = new Image("res/dialogue/witchTalk.png");
		gfTalk = new Image("res/dialogue/gfTalk.png");
		bg = new Image("res/cutscenes/townRavaged.png");
		hero.setxPosOut(-hero.getWidthOut());
		gf.setxPosOut(-(gf.getInfo().getWidth() * 2));
		witch.setxPosOut(800 + 1);
		hero.setyPosOut(250);
		gf.setyPosOut(200);
		witch.setyPosOut(200);
		SpriteSheet bol = new SpriteSheet("res/effects/ball.png", 50, 50);
		ball = new Animation(bol, 300);
		SpriteSheet portol = new SpriteSheet("res/effects/portal.png", 120, 160);
		portal = new Animation(portol, 300);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		bg.draw(0, 0);
		if (renderGf) {
			gf.getAnimation().draw(gf.getxPosOut(), gf.getyPosOut());
		}
		if (renderSlayer) {
			hero.getAnimation().draw(hero.getxPosOut(), hero.getyPosOut());
		}
		if (renderWitch) {
			witch.getAnimation().draw(witch.getxPosOut(), witch.getyPosOut());
		}
		if (renderBall) {
			ball.draw(gf.getCenterX() - 25, gf.getCenterY() - 25);
		}
		if (renderPortal) {
			portal.draw(hero.getCenterXOut(), hero.getCenterYOut());
		}
		switch (event) {
		case 0:
			break;
		case 1:
			chatbox = new ChatBox(slayerTalk, "Over here!");
			chatbox.render(g);
			break;
		case 2:
			chatbox = new ChatBox(gfTalk, "Okay!");
			chatbox.render(g);
			break;
		case 3:
			renderSlayer = true;
			renderGf = true;
			break;
		case 4:
			chatbox = new ChatBox(witchTalk, "Where do you think you're going?");
			chatbox.render(g);
			break;
		case 5:
			renderWitch = true;
			break;
		case 6:
			chatbox = new ChatBox(gfTalk, "No!");
			chatbox.render(g);
			break;
		case 7:
			chatbox = new ChatBox(slayerTalk, "How did she get ahead of us!");
			chatbox.render(g);
			break;
		case 8:
			chatbox = new ChatBox(witchTalk, "Your cute little girlfriend's soul is mine to keep now!");
			chatbox.render(g);
			break;
		case 9:
			chatbox = new ChatBox(gfTalk, "Noooooooooo!");
			chatbox.render(g);
			break;
		case 10:
			break;
		case 11:
			renderBall = true;
			renderGf = false;
			chatbox = new ChatBox(gfTalk, "...");
			chatbox.render(g);
			break;
		case 12:
			break;
		case 13:
			hero.faceLeft();
			break;
		case 14:
			chatbox = new ChatBox(slayerTalk, "HATOKOOOOOOO!");
			chatbox.render(g);
			break;
		case 15:
			chatbox = new ChatBox(witchTalk, "You're next!");
			chatbox.render(g);
			break;
		case 16:
			chatbox = new ChatBox(witchTalk, "But I have a better plan for you nyehehehe!");
			hero.faceRight();
			chatbox.render(g);
			break;
		case 17:
			chatbox = new ChatBox(slayerTalk, "You'll pay for this!");
			chatbox.render(g);
			break;
		case 18:
			chatbox = new ChatBox(witchTalk, "Nyehehehehehe!");
			chatbox.render(g);
			break;
		case 19:
			break;
		case 20:
			renderSlayer = false;
			renderPortal = true;
			chatbox = new ChatBox(slayerTalk, "Woooooaaaaaah!");
			chatbox.render(g);
			break;
		case 21:
			chatbox = new ChatBox(witchTalk, "Good luck with surviving my tower!",
					"If you live, you can get your girlfriend back!");
			chatbox.render(g);
			break;
		case 22:
			break;
		case 23:
			renderPortal = false;
			break;
		case 24:
			chatbox = new ChatBox(witchTalk, "Now then...", "Shall we proceed dear Hatoko? Nyehehehehehe!");
			chatbox.render(g);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		updateAnimationDelta(delta);
		switch (event) {
		case 0:
			event++;
			break;
		case 1:
			pressEnter(gc);
			break;
		case 2:
			pressEnter(gc);
			break;
		case 3:
			if (hero.getxPosOut() < 380) {
				hero.moveRight(delta);
				gf.moveRight(delta);
			} else {
				hero.stopMoving();
				gf.stopMoving();
				event++;
			}
			break;
		case 4:
			pressEnter(gc);
			break;
		case 5:
			if (witch.getxPosOut() > 450) {
				witch.moveLeft(delta);
			} else {
				witch.stopMoving();
				event++;
			}
			break;
		case 6:
			pressEnter(gc);
			break;
		case 7:
			pressEnter(gc);
			break;
		case 8:
			pressEnter(gc);
			break;
		case 9:
			pressEnter(gc);
			break;
		case 10:
			sbg.enterState(this.getID(), new FadeOutTransition(Color.white, 1200),
					new FadeInTransition(Color.white, 2000));
			break;
		case 11:
			pressEnter(gc);
			break;
		case 12:
			delay(delta, 1100);
			break;
		case 13:
			delay(delta, 500);
			break;
		case 14:
			pressEnter(gc);
			break;
		case 15:
			pressEnter(gc);
			break;
		case 16:
			pressEnter(gc);
			break;
		case 17:
			pressEnter(gc);
			break;
		case 18:
			pressEnter(gc);
			break;
		case 19:
			sbg.enterState(this.getID(), new FadeOutTransition(Color.white, 1200),
					new FadeInTransition(Color.white, 2000));
			break;
		case 20:
			pressEnter(gc);
			break;
		case 21:
			pressEnter(gc);
			break;
		case 22:
			sbg.enterState(this.getID(), new FadeOutTransition(Color.white, 1200),
					new FadeInTransition(Color.white, 2000));
			break;
		case 23:
			delay(delta, 2000);
			break;
		case 24:
			pressEnter(gc);
			break;
		case 25:
			sbg.enterState(Game.scene2, new FadeOutTransition(Color.black, 1200), new FadeInTransition(Color.black, 2000));
		}

	}

	public void delay(int delta, int ms){
		time += delta;
		if (time >= ms) {
			event++;
			time = 0L;
		}
	}
	private void updateAnimationDelta(int delta) {
		hero.getAnimation().update(delta);
		gf.getAnimation().update(delta);
		witch.getAnimation().update(delta);
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
		return 11;
	}

}
