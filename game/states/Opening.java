package game.states;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.util.Sounds;

public class Opening extends BasicGameState {
	private Image op;
	private Music music;
	private static Sound sound;
	private float pan, zoom = 1;
	
	private float y = 0, ctr = 0, x = 0;
	
	public Opening(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		music = new Music("sounds/one/opMusic.ogg");
		op = new Image("res/cutscenes/tower.png");
		
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		music.play();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		op.draw(x, y, zoom);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		if(input.isKeyPressed(Input.KEY_ENTER)){
			Sounds.bellSound();
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		if(y < (float) -1200.0){
			ctr += delta * 0.155f;
			zoom += 1 * 0.0008f;
			pan = 1.4f;
			x -= 1 * 0.3f;
			if(ctr > 100){
				sbg.enterState(1, new FadeOutTransition(), new EmptyTransition());
			}
		} else{
			pan = delta * 0.13f;
		}
		y -= pan;
	}

	public int getID() {
		return 0;
	}
}
