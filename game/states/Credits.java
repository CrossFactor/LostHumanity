package game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.Game;
import game.util.Songs;

public class Credits extends BasicGameState {
	Image[] credits;
	private int event = 0;

	public Credits(int state) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		credits = new Image[] { new Image("res/credits/credits1.png"), new Image("res/credits/credits2.png"),
				new Image("res/credits/credits3.png") };
	}

	@Override
	public void enter(GameContainer container, StateBasedGame sbg) throws SlickException {
		super.enter(container, sbg);
		Songs.creditsBgm();
		event = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		credits[event].draw(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			if (event < 2) {
				event++;
			}
			else {
				sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
