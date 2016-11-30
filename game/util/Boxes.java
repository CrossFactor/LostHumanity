package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Boxes {

	private Box hitbox, hurtbox;
	private Box aggressionBoxLeft, aggressionBoxRight;
	private Box attackBox;
	//private Box targetRightBox;

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
	public Box getAggressionBoxLeft() {
		return aggressionBoxLeft;
	}

	public void setAggressionBoxLeft(Box aggressionBoxLeft) {
		this.aggressionBoxLeft = aggressionBoxLeft;
	}

	
	public Box getAggressionBoxRight() {
		return aggressionBoxRight;
	}

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

//	public Box getTargetRightBox() {
//		return targetRightBox;
//	}
//
//	public void setTargetRightBox(Box targetRightBox) {
//		this.targetRightBox = targetRightBox;
//	}
//
//	public void drawTargetRightBox(Graphics g) {
//		targetRightBox.draw(g);
//	}

}
