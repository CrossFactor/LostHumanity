package game.characters.monsters;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import game.characters.*;
import game.util.Debug;

public abstract class Monster extends BattleCharacter {
	BattleCharacter target;
	Boolean attackTarget;
	Boolean moving;

	public BattleCharacter getTarget() {
		return target;
	}

	public void setTarget(BattleCharacter target) {
		this.target = target;
	}

	public Monster(BattleCharacterInfo info) {
		super(info);
		attackTarget = false;
		battleFaceLeft();
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
		moving = false; // 0 up 1 down 2 left 3 right

		if (isAlive() == true) {
			if (attackTarget == false) {
				if (getGeneralBoxes().getAggressionBoxLeft().getBounds()
						.intersects(target.getGeneralBoxes().getHitbox().getBounds())) {
					attackTarget = (new Random().nextInt(10) + 1 == 6);
				}
			}

			// despawns hitbox
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexEndAttackFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexEndAttackFrame())) {
				getGeneralBoxes().setHurtbox(null);
			}
			if (isHit()) {
				stopAttack();
			}
			// stops attack animation at last frame
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexLastFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexLastFrame())) {
				stopAttack(); // sets isAttacking() to false
				attackTarget = false;
				System.out.println("LEL");
			} else if (isAttacking() == true) {
				startAttack();
			} else if (isAttacking() == false && isHit() == false) { // Movement.
				// Can only
				// move if
				// is
				// not attacking and not hit
				if (attackTarget == true) {
					if (getGeneralBoxes().getAttackBox().getBounds()
							.intersects(target.getGeneralBoxes().getHitbox().getBounds()) == true) {
						startAttack(); // sets isAttacking() to true
					} else if (getGeneralBoxes().getAggressionBoxLeft().getBounds()
							.intersects(target.getGeneralBoxes().getHitbox().getBounds()) == true) {
						battleMoveLeft(delta);
						moving = true;
						setBattleDirection(1);
					} else if (getGeneralBoxes().getAggressionBoxRight().getBounds()
							.intersects(target.getGeneralBoxes().getHitbox().getBounds()) == true) {
						battleMoveRight(delta);
						moving = true;
						setBattleDirection(2);
					}
				}
				if (moving == false) {
					resetIdle();
				}
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