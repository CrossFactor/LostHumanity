package game.characters.heroes;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import game.Attack;
import game.characters.*;

public abstract class Hero extends GameCharacter {
	private Animation movingUp, movingDown, movingLeft, movingRight, idleUp, idleDown, idleLeft, idleRight;
	Boolean isMoving;
	private int widthOut = 45;
	private int heightOut = 80;
	private int widthBattle = 180;
	private int heightBattle = 170;
	private float xPosOut = 376;
	private float yPosOut = 3;
	private int direction = 0;

	public Hero(String name, int hp) {
		super(name, hp);
	}
	
	public void reset() {
		setxPosBattle(400);
		setyPosBattle(400);
	}

	public void moveOut(GameContainer gc, StateBasedGame sbg, int delta, Input input, Image worldMapWalls) { // 0
																												// up
																												// 1
																												// down
																												// 2
																												// left
																												// 3
																												// right
		// Sound.bg.loop();
		isMoving = false;
		if (input.isKeyDown(Input.KEY_W)) { // 206
			Color c = worldMapWalls.getColor((int) xPosOut, (int) yPosOut + heightOut - 20);
			Boolean collision = c.a == 0f;
			if (collision) {
				moveUp();
				setyPosOut(getyPosOut() - delta * .125f);
				isMoving = true;
				direction = 0;
			}
		}

		else if (input.isKeyDown(Input.KEY_S)) {// -162
			Color c = worldMapWalls.getColor((int) xPosOut, (int) yPosOut + heightOut);
			Boolean collision = c.a == 0f;
			if (collision) {
				moveDown();
				setyPosOut(getyPosOut() + delta * .125f);
				isMoving = true;
				direction = 1;
			}
		}

		else if (input.isKeyDown(Input.KEY_A)) { // 404
			Color c = worldMapWalls.getColor((int) xPosOut - 3, (int) yPosOut + heightOut - 15);
			Boolean collision = c.a == 0f;
			if (collision) {
				moveLeft();
				setxPosOut(getxPosOut() - delta * .125f);
				isMoving = true;
				direction = 2;
			}
		}

		else if (input.isKeyDown(Input.KEY_D)) {// -381
			Color c = worldMapWalls.getColor((int) xPosOut + widthOut, (int) yPosOut + heightOut - 15);
			Boolean collision = c.a == 0f;
			if (collision) {
				moveRight();
				setxPosOut(getxPosOut() + delta * .125f);
				isMoving = true;
				direction = 3;
			}
		}

		if (isMoving == false) {
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
		}
	}

	public void findBattle(Music music, StateBasedGame sbg) {
		if (isMoving == true) {
			int battle = (new Random()).nextInt(6999) + 1;
			if (battle == 6) {
				music.stop();
				sbg.enterState(2);
			}
		}
	}

	public void moveBattle(GameContainer gc, StateBasedGame sbg, int delta, Input input) {
		Boolean isMoving = false; // 0 up 1 down 2 left 3 right
		// Sound.bg.loop();
		getAnimation().start();
		getAnimation().update(delta);
		
		if (input.isKeyPressed(Input.KEY_K)) {
			startAttack(); // sets isAttacking() to true
		}
		
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(4)
				|| getAnimation().getCurrentFrame() == attackRight.getImage(4)) {
			stopAttack(); // sets isAttacking() to false
			if (direction == 2) {
				battleFaceLeft();
			} else {
				battleFaceRight();
			}
		}
		
		if (isAttacking() == true) {
			if (direction == 2) {
				attackLeft();
			} else {
				attackRight();
			}

		} else if (isAttacking() == false) { // Movement. Can only move is is
												// not attacking
			if (input.isKeyDown(Input.KEY_A)) {
				battleMoveLeft();
				setxPosBattle(getxPosBattle() - delta * .175f);
				isMoving = true;
				direction = 2;
			}

			else if (input.isKeyDown(Input.KEY_D)) {
				battleMoveRight();
				setxPosBattle(getxPosBattle() + delta * .175f);
				isMoving = true;
				direction = 3;
			}

			if (isMoving == false) {
				switch (direction) {
				case 2:
					battleFaceLeft();
					break;
				case 3:
					battleFaceRight();
					break;
				}
			}
		}
	}

	public void moveUp() {
		setAnimation(movingUp);
	}

	public void moveDown() {
		setAnimation(movingDown);
	}

	public void moveLeft() {
		setAnimation(movingLeft);
	}

	public void moveRight() {
		setAnimation(movingRight);
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

	public void setSpriteSheets(List<SpriteSheet> move, List<SpriteSheet> idle, List<SpriteSheet> attack,
			List<SpriteSheet> battleMove) {
		this.movingUp = new Animation(move.get(0), 450);
		this.movingDown = new Animation(move.get(1), 450);
		this.movingLeft = new Animation(move.get(2), 450);
		this.movingRight = new Animation(move.get(3), 450);
		this.movingUp.setPingPong(true);
		this.movingDown.setPingPong(true);
		this.movingLeft.setPingPong(true);
		this.movingRight.setPingPong(true);
		Image[] idleUp = { this.movingUp.getImage(1), this.movingUp.getImage(1) };
		Image[] idleDown = { this.movingDown.getImage(1), this.movingDown.getImage(1) };
		Image[] idleLeft = { this.movingLeft.getImage(1), this.movingLeft.getImage(1) };
		Image[] idleRight = { this.movingRight.getImage(1), this.movingRight.getImage(1) };
		this.idleUp = new Animation(idleUp, 450, false);
		this.idleDown = new Animation(idleDown, 450, false);
		this.idleLeft = new Animation(idleLeft, 450, false);
		this.idleRight = new Animation(idleRight, 450, false);
		super.setSpriteSheets(idle, attack, battleMove);

	}

	public float getxPosOut() {
		return xPosOut;
	}

	public void setxPosOut(float xPosOut) {
		this.xPosOut = xPosOut;
	}

	public float getyPosOut() {
		return yPosOut;
	}

	public void setyPosOut(float yPosOut) {
		this.yPosOut = yPosOut;
	}

	public int getWidthOut() {
		return widthOut;
	}

	public void setWidthOut(int widthOut) {
		this.widthOut = widthOut;
	}

	public int getHeightOut() {
		return heightOut;
	}

	public void setHeightOut(int heightOut) {
		this.heightOut = heightOut;
	}

	public int getWidthBattle() {
		return widthBattle;
	}

	public void setWidthBattle(int widthBattle) {
		this.widthBattle = widthBattle;
	}

	public int getHeightBattle() {
		return heightBattle;
	}

	public void setHeightBattle(int heightBattle) {
		this.heightBattle = heightBattle;
	}

	public Rectangle hitBoxOut() {
		return new Rectangle(getxPosOut(), getyPosOut(), getWidthOut(), getHeightOut());
	}

	public Rectangle hitBoxBattle() {
		return null;
	}
}
