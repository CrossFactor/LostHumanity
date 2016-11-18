package game.characters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

public abstract class GameCharacter {
	private String name;
	private int hp;
	private int damage;
	private Boolean isAttacking = false;
	private Animation animation, battleIdleLeft, battleIdleRight;
	protected Animation attackLeft;
	protected Animation attackRight;
	private Animation battleMoveLeft;
	private Animation battleMoveRight;
	private float xPosBattle = 400;
	private float yPosBattle = 400;
	
	public Animation getAL(){
		return attackLeft;
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

	public GameCharacter(String name, int hp) {
		this.name = name;
		this.hp = hp;
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

	public void setSpriteSheets(List<SpriteSheet> idle, List<SpriteSheet> attack, List<SpriteSheet> battleMove) {
		this.battleIdleLeft = new Animation(idle.get(0), 450);
		this.battleIdleRight = new Animation(idle.get(1), 450);
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
}
