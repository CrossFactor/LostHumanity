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
				if (aggressionBoxesIntersectTargetHitbox()) {
					attackTarget = (new Random().nextInt(999) + 1 == 6);
				}
			}

			// despawns hitbox
			if (currentFrameIsEndFrame()) {
				getGeneralBoxes().setHurtbox(null);
			}
			if (isHit()) {
				stopAttack();
			}
			// stops attack animation at last frame
			if (currentFrameIsLastFrame()) {
				stopAttack(); // sets isAttacking() to false
				attackTarget = false;
			} else if (isHit()) {
				hit();
			} else if (isAttacking() == true) {
				startAttack();
			} else if (isAttacking() == false && isHit() == false) { // Movement.
				// Can only
				// move if
				// is
				// not attacking and not hit
				if (attackTarget == true) {
					if (targetIsInAttackRange()) {
						startAttack(); // sets isAttacking() to true
					} else if (getGeneralBoxes().getTargetRightBox().getBounds()
							.intersects(target.getGeneralBoxes().getHitbox().getBounds()) == false) {
						battleMoveLeft(delta);
						moving = true;
						setBattleDirection(1);
					} else {
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

	public Boolean targetIsInAttackRange() {
		return getGeneralBoxes().getAttackBox().getBounds()
				.intersects(target.getGeneralBoxes().getHitbox().getBounds()) == true;
	}

	public Boolean aggressionBoxesIntersectTargetHitbox() {
		return getGeneralBoxes().getAggressionBoxLeft().getBounds()
				.intersects(target.getGeneralBoxes().getHitbox().getBounds())
				|| getGeneralBoxes().getAggressionBoxRight().getBounds()
						.intersects(target.getGeneralBoxes().getHitbox().getBounds());
	}

	public Boolean currentFrameIsEndFrame() {
		return getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexEndAttackFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexEndAttackFrame());
	}

	public Boolean currentFrameIsLastFrame() {
		return getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexLastFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexLastFrame());
	}

	public void setSpriteSheets(List<SpriteSheet> idle, List<SpriteSheet> attack, List<SpriteSheet> battleMove) {
		super.setSpriteSheets(idle, attack, battleMove);
	}

	protected void setHitAnimations(Animation hitLeft, Animation hitRight) {
		super.setHitLeft(hitLeft);
		super.setHitRight(hitRight);
	}

	protected void setDeathAnimations(SpriteSheet deadLeft, SpriteSheet deadRight) {
		Animation left = new Animation(deadLeft, 500);
		Animation right = new Animation(deadRight, 500);
		super.setDeadLeft(left);
		super.setDeadRight(right);
	}

	public abstract void setMonsterSheets() throws SlickException;
}