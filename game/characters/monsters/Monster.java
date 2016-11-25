package game.characters.monsters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import game.characters.*;
import game.util.Debug;

public abstract class Monster extends BattleCharacter {
	Boolean isMoving;

	public Monster(BattleCharacterInfo info) {
		super(info);

	}

	@Override
	// this is for hero control during battle
	public void battle(GameContainer gc, StateBasedGame sbg, int delta) {
		// Sound.bg.loop();
		if (isAlive()) {
			Input input = gc.getInput();
			getAnimation().start();
			getAnimation().update(delta);
			battleInput(gc, sbg, delta, input);
		}
	}

	// controls for hero during battle
	public void battleInput(GameContainer gc, StateBasedGame sbg, int delta, Input input) {
		Boolean isMoving = false; // 0 up 1 down 2 left 3 right
		// sends player back to menu TEMPORARY
		if (Debug.debugMode == true) {
			if (input.isKeyDown(Input.KEY_M)) {
				sbg.enterState(1);
			}
			if (input.isKeyDown(Input.KEY_4))
				;
		}

		// despawns hitbox
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexEndAttackFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexEndAttackFrame())) {
			setHurtbox(null);
		}

		// stops attack animation at last frame
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexLastFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexLastFrame())) {
			stopAttack(); // sets isAttacking() to false
		} else if (isAttacking() == true) {
			if (getBattleDirection() == 1) {
				attackLeft();
			} else {
				attackRight();
			}
		} else if (isHit() == true) {
			if (getAnimation().getCurrentFrame() == hitLeft.getImage(3)
					|| getAnimation().getCurrentFrame() == hitRight.getImage(3)) {
				hitRecover();
			}
		} else if (isAttacking() == false && isHit() == false) { // Movement.
			// Can only
			// move if
			// is
			// not attacking and not hit

			if (input.isKeyPressed(Input.KEY_Y)) {
				startAttack(); // sets isAttacking() to true
			}

			else if (input.isKeyDown(Input.KEY_A)) {
				battleMoveLeft(delta);
				isMoving = true;
				setBattleDirection(1);
			}

			else if (input.isKeyDown(Input.KEY_D)) {
				battleMoveRight(delta);
				isMoving = true;
				setBattleDirection(2);
				;
			}

			if (isMoving == false) {
				resetIdle();
			}
		}
	}

	public void setSpriteSheets(List<SpriteSheet> idle, List<SpriteSheet> attack, List<SpriteSheet> battleMove) {
		super.setSpriteSheets(idle, attack, battleMove);
	}

	protected void setHitAnimations(Animation hitLeft, Animation hitRight) {
		super.setHitLeft(hitLeft);
		super.setHitRight(hitRight);
	}

	public abstract void setMonsterSheets() throws SlickException;
}