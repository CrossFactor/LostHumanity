package game.characters;

import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class NPC {
	private Animation animation, movingUp, movingDown, movingLeft, movingRight, idleUp, idleDown, idleLeft, idleRight;
	private float xPosOut = 50;
	private float yPosOut = 3;
	private int direction = 1;
	private NPCInfo info;

	public NPC(NPCInfo info) {
		this.setInfo(info);
	}

	// functionality
	public void moveUp(int delta) {
		setAnimation(movingUp);
		setyPosOut(getyPosOut() - delta * .125f);
		setDirection(0);
	}

	public void moveDown(int delta) {
		setAnimation(movingDown);
		setyPosOut(getyPosOut() + delta * .125f);
		setDirection(1);
	}

	public void moveLeft(int delta) {
		setAnimation(movingLeft);
		setxPosOut(getxPosOut() - delta * .125f);
		setDirection(2);
	}

	public void moveRight(int delta) {
		setAnimation(movingRight);
		setxPosOut(getxPosOut() + delta * .125f);
		setDirection(3);
	}

	public void faceUp() {
		setAnimation(idleUp);
	}

	public void faceDown() {
		setAnimation(idleDown);
	}

	public void faceLeft() {
		setAnimation(idleLeft);
	}

	public void faceRight() {
		setAnimation(idleRight);
	}

	public void stopMoving() {
		switch (direction) {
		case 0:
			faceUp();
			break;
		case 1:
			faceDown();
			break;
		case 2:
			faceLeft();
			break;
		case 3:
			faceRight();
			break;
		}
		resetAnimations();
	}

	public void resetAnimations() {
		movingUp.restart();
		movingDown.restart();
		movingLeft.restart();
		movingRight.restart();
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public float getyPosOut() {
		return yPosOut;
	}

	public void setyPosOut(float yPosOut) {
		this.yPosOut = yPosOut;
	}

	public float getxPosOut() {
		return xPosOut;
	}
	public float getCenterX() {
		return xPosOut + (info.getWidth() / 2.0f);
	}
	public float getCenterY() {
		return yPosOut + (info.getHeight() / 2.0f);
	}
	public void setxPosOut(float xPosOut) {
		this.xPosOut = xPosOut;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void setMoveAnimation(List<SpriteSheet> move) throws SlickException {
		movingUp = new Animation(move.get(0), 450);
		movingDown = new Animation(move.get(1), 450);
		movingLeft = new Animation(move.get(2), 450);
		movingRight = new Animation(move.get(3), 450);
		movingUp.setPingPong(true);
		movingDown.setPingPong(true);
		movingLeft.setPingPong(true);
		movingRight.setPingPong(true);
		
		Image[] iUp = { movingUp.getImage(1) };
		Image[] iDown = { movingDown.getImage(1) };
		Image[] iLeft = { movingLeft.getImage(1) };
		Image[] iRight = { movingRight.getImage(1) };
		
		idleUp = new Animation(iUp, 500, true);
		idleDown = new Animation(iDown, 500, true);
		idleLeft = new Animation(iLeft, 500, true);
		idleRight = new Animation(iRight, 500, true);
		
	}

	public NPCInfo getInfo() {
		return info;
	}

	public void setInfo(NPCInfo info) {
		this.info = info;
	}
}
