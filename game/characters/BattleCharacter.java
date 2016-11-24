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

import game.util.Healthbar;
import game.util.Hitbox;

public abstract class BattleCharacter {
	private String name;
	private int hp;
	private int damage;
	private int widthBattle, heightBattle;
	private Boolean isAttacking = false;
	private Animation animation, battleIdleLeft, battleIdleRight;
	protected Animation attackLeft;
	protected Animation attackRight;
	private Animation battleMoveLeft;
	private Animation battleMoveRight;
	private float xPosBattle = 400;
	private float yPosBattle = 400;
	private Hitbox charHitbox, weapHitbox;
	private Healthbar healthbar;
	
	public abstract void battle(GameContainer gc, StateBasedGame sbg, int delta);
	
	public BattleCharacter(String name, int hp, int damage, int widthBattle, int heightBattle) {
		this.name = name;
		this.hp = hp;
		this.damage = damage;
		this.setWidthBattle(widthBattle);
		this.setHeightBattle(heightBattle);
		setCharHitbox(new Hitbox(xPosBattle, yPosBattle, widthBattle, heightBattle, false));
		setHealthbar(new Healthbar(hp, xPosBattle, yPosBattle, widthBattle));
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		getAnimation().draw(getxPosBattle(), getyPosBattle());
		drawHealthbar(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		getAnimation().update(delta);
		healthbar.update(xPosBattle, yPosBattle);
		battle(gc, sbg, delta);
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

	private void setHealthbar(Healthbar healthbar) {
		this.healthbar = healthbar;
	}

	private Healthbar getHealthbar() {
		return healthbar;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public void startAttack() {
		isAttacking = true;

	}

	public void stopAttack() {
		isAttacking = false;
	}

	public Boolean isAttacking() {
		return isAttacking;
	}

	public void attack(BattleCharacter c) {
		c.takeDamage(this.damage);
	}

	public void takeDamage(int damage) {
		this.hp -= damage;
	}

	public Boolean isAlive() {
		return hp > 0 ? true : false;
	}

	public void setSpriteSheets(List<SpriteSheet> idle, List<SpriteSheet> attack, List<SpriteSheet> battleMove) {
		this.battleIdleLeft = new Animation(idle.get(0), 550);
		this.battleIdleRight = new Animation(idle.get(1), 550);
		this.attackLeft = new Animation(attack.get(0), 350);
		this.attackRight = new Animation(attack.get(1), 350);
		this.battleMoveLeft = new Animation(battleMove.get(0), 450);
		this.battleMoveRight = new Animation(battleMove.get(1), 450);
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

	public Animation getAnimation() {
		return animation;
	}

	protected void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public int getWidthBattle() {
		return widthBattle;
	}

	private void setWidthBattle(int widthBattle) {
		this.widthBattle = widthBattle;
	}

	public int getHeightBattle() {
		return heightBattle;
	}

	public void drawHealthbar(Graphics g) throws SlickException {
		healthbar.draw(g);
	}

	private void setHeightBattle(int heightBattle) {
		this.heightBattle = heightBattle;
	}

	public Hitbox getCharHitbox() {
		return charHitbox;
	}

	public void setCharHitbox(Hitbox charHitbox) {
		this.charHitbox = charHitbox;
	}

	// public Hitbox getWeapHitbox() {
	// return weapHitbox;
	// }
	//
	// public void setWeapHitbox(Hitbox weapHitbox) {
	// this.weapHitbox = weapHitbox;
	// }
	//
	// public Hitbox getCharHitbox() {
	// return charHitbox;
	// }
	//
	// public void setCharHitbox(Hitbox charHitbox) {
	// this.charHitbox = charHitbox;
	// }
}