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
import game.states.Play;
import game.util.Bar;
import game.util.Debug;
import game.util.Healthbar;
import game.util.Staminabar;

public abstract class Hero extends BattleCharacter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	private Animation movingUp, movingDown, movingLeft, movingRight, idleUp, idleDown, idleLeft, idleRight,
			battleFoundStance;
	private Boolean moving = false;
	private Boolean jumping = false;
	private Boolean falling = false;
	private Boolean blocking = false;
	private static final float DEFAULT_X = 50;
	private static final float DEFAULT_Y = 300;
	private int widthOut = 45;
	private int heightOut = 80;
	private float xPosOut = 50;
	private float yPosOut = 3;
	private int direction = 4;
	private int heightJumped = 0;
	private int airTime = 0;
	private Animation jumpLeft, jumpRight;
	private Animation blockLeft, blockRight;
	private int battleSelection = 0;
	private int potions = 0;
	private int stamina;
	private int staminaRegenRate, normalStaminaRegenRate, slowerStaminaRegenRate; //bigger = slower
	private Long staminaRegenCtr = 0L;
	private int staminaRegenCap;
	private int attackStaminaCost;
	private Long ctrPerOneStamina = 0L;
	private Bar staminabar;

	private Boolean enableJump = false; //this is to enable jumping in the actual game

	
	// abstraction
	public abstract void setHeroSprites() throws SlickException;
	
	//
	public Hero(BattleCharacterInfo info) {
		super(info);
		setHealthbar(new Healthbar(getInfo().getMaxHp(), 0, 0, this));
		setStamina(100);
		setStaminabar(new Staminabar(getStamina(), 0, 0, this));
		//balance out stamina regen here
		setNormalStaminaRegenRate(30);
		setSlowerStaminaRegenRate(50);
		setStaminaRegenCap(1200);
		setAttackStaminaCost(20);
	}
	
	public void reset() {
		setxPosBattle(DEFAULT_X);
		setyPosBattle(DEFAULT_Y);
	}

	// controls for hero outside combat
	public void moveOut(GameContainer gc, StateBasedGame sbg, int delta, Input input, Image mapWalls,
			Image mapTransition) { // 0
		// up
		// 1
		// down
		// 2
		// left
		// 3
		// right
		moving = false; //moving is by default false unless user inputs otherwise
		
		if (input.isKeyDown(Input.KEY_W)) { // 206
			Color c = mapWalls.getColor(((int)xPosOut + 22), ((int)yPosOut + 65));
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveUp(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_S)) {// -162
			Color c = mapWalls.getColor(((int) xPosOut + 22), ((int) yPosOut + 75));
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveDown(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_A)) { // 404
			Color c = mapWalls.getColor(((int) xPosOut + 6), ((int) yPosOut + 70));
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveLeft(delta);
			}
		}

		else if (input.isKeyDown(Input.KEY_D)) {// -381
			Color c = mapWalls.getColor(((int) xPosOut + 38), ((int) yPosOut + 70));
			Boolean noCollision = c.a == 0f;
			if (noCollision) {
				moveRight(delta);
			}
		}

		Color trans = mapTransition.getColor(((int) xPosOut + 20), ((int) yPosOut + 70));
		Boolean transition = trans.a != 0f;
		if (transition) {
			Play.nextLevel();
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
			int battle = (new Random()).nextInt(3499) + 1;
			if (battle == 6) {
				battleFoundStance.restart();
				setAnimation(battleFoundStance);
				return true;
			}
		}
		return false;
	}
	
	//this is called to update BattleCharacter with hero class
	public void battle(GameContainer gc, StateBasedGame sbg, int delta) {
		getAnimation().start();
		staminaRegen(delta);
		getAnimation().update(delta);
		battleInput(gc, sbg, delta);
	}

	// controls for hero during battle
	public void battleInput(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();
		moving = false; // moving is false by default incase user doesn't input anything
		if (isAlive()) {
			// sends player back to menu TEMPORARY
			if (Debug.debugMode == true) {
				if (input.isKeyDown(Input.KEY_M)) {
					sbg.enterState(3);
				}
			}
			// despawns hitbox after end of hitbox window of attack animation
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexEndAttackFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexEndAttackFrame())) {
				getGeneralBoxes().setHurtbox(null);
			}
			// stops attack animation at last frame of attack animation
			if (getAnimation().getCurrentFrame() == attackLeft.getImage(info.getIndexLastFrame())
					|| getAnimation().getCurrentFrame() == attackRight.getImage(info.getIndexLastFrame())) {
				stopAttack(); // sets isAttacking() to false
				
			} else if (isHit()) {
				hit();
			} else if (isAttacking() == true) {
				startAttack();
			} else if (isAttacking() == false && isHit() == false) {  //can only perform actions if is not attacking and not hit
				//controls for block
				if (input.isKeyDown(Input.KEY_SPACE)) {
					block();
				} else {
					stopBlock();
				}
				
				if (!blocking) { //blocking takes priority over all other controls, you cannot move or attack while blocking
					if (input.isKeyPressed(Input.KEY_K) && isOnCooldown() == false && isJumping() == false
							&& isFalling() == false && stamina > 0) {
							startAttack(); // sets isAttacking() to true
							useStamina(attackStaminaCost);
					}
					//moves left
					else if (input.isKeyDown(Input.KEY_A)) {
						if (getGeneralBoxes().getHitbox().getX() - 10 > 0) {
							if (inAir()) {
								jumpMoveLeft(delta);
							} else {
								battleMoveLeft(delta);
							}
							moving = true;
							setBattleDirection(1);
						}
					}
					//moves right
					else if (input.isKeyDown(Input.KEY_D)) {
						if (getGeneralBoxes().getHitbox().getX() + getGeneralBoxes().getHitbox().getWidth()
								+ 10 < 800) {
							if (inAir()) {
								jumpMoveRight(delta);
							} else {
								battleMoveRight(delta);
							}
							moving = true;
							setBattleDirection(2);
						}
					}
					// logic for falling if jumping is enabled
					else if (inAir() && input.isKeyDown(Input.KEY_A) == false
							&& input.isKeyDown(Input.KEY_D) == false) {
						if (getBattleDirection() == 1) {
							setAnimation(jumpLeft);
						} else {
							setAnimation(jumpRight);
						}
					}

					if (input.isKeyPressed(Input.KEY_W) && isJumping() == false && isFalling() == false && enableJump) {
						jump();
					}
					if (inAir()) {
						jumpLogic(delta);
					}
					if (moving == false && inAir() == false) {
						resetIdle();
					}
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

	private void stopBlock() {
		blocking = false;
		staminaRegenRate = normalStaminaRegenRate;
		resetIdle();
	}

	private void block() {
		blocking = true;
		staminaRegenRate = slowerStaminaRegenRate;
		if (getBattleDirection() == 1) {
			setAnimation(blockLeft);
		} else {
			setAnimation(blockRight);
		}
	}


	public void jump() {
		jumping = true;
		if (getBattleDirection() == 1) {
			jumpLeft();
		} else {
			jumpRight();
		}
	}

	public void fall() {
		jumping = false;
		falling = true;
		if (getBattleDirection() == 1) {
			jumpLeft();
		} else {
			jumpRight();
		}
	}

	public void land() {
		jumping = false;
		falling = false;
	}

	public void jumpRight() {
		setAnimation(jumpRight);
	}

	public void jumpLeft() {
		setAnimation(jumpLeft);
	}

	public void jumpMoveRight(int delta) {
		if (inMapBounds()) {
			jumpRight();
			setxPosBattle(getxPosBattle() + delta * getInfo().getMoveSpeed());
		}
	}

	public void jumpMoveLeft(int delta) {
		if (inMapBounds()) {
			jumpLeft();
			setxPosBattle(getxPosBattle() - delta * getInfo().getMoveSpeed());
		}
	}

	private void jumpLogic(int delta) {
		int maxJumpHeight = 200;
		float jumpSpeed = 0.35f;
		if (isJumping()) {
			setyPosBattle(getyPosBattle() - delta * jumpSpeed);
			heightJumped++;
			if (heightJumped > maxJumpHeight) {
				fall();
			}
		} else if (isFalling()) {
			if (airTime > 20) {
				setyPosBattle(getyPosBattle() + delta * jumpSpeed);
				heightJumped--;
				if (heightJumped <= 0) {
					land();
				}
			} else {
				airTime += delta * 0.5f;
			}
		}
	}

	public Boolean inAir() {
		return isJumping() || isFalling();
	}

	@Override
	public void updateHealthbar(GameContainer gc) {
		getHealthbar().update(130, 520);
		getStaminabar().update(130, 550);
	}
	
	public void staminaRegen(int delta){
		if(staminaRegenCtr < staminaRegenCap){
			staminaRegenCtr += delta;
		} else if (stamina <= 100){
			if (ctrPerOneStamina < staminaRegenRate){
				ctrPerOneStamina += delta;
			}else{
				ctrPerOneStamina = 0L;
				stamina += 1;
			}
			if(stamina > 100){
				stamina = 100;
			}
		}
	}
	
	public void useStamina(int amountUsed){
		staminaRegenCtr = 0L;
		stamina -= amountUsed;
		if (stamina < 0){
			stamina = 0;
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

	public float getCenterXOut() {
		return xPosOut - (widthOut / 2.0f);
	}

	public float getCenterYOut() {
		return yPosOut - (heightOut / 2.0f);
	}

	public float getxPosOut() {
		return xPosOut;
	}

	public void setBattleFoundStance(SpriteSheet battleFoundStance) {
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

	protected void setJumpAnimations(Animation jumpLeft, Animation jumpRight) {
		this.setJumpLeft(jumpLeft);
		this.setJumpRight(jumpRight);
	}

	public void setJumpLeft(Animation jumpLeft) {
		this.jumpLeft = jumpLeft;
	}

	public void setJumpRight(Animation jumpRight) {
		this.jumpRight = jumpRight;
	}

	public Boolean isJumping() {
		return jumping;
	}

	private boolean isFalling() {
		return falling;
	}

	public int getBattleSelection() {
		return battleSelection;
	}

	public void setBattleSelection(int selection) {
		this.battleSelection = selection;
	}

	public int getPotions() {
		return potions;
	}

	public void setPotions(int potions) {
		this.potions = potions;
	}

	public void setBlockAnimations(Animation blockLeft, Animation blockRight) {
		setBlockLeft(blockLeft);
		setBlockRight(blockRight);
	}

	public Animation getBlockLeft() {
		return blockLeft;
	}

	public void setBlockLeft(Animation blockLeft) {
		this.blockLeft = blockLeft;
	}

	public Animation getBlockRight() {
		return blockRight;
	}

	public void setBlockRight(Animation blockRight) {
		this.blockRight = blockRight;
	}

	public Boolean isBlocking() {
		return blocking;
	}

	
	public Staminabar getStaminabar() {
		return (Staminabar) staminabar;
	}


	public void setStaminabar(Bar staminabar) {
		this.staminabar = staminabar;
	}


	public int getStamina() {
		return stamina;
	}


	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	private void setStaminaRegenCap(int staminaRegenCap) {
		this.staminaRegenCap = staminaRegenCap;
	}

	private void setAttackStaminaCost(int attackStaminaCost) {
		this.attackStaminaCost = attackStaminaCost;
	}
	

	private void setNormalStaminaRegenRate(int normalStaminaRegenRate) {
		this.normalStaminaRegenRate = normalStaminaRegenRate;
	}


	private void setSlowerStaminaRegenRate(int slowerStaminaRegenRate) {
		this.slowerStaminaRegenRate = slowerStaminaRegenRate;
	}


}
