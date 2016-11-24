package game.characters.heroes;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import game.characters.*;
import game.util.AttackCooldown;
import game.util.BattleController;
import game.util.Hitbox;
import game.util.SpriteSheetLists;

public abstract class Hero extends BattleCharacter {
	private Animation movingUp, movingDown, movingLeft, movingRight, idleUp, idleDown, idleLeft, idleRight;
	private Boolean isMoving;
	private Boolean attackPressed = false;
	private static final float DEFAULT_X = 400;
	private static final float DEFAULT_Y = 300;
	private int widthOut = 45;
	private int heightOut = 80;
	private float xPosOut = 376;
	private float yPosOut = 3;
	private int direction = 0;
	private float hurtboxX;

	public Hero(BattleCharacterInfo info) {
		super(info);
	}

	public void reset() {
		setxPosBattle(DEFAULT_X);
		setyPosBattle(DEFAULT_Y);
	}

	// controls for hero outside combat
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

	// generates random number to find battle while moving
	public void findBattle(Music music, StateBasedGame sbg) {
		if (isMoving == true) {
			int battle = (new Random()).nextInt(5999) + 1;
			if (battle == 6) {
				music.stop();
				sbg.enterState(2);
			}
		}
	}

	// this is for hero control during battle
	public void battle(GameContainer gc, StateBasedGame sbg, int delta) {
		// Sound.bg.loop();
		Input input = gc.getInput();
		getAnimation().start();
		getAnimation().update(delta);
		battleInput(gc, sbg, delta, input);
	}

	// controls for hero during battle
	public void battleInput(GameContainer gc, StateBasedGame sbg, int delta, Input input) {
		Boolean isMoving = false; // 0 up 1 down 2 left 3 right
		Boolean spawnHurtbox = false;
		// sends player back to menu TEMPORARY
		if (input.isKeyDown(Input.KEY_M)) {
			sbg.enterState(1);
		}

		// stops attack animation at last frame
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(4)
				|| getAnimation().getCurrentFrame() == attackRight.getImage(4)) {
			stopAttack(); // sets isAttacking() to false after a few seconds of
							// cooldown
			if (direction == 2) {
				battleFaceLeft();
			} else {
				battleFaceRight();
			}
		}

		// spawns hurtbox at certain frame
		if (getAnimation().getCurrentFrame() == attackLeft.getImage(2)
				|| getAnimation().getCurrentFrame() == attackRight.getImage(2)
				|| getAnimation().getCurrentFrame() == attackLeft.getImage(3)
				|| getAnimation().getCurrentFrame() == attackRight.getImage(3)) {
			spawnHurtbox = true;
		}
		// makes hitbox only if attacking
		if (spawnHurtbox == true) {
			if (direction == 2) {
				setHurtboxX(getCenterX() - info.getHurtboxWidth() - info.getGapFromCenter());
				setHurtbox(new Hitbox(hurtboxX, getyPosBattle() + 30, info.getHurtboxWidth(),
						info.getHeightBattle() - 30));
			} else {
				setHurtboxX(getCenterX() + info.getGapFromCenter());
				setHurtbox(new Hitbox(hurtboxX, getyPosBattle() + 30, info.getHurtboxWidth(),
						info.getHeightBattle() - 30));
			}
			spawnHurtbox = false;
		}

		if (isAttacking() == true) {
			if (direction == 2) {
				attackLeft();
			} else {
				attackRight();
			}
		} else if (isAttacking() == false && isHit() == false && isOnCooldown() == false) { // Movement.
			// Can only
			// move if
			// is
			// not attacking and not hit

			if (input.isKeyDown(Input.KEY_K)) {
				startAttack(); // sets isAttacking() to true
			}

			if (input.isKeyDown(Input.KEY_A)) {
				battleMoveLeft();
				setxPosBattle(getxPosBattle() - delta * .2f);
				isMoving = true;
				direction = 2;
			}

			else if (input.isKeyDown(Input.KEY_D)) {
				battleMoveRight();
				setxPosBattle(getxPosBattle() + delta * .2f);
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

	// functionality
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

	public void face(int direction) {
		switch (direction) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}

	// getters and setters
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

	public float getHurtboxX() {
		return hurtboxX;
	}

	public void setHurtboxX(float hurtboxX) {
		this.hurtboxX = hurtboxX;
	}
}
