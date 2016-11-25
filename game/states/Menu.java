package game.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Menu extends BasicGameState {

	Image menu;
	Image menuLoad;
	Image menuNG;
	Image menuQuit;
	int selection;
	Music music;
	Sound select;
	Music arrow;

	public Menu(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music = new Music("sounds/one/mainMenuMusic.wav");
		select = new Sound("sounds/one/powerup.wav");
		menuNG = new Image("res/menu/menuNG.png");
		menuQuit = new Image("res/menu/menuQuit.png");
		menuLoad = new Image("res/menu/menuLoad.png");
		menu = menuLoad;
		selection = 0;
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		music.loop();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(menu, 0, 0);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			select.play();
			if (selection < 2) {
				selection++;
			}
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			select.play();
			if (selection > 0) {
				selection--;
			}
		}
		switch (selection) {
		case 0:
			menu = menuLoad;
			break;
		case 1:
			menu = menuNG;
			break;
		case 2:
			menu = menuQuit;
			break;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			select.play();
			music.stop();
			switch (selection) {
			case 0:
				break;
			case 1:
				sbg.enterState(1);
				break;
			case 2:
				System.exit(0);
				break;
			}
		}
	}

	public int getID() {
		return 0;
	}
}
