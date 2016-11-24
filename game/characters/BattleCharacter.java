package game.characters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import game.util.Debug;
import game.util.Healthbar;
import game.util.Hitbox;
import game.util.SpriteSheetLists;

public abstract class BattleCharacter {
	protected BattleCharacterInfo info;
	private SpriteSheetLists spriteSheets;
	private Boolean isOnCooldown = false;
	private Boolean isAttacking = false;
	private Boolean isHit = false;
	private Animation animation, battleIdleLeft, battleIdleRight;
	protected Animation attackLeft;
	protected Animation attackRight;
	private Animation battleMoveLeft;
	private Animation battleMoveRight;
	private float xPosBattle = 400;
	private float yPosBattle = 400;
	private Hitbox hitbox, hurtbox;
	private Healthbar healthbar;

	public abstract void battle(GameContainer gc, StateBasedGame sbg, int delta);

	public BattleCharacter(BattleCharacterInfo info) {
		this.info = info;
		setHitbox(new Hitbox(getCenterX() - (info.getHitboxWidth() / 2), yPosBattle + info.getDistanceFromTop(),
				info.getHitboxWidth(), info.getHitboxHeight()));
		setHealthbar(new Healthbar(info.getHp(), xPosBattle, yPosBattle, info.getWidthBattle()));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		getAnimation().draw(getxPosBattle(), getyPosBattle());
		drawHealthbar(g);
		if (Debug.debugMode == true) {
			drawHitbox(g);
			if (getHurtbox() != null) {
				drawHurtbox(g);
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		getAnimation().update(delta);
		healthbar.update(xPosBattle, yPosBattle);
		hitbox.update(getCenterX() - (info.getHitboxWidth() / 2), yPosBattle + info.getDistanceFromTop());
		battle(gc, sbg, delta);
	}

	// functionality
	public void startAttack() {
		isAttacking = true;

	}

	public void stopAttack() {
		isAttacking = false;
		setHurtbox(null);
	}

	public Boolean isAttacking() {
		return isAttacking;
	}

	public Boolean isHit() {
		return isHit;
	}

	public void hit() {
		isHit = true;
	}

	public void hitRecover() {
		isHit = false;
	}

	public void attack(BattleCharacter c) {
		c.takeDamage(this.info.getDamage());
	}

	public void takeDamage(int damage) {
		this.info.setHp(this.info.getHp() - damage);
	}

	public Boolean isAlive() {
		return info.getHp() > 0 ? true : false;
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
	}

	public void attackRight() {
		setAnimation(attackRight);
		startAttack();
	}

	public void battleMoveLeft() {
		setAnimation(battleMoveLeft);
	}

	public void battleMoveRight() {
		setAnimation(battleMoveRight);
	}

	// draw shit
	public void drawHitbox(Graphics g) {
		hitbox.draw(g);
	}

	public void drawHealthbar(Graphics g) throws SlickException {
		healthbar.draw(g);
	}

	public void drawHurtbox(Graphics g) throws SlickException {
		hurtbox.draw(g);
	}

	// getters and setters
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

	private void setHealthbar(Healthbar healthbar) {
		this.healthbar = healthbar;
	}

	private Healthbar getHealthbar() {
		return healthbar;
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

	public Hitbox getHitbox() {
		return hitbox;
	}

	public void setHitbox(Hitbox charHitbox) {
		this.hitbox = charHitbox;
	}

	public Hitbox getHurtbox() {
		return hurtbox;
	}

	public void setHurtbox(Hitbox hurtbox) {
		this.hurtbox = hurtbox;
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
}