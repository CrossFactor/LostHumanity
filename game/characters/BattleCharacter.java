package game.characters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
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
	private Boolean attacking = false;
	private Boolean isHit = false;
	private Boolean hurtboxSpawned = false;

	private Animation animation, battleIdleLeft, battleIdleRight;
	protected Animation attackLeft, attackRight;
	private Animation battleMoveLeft, battleMoveRight;
	protected Animation hitLeft, hitRight;

	private float xPosBattle = 400;
	private float yPosBattle = 400;

	private Boxes generalBoxes;
	private Healthbar healthbar;
	private float hurtboxX;

	private Boolean alive = true;
	private float hitCd, cdIncrement = 0;

	public abstract void battle(GameContainer gc, StateBasedGame sbg, int delta);

	public BattleCharacter(BattleCharacterInfo info) {
		this.info = info;
		generalBoxes = new Boxes();
		generalBoxes.setHitbox(new Box(getCenterX() - (info.getHitboxWidth() / 2),
				yPosBattle + info.getDistanceFromTop(), info.getHitboxWidth(), info.getHitboxHeight()));

		setMonsterBoxes();

		setHealthbar(new Healthbar(info.getMaxHp(), getCenterX() - (info.getMaxHp() / 2),
				yPosBattle - info.getHealthBarDistance(), info.getWidthBattle()));
	}

	private void setMonsterBoxes() {
		if (info.getAggression() != 0) {
			Box aggressionLeft = new Box(getCenterX() - (info.getAggression()), yPosBattle, info.getAggression(),
					info.getHeightBattle());
			Box aggressionRight = new Box(getCenterX(), yPosBattle, info.getAggression(), info.getHeightBattle());
			generalBoxes.setAggressionBoxLeft(aggressionLeft);
			generalBoxes.setAggressionBoxRight(aggressionRight);

			// attack boxes set monster to attack if hero hitbox intersects it
			generalBoxes.setAttackBox(new Box(getCenterX() - info.getHurtboxWidth(),
					yPosBattle + info.getDistanceFromTop(), info.getHurtboxWidth() * 2, info.getHitboxHeight()));
			//TODO generalBoxes.setTargetRightBox(new Box(getCenterX(), getCenterY() - 2, 800, 4));
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		getAnimation().draw(getxPosBattle(), getyPosBattle());
		drawHealthbar(g);
		if (Debug.debugMode == true) {
			g.setColor(Color.green);
			generalBoxes.drawHitbox(g);
			if (generalBoxes.getAggressionBoxLeft() != null && generalBoxes.getAggressionBoxRight() != null) {
				g.setColor(Color.cyan);
				generalBoxes.drawAggressionBoxes(g);
			}
			if (generalBoxes.getAttackBox() != null) {
				g.setColor(Color.orange);
				generalBoxes.drawAttackBox(g);
			}
			if (generalBoxes.getHurtbox() != null) {
				g.setColor(Color.red);
				generalBoxes.drawHurtbox(g);
			}
//			if (generalBoxes.getTargetRightBox() != null) {
//				g.setColor(Color.pink);
//				generalBoxes.drawTargetRightBox(g);
//			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		getAnimation().update(delta);
		updateHealthbar(gc);

		generalBoxes.getHitbox().update(getCenterX() - (info.getHitboxWidth() / 2),
				yPosBattle + info.getDistanceFromTop());

		battle(gc, sbg, delta); // calls battle method of each

		hitCd += delta * cdIncrement; // cooldown after getting hit
		updateRecover();

		if (generalBoxes.getAggressionBoxLeft() != null && generalBoxes.getAggressionBoxRight() != null) {
			generalBoxes.getAggressionBoxLeft().update((getCenterX() - (info.getAggression())), yPosBattle);
			generalBoxes.getAggressionBoxRight().update(getCenterX(), yPosBattle);
			generalBoxes.getAttackBox().update(getCenterX() - info.getHurtboxWidth(),
					yPosBattle + info.getDistanceFromTop());
			//TODO generalBoxes.getTargetRightBox().update(getCenterX(), getCenterY() - 2);
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

	public Boolean isHit() {
		return isHit;
	}

	public void hit() {
		isHit = true;
		setCdIncrement(.1f);
	}

	public void resetIdle() {
		animation.stop();
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
		healthbar.setCurrentHp(0);
		alive = false;
	}

	public void battleFaceLeft() {
		setAnimation(battleIdleLeft);
	}

	public void battleFaceRight() {
		setAnimation(battleIdleRight);
	}

	public void startAttack() {
		animation.stop();
		if(battleDirection == 1){
			attackLeft();
		}
		else {
			attackRight();
		}
		animation.start();
		attacking = true;
	}

	public void stopAttack() {
		attacking = false;
		setHurtboxSpawned(false);
		resetIdle();
	}

	public Boolean isAttacking() {
		return attacking;
	}

	public void attackLeft() {
		//setAnimation(battleDirection == 1 ? attackLeft : attackRight);
		setAnimation(attackLeft);
		spawnHurtboxLeft();
	}

	public void attackRight() {
		setAnimation(attackRight);
		spawnHurtboxRight();
	}

	public void spawnHurtboxRight() {
		// spawns hurtbox at certain frame
		if (getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexStartAttackFrame())
				|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexStartAttackFrame())) {
			// makes hitbox only if attacking
			setHurtboxX(getCenterX() + info.getGapFromCenter());
			setHurtboxSpawned(true);
		}
	}

	public void spawnHurtboxLeft() {
		// spawns hurtbox at certain frame
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexStartAttackFrame())
				|| getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexStartAttackFrame())) {
			// makes hitbox only if attacking
			setHurtboxX(getCenterX() - info.getHurtboxWidth() - info.getGapFromCenter());
			setHurtboxSpawned(true);
		}
	}

	public void battleMoveLeft(int delta) {
		if (inMapBounds()) {
			setAnimation(battleMoveLeft);
			setxPosBattle(getxPosBattle() - delta * getInfo().getMoveSpeed());
		}

	}

	public void battleMoveRight(int delta) {
		if (inMapBounds()) {
			setAnimation(battleMoveRight);
			setxPosBattle(getxPosBattle() + delta * getInfo().getMoveSpeed());
		}
	}

	private boolean inMapBounds() {
		return generalBoxes.getHitbox().getX() - 1 > 0
				|| (generalBoxes.getHitbox().getX() + generalBoxes.getHitbox().getWidth() + 1 < 600);
	}

	public void drawHealthbar(Graphics g) throws SlickException {
		if (alive) {
			healthbar.draw(g);
		}
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
		this.battleIdleLeft = new Animation(idle.get(0), 200);
		this.battleIdleRight = new Animation(idle.get(1), 200);
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
		generalBoxes.setHurtbox(hurtboxSpawned == false ? null
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

	public Boxes getGeneralBoxes() {
		return generalBoxes;
	}

	public void setgeneralBoxes(Boxes generalBoxes) {
		this.generalBoxes = generalBoxes;
	}
}