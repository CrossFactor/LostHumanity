package game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import game.Game;
import game.util.Songs;
import game.util.Sounds;

public class Pause extends BasicGameState {
	private Image[] pause;
	private int selection = 0;

	public Pause(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		Songs.pauseBgm();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		pause = new Image[] { new Image("res/pause/pause1.png"), new Image("res/pause/pause2.png"),
				new Image("res/pause/pause3.png") };
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
		pause[selection].draw(0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		controlMenu(input);
		if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_K)) {
			Sounds.bellSound();
			enterLogic(sbg);
		}
	}

	public void controlMenu(Input input) throws SlickException {
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) {
			if (selection < 2) {
				selection++;
				Sounds.selectSound();
			}
		} else if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			if (selection > 0) {
				selection--;
				Sounds.selectSound();
			}
		}
	}

	public void enterLogic(StateBasedGame sbg) {
		switch (selection) { // this controls if enter is pressed
		case 0:
			sbg.enterState(Game.play);
			break;
		case 1:
			break;
		case 2:
			sbg.enterState(Game.menu);;
			break;
		}
	}

	@Override
	public int getID() {
		return 6;
	}
}
