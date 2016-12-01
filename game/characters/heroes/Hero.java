package game.characters.heroes;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.StateBasedGame;
import game.characters.*;
import game.util.Debug;

public abstract class Hero extends BattleCharacter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Animation movingUp, movingDown, movingLeft, movingRight, idleUp, idleDown, idleLeft, idleRight, battleFoundStance;
	private Boolean moving = false;
	private static final float DEFAULT_X = 50;
	private static final float DEFAULT_Y = 300;
	private int widthOut = 45;
	private int heightOut = 80;
	private float xPosOut = 50;
	private float yPosOut = 3;
	private int direction = 4;

	public Hero(BattleCharacterInfo info) {
		super(info);
	}

	public void reset() {
		setxPosBattle(DEFAULT_X);
		setyPosBattle(DEFAULT_Y);
	}

	// controls for hero outside combat
	public void moveOut(GameContainer gc, StateBasedGame sbg, int delta, Input input, Image worldMapWalls, int mapX, int mapY) { // 0
																												// up
																												// 1
																												// down
																												// 2
																												// left
																												// 3
																												// right
		moving = false;
		// upper right 42 x 80
		// lower left 12 x 90
		// lower right 36 x 90
		// upper left 5 x 80
		if (input.isKeyDown(Input.KEY_W)) { // 206
			Color c = worldMapWalls.getColor(((int) xPosOut + 22) - mapX, ((int) yPosOut + 65) - mapY);
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveUp(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_S)) {// -162
			Color c = worldMapWalls.getColor(((int) xPosOut + 22) - mapX, ((int) yPosOut + 75) - mapY);
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveDown(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_A)) { // 404
			Color c = worldMapWalls.getColor(((int) xPosOut + 6) - mapX, ((int) yPosOut + 70) - mapY);
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveLeft(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_D)) {// -381
			Color c = worldMapWalls.getColor(((int) xPosOut + 38) - mapX, ((int) yPosOut + 70) - mapY);
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveRight(delta);
			}
		}

		if (moving == false) {
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
	public Boolean findBattle() {
		if (moving == true) {
			int battle = (new Random()).nextInt(2999) + 1;
			if (battle == 6) {
				setAnimation(battleFoundStance);
				getAnimation().stop();
				return true;
			}
		}
		return false;
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
		moving = false; // 0 up 1 down 2 left 3 right
		if (isAlive()) {
			// sends player back to menu TEMPORARY
			if (Debug.debugMode == true) {
				if (input.isKeyDown(Input.KEY_M)) {
					sbg.enterState(3);
				}
			}
			// despawns hitbox after end attack frame
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexEndAttackFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexEndAttackFrame())) {
				getGeneralBoxes().setHurtbox(null);
			}
			// stops attack animation at last frame
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexLastFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexLastFrame())) {
				stopAttack(); // sets isAttacking() to false

			} else if (isHit()) {
				hit();
			} else if (isAttacking() == true) {
				startAttack();
			} else if (isAttacking() == false && isHit() == false) { // Movement.
				// Can only
				// move if
				// is
				// not attacking and not hit

				if (input.isKeyPressed(Input.KEY_K)) {
					startAttack(); // sets isAttacking() to true
				}

				else if (input.isKeyDown(Input.KEY_A)) {
					if (getGeneralBoxes().getHitbox().getX() - 10 > 0) {
						battleMoveLeft(delta);
						moving = true;
						setBattleDirection(1);
					}
				}

				else if (input.isKeyDown(Input.KEY_D)) {
					if (getGeneralBoxes().getHitbox().getX() + getGeneralBoxes().getHitbox().getWidth() + 10 < 800) {
						battleMoveRight(delta);
						moving = true;
						setBattleDirection(2);
					}

				}

				if (moving == false) {
					resetIdle();
				}
			}
		}
	}

	// functionality
	public void moveUp(int delta) {
		setAnimation(movingUp);
		setyPosOut(getyPosOut() - delta * .125f);
		moving = true;
		direction = 0;
	}

	public void moveDown(int delta) {
		setAnimation(movingDown);
		setyPosOut(getyPosOut() + delta * .125f);
		moving = true;
		direction = 1;
	}

	public void moveLeft(int delta) {
		setAnimation(movingLeft);
		setxPosOut(getxPosOut() - delta * .125f);
		moving = true;
		direction = 2;
	}

	public void moveRight(int delta) {
		setAnimation(movingRight);
		setxPosOut(getxPosOut() + delta * .125f);
		moving = true;
		direction = 3;
	}

	public void faceUp() {
		setAnimation(idleUp);
		getAnimation().start();
	}

	public void faceDown() {
		setAnimation(idleDown);
		getAnimation().start();
	}

	public void faceLeft() {
		setAnimation(idleLeft);
		getAnimation().start();
	}

	public void faceRight() {
		setAnimation(idleRight);
		getAnimation().start();
	}

	@Override
	public void updateHealthbar(GameContainer gc) {
		getHealthbar().update(105, 520);
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

	public abstract void setHeroSprites() throws SlickException;

	public float getxPosOut() {
		return xPosOut;
	}
	
	public void setBattleFoundStance(SpriteSheet battleFoundStance){
		this.battleFoundStance = new Animation(battleFoundStance, 500);
	}
	protected void setHitAnimations(Animation hitLeft, Animation hitRight) {
		super.setHitLeft(hitLeft);
		super.setHitRight(hitRight);
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
	
	protected void setDeathAnimations(SpriteSheet deadLeft, SpriteSheet deadRight) {
		Animation left = new Animation(deadLeft, 500);
		Animation right = new Animation(deadRight, 500);
		super.setDeadLeft(left);
		super.setDeadRight(right);
	}
}
