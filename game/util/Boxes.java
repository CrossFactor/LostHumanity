package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Boxes {

	private Box hitbox, hurtbox;
	private Box aggressionBoxLeft, aggressionBoxRight;
	private Box attackBox;

	public Boxes() {
	}

	// draw shit
	public void drawHitbox(Graphics g) {
		hitbox.draw(g);
	}

	public void drawHurtbox(Graphics g) throws SlickException {
		hurtbox.draw(g);
	}
	
	public void drawAttackBox(Graphics g){
		attackBox.draw(g);
	}

	// getters and setters
	/**
	 * @return the aggressionBoxLeft
	 */
	public Box getAggressionBoxLeft() {
		return aggressionBoxLeft;
	}

	/**
	 * @param aggressionBoxLeft
	 *            the aggressionBoxLeft to set
	 */
	public void setAggressionBoxLeft(Box aggressionBoxLeft) {
		this.aggressionBoxLeft = aggressionBoxLeft;
	}

	/**
	 * @return the aggressionBoxRight
	 */
	public Box getAggressionBoxRight() {
		return aggressionBoxRight;
	}

	/**
	 * @param aggressionBoxRight
	 *            the aggressionBoxRight to set
	 */
	public void setAggressionBoxRight(Box aggressionBoxRight) {
		this.aggressionBoxRight = aggressionBoxRight;
	}

	public Box getHitbox() {
		return hitbox;
	}

	public void setHitbox(Box hitbox) {
		this.hitbox = hitbox;
	}

	public Box getHurtbox() {
		return hurtbox;
	}

	public void setHurtbox(Box hurtbox) {
		this.hurtbox = hurtbox;
	}

	public Box getAttackBox() {
		return attackBox;
	}

	public void setAttackBox(Box attackBox) {
		this.attackBox = attackBox;
	}

	public void drawAggressionBoxes(Graphics g) {
		aggressionBoxLeft.draw(g);
		aggressionBoxRight.draw(g);
	}

}
