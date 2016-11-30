package game.characters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;

import game.util.Debug;
import game.util.Healthbar;
import game.util.Box;
import game.util.Boxes;

public abstract class BattleCharacter {
	protected BattleCharacterInfo info;
	private int battleDirection = 2;
	private Boolean isOnCooldown = false;
	private Boolean isAttacking = false;
	private Boolean isHit = false;
	private Boolean hurtboxSpawned = false;
	private Animation animation, battleIdleLeft, battleIdleRight;
	protected Animation attackLeft, attackRight;
	private Animation battleMoveLeft, battleMoveRight;
	protected Animation hitLeft, hitRight;
	private float xPosBattle = 400;
	private float yPosBattle = 400;
	private Boxes hitboxes;
	protected Healthbar healthbar;
	private float hurtboxX;
	private boolean alive = true;
	private float hitCd, cdIncrement = 0;

	public abstract void battle(GameContainer gc, StateBasedGame sbg, int delta);

	public BattleCharacter(BattleCharacterInfo info) {
		this.info = info;
		hitboxes = new Boxes();
		hitboxes.setHitbox(new Box(getCenterX() - (info.getHitboxWidth() / 2), yPosBattle + info.getDistanceFromTop(),
				info.getHitboxWidth(), info.getHitboxHeight()));

		Box aggressionLeft = info.getAggression() == 0 ? null
				: new Box(getCenterX() - (info.getAggression()), yPosBattle, info.getAggression(),
						info.getHeightBattle());
		Box aggressionRight = info.getAggression() == 0 ? null
				: new Box(getCenterX(), yPosBattle, info.getAggression(), info.getHeightBattle());
		hitboxes.setAggressionBoxLeft(aggressionLeft);
		hitboxes.setAggressionBoxRight(aggressionRight);

		setHealthbar(new Healthbar(info.getMaxHp(), getCenterX() - (info.getMaxHp() / 2),
				yPosBattle - info.getHealthBarDistance(), info.getWidthBattle()));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		getAnimation().draw(getxPosBattle(), getyPosBattle());
		drawHealthbar(g);
		if (Debug.debugMode == true) {
			hitboxes.drawHitbox(g);
			if (hitboxes.getHurtbox() != null) {
				hitboxes.drawHurtbox(g);
			}
			if (hitboxes.getAggressionBoxLeft() != null && hitboxes.getAggressionBoxRight() != null) {
				hitboxes.drawAggressionBoxes(g);
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		getAnimation().update(delta);
		updateHealthbar(gc);
		
		hitboxes.getHitbox().update(getCenterX() - (info.getHitboxWidth() / 2), yPosBattle + info.getDistanceFromTop());
		
		battle(gc, sbg, delta); //calls battle method of each
		
		hitCd += delta * cdIncrement; //cooldown after getting hit
		updateRecover();
		
		if (hitboxes.getAggressionBoxLeft() != null && hitboxes.getAggressionBoxRight() != null) {
			hitboxes.getAggressionBoxLeft().update((getCenterX() - (info.getAggression())), yPosBattle);
			hitboxes.getAggressionBoxRight().update(getCenterX(), yPosBattle);
		}
		if (!alive) {
			info.setCurrentHp(0);
		}
	}

	// functionality
	public void updateRecover() {
		if (isHit()) {
			if (getHitCd() > 49) {
				hitRecover();
				setHitCd(0);
			}
		}
	}

	public void updateHealthbar(GameContainer gc) {
		healthbar.update(getCenterX() - (info.getMaxHp() / 2), yPosBattle - info.getHealthBarDistance());
	}

	public void startAttack() {
		isAttacking = true;
	}

	public void stopAttack() {
		isAttacking = false;
		setHurtboxSpawned(false);
		resetIdle();
	}

	public Boolean isAttacking() {
		return isAttacking;
	}

	public Boolean isHit() {
		return isHit;
	}

	public void hit() {
		isHit = true;
		setCdIncrement(.1f);
	}

	public void resetIdle() {
		if (getBattleDirection() == 1) {
			battleFaceLeft();
		} else {
			battleFaceRight();
		}
	}

	public void hitRecover() {
		isHit = false;
		setCdIncrement(0f);
		resetIdle();
	}

	public void attack(BattleCharacter c) {
		c.takeDamage(this.info.getDamage());
		c.getsHit();
	}

	private void getsHit() {
		if (getBattleDirection() == 1) {
			getsHitLeft();
		} else {
			getsHitRight();
		}
		hit();
	}

	public void takeDamage(int damage) {
		if (isHit == false) {
			this.info.setCurrentHp(this.info.getCurrentHp() - damage);
			this.healthbar.setCurrentHp(this.info.getCurrentHp());
			if (info.getCurrentHp() <= 0) {
				kill();
			}
		}
	}

	public Boolean isAlive() {
		return alive;
	}

	public void kill() {
		info.setCurrentHp(0);
		alive = false;
	}

	public void battleFaceLeft() {
		setAnimation(battleIdleLeft);
	}

	public void battleFaceRight() {
		setAnimation(battleIdleRight);
	}

	public void attackLeft() {
		setAnimation(attackLeft);
		startAttack();
		// spawns hurtbox at certain frame
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexStartAttackFrame())
				|| getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexStartAttackFrame())) {
			// makes hitbox only if attacking
			setHurtboxX(getCenterX() - info.getHurtboxWidth() - info.getGapFromCenter());
			setHurtboxSpawned(true);
		}
	}

	public void attackRight() {
		setAnimation(attackRight);
		startAttack();
		// spawns hurtbox at certain frame
		if (getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexStartAttackFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexStartAttackFrame())) {
			// makes hitbox only if attacking
			setHurtboxX(getCenterX() + info.getGapFromCenter());
			setHurtboxSpawned(true);
		}
	}

	public void battleMoveLeft(int delta) {
		setAnimation(battleMoveLeft);
		setxPosBattle(getxPosBattle() - delta * getInfo().getMoveSpeed());
	}

	public void battleMoveRight(int delta) {
		setAnimation(battleMoveRight);
		setxPosBattle(getxPosBattle() + delta * getInfo().getMoveSpeed());
	}

	// draw healthbar
	public void drawHealthbar(Graphics g) throws SlickException {
		healthbar.draw(g);
	}

	// getters and setters
	public Healthbar getHealthbar() {
		return healthbar;
	}

	public float getxPosBattle() {
		return xPosBattle;
	}

	public void setxPosBattle(float xPosBattle) {
		this.xPosBattle = xPosBattle;
	}

	public float getyPosBattle() {
		return yPosBattle;
	}

	public void setyPosBattle(float yPosBattle) {
		this.yPosBattle = yPosBattle;
	}

	public void setHealthbar(Healthbar healthbar) {
		this.healthbar = healthbar;
	}

	public void setSpriteSheets(List<SpriteSheet> idle, List<SpriteSheet> attack, List<SpriteSheet> battleMove) {
		this.battleIdleLeft = new Animation(idle.get(0), 550);
		this.battleIdleRight = new Animation(idle.get(1), 550);
		this.attackLeft = new Animation(attack.get(0), 350);
		this.attackRight = new Animation(attack.get(1), 350);
		this.battleMoveLeft = new Animation(battleMove.get(0), 450);
		this.battleMoveRight = new Animation(battleMove.get(1), 450);
	}

	public Animation getAnimation() {
		return animation;
	}

	protected void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public float getCenterX() {
		return xPosBattle + (info.getWidthBattle() / 2.0f);
	}

	public float getCenterY() {
		return yPosBattle + (info.getHeightBattle() / 2.0f);
	}

	public BattleCharacterInfo getInfo() {
		return info;
	}

	public Boolean isOnCooldown() {
		return isOnCooldown;
	}

	public void offCooldown() {
		isOnCooldown = false;
	}

	public void onCooldown() {
		isOnCooldown = true;
	}

	public void getsHitLeft() {
		setAnimation(hitLeft);
	}

	public void getsHitRight() {
		setAnimation(hitRight);
	}

	public void setHitLeft(Animation hitLeft) {
		this.hitLeft = hitLeft;
	}

	public void setHitRight(Animation hitRight) {
		this.hitRight = hitRight;
	}

	public int getBattleDirection() {
		return battleDirection;
	}

	public void setBattleDirection(int battleDirection) {
		this.battleDirection = battleDirection;
	}

	public float getHurtboxX() {
		return hurtboxX;
	}

	public void setHurtboxX(float hurtboxX) {
		this.hurtboxX = hurtboxX;
	}

	public Boolean hurtboxIsSpawned() {
		return hurtboxSpawned;
	}

	public void setHurtboxSpawned(Boolean hurtboxSpawned) {
		this.hurtboxSpawned = hurtboxSpawned;
		hitboxes.setHurtbox(hurtboxSpawned == false ? null
				: new Box(hurtboxX, getyPosBattle() + info.getDistanceFromTopAttack(), info.getHurtboxWidth(),
						info.getHeightBattle() - info.getDistanceFromTopAttack()));
	}

	public float getHitCd() {
		return hitCd;
	}

	public void setHitCd(float hitCd) {
		this.hitCd = hitCd;
	}

	public float getCdIncrement() {
		return cdIncrement;
	}

	public void setCdIncrement(float cdIncrement) {
		this.cdIncrement = cdIncrement;
	}

	public Boxes getHitboxes() {
		return hitboxes;
	}

	public void setHitboxes(Boxes hitboxes) {
		this.hitboxes = hitboxes;
	}
}