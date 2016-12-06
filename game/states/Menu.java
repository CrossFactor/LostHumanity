package game.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.util.Songs;
import game.util.Sounds;
import game.Game;

public class Menu extends BasicGameState {
	private int selection;
	private Image[] menu;

	public Menu(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		menu = new Image[] { new Image("res/menu/menuLoad.png"), new Image("res/menu/menuNG.png"),
				new Image("res/menu/menuCredits.png"), new Image("res/menu/menuQuit.png") };
		selection = 0;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		Songs.menuBgm();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		menu[selection].draw(0, 0);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) {
			Sounds.selectSound();
			if (selection < 3) {
				selection++;
			}
		}
		if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
			Sounds.selectSound();
			if (selection > 0) {
				selection--;
			}
		}
		if (input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_K)) {
			Sounds.bellSound();
			switch (selection) {
			case 0:
				break;
			case 1:
				Songs.playSceneOneBgm();
				Songs.pause();
				sbg.enterState(Game.scene1, new FadeOutTransition(Color.white, 1000), new FadeInTransition(Color.white, 10000));
				break;
			case 2:
				sbg.enterState(Game.credits, new FadeOutTransition(), new FadeInTransition());
				break;
			case 3:
				System.exit(0);
				break;
			}
		}
	}

	public int getID() {
		return 2;
	}
}
