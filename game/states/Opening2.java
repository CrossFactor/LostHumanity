package game.states;

import org.newdawn.slick.Animation;
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

public class Opening2 extends BasicGameState {
	Image menu;
	Animation enter;

	public Opening2(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menu = new Image("res/menu/menunu.png");
		SpriteSheet enterSheet = new SpriteSheet("res/menu/pressEnter.png", 250, 100);
		enter = new Animation(enterSheet, 1000);
		enter.setPingPong(true);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menu.draw(0, 0);
		enter.draw(400 - (250 / 2), 400);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
			Opening.playSound();
		}

	}

	@Override
	public int getID() {
		return 1;
	}

}
