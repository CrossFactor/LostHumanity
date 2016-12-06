package game.characters.monsters;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.characters.BattleCharacterInfo;

public class Teru extends Monster {
	/*
	 * hp = 80
	 * damage = 15
	 * widthBattle = 100
	 * heightBattle = 80
	 * hitboxWidth = 30
	 * hitboxHeight = 55
	 * distanceFromTop = 4
	 * hurtboxWidth = 50?
	 * gapFromCenter = 0? 
	 * healthbarDistance = 20
	 * aggression = 20
	 * moveSpeed = .15f
	 * distanceFromTopAttack = 10 //distance from top of height to the top of hurtbox 
	 * indexLastAttackFrame = 5 TEMPORARY 
	 * indexStartAttackFrame = 3 TEMPORARY 
	 * indexEndAttackFrame = 5 TEMPORARY
	 */
	public Teru() {
		super(new BattleCharacterInfo("teru", 80, 15, 100, 80, 40, 55, 4, 50, 0, 20, 20, 0.15f, 10, 5, 3, 5, 0));
	}

	public void setMonsterSheets() throws SlickException {
		int battleWidth = 100;
		int battleHeight = 80;
		SpriteSheet idleLeft = new SpriteSheet("res/monsters/teru/idle/idleLeft.png", battleWidth, battleHeight);
		SpriteSheet idleRight = new SpriteSheet("res/monsters/teru/idle/idleRight.png", battleWidth, battleHeight);
		SpriteSheet attackLeft = new SpriteSheet("res/monsters/teru/attackMelee/attackLeft.png", battleWidth, battleHeight);
		SpriteSheet attackRight = new SpriteSheet("res/monsters/teru/attackMelee/attackRight.png", battleWidth, battleHeight);
		SpriteSheet battleMoveLeft = new SpriteSheet("res/monsters/teru/move/moveLeft.png", battleWidth, battleHeight);
		SpriteSheet battleMoveRight = new SpriteSheet("res/monsters/teru/move/moveRight.png", battleWidth, battleHeight);
		SpriteSheet deadLeft = new SpriteSheet("res/monsters/teru/death/deadLeft.png", battleWidth, battleHeight);
		SpriteSheet deadRight = new SpriteSheet("res/monsters/teru/death/deadRight.png", battleWidth, battleHeight);
		
		List<SpriteSheet> idle = new ArrayList<SpriteSheet>();
		List<SpriteSheet> attack = new ArrayList<SpriteSheet>();
		List<SpriteSheet> battleMove = new ArrayList<SpriteSheet>();
		Image[] hitLeftImages = { new Image("res/monsters/teru/attackMelee/hitLeft.png"),
				new Image("res/monsters/teru/attackMelee/hitLeft.png"),
				new Image("res/monsters/teru/attackMelee/hitLeft.png"),
				new Image("res/monsters/teru/attackMelee/hitLeft.png") };
		Image[] hitRightImages = { new Image("res/monsters/teru/attackMelee/hitRight.png"),
				new Image("res/monsters/teru/attackMelee/hitRight.png"),
				new Image("res/monsters/teru/attackMelee/hitRight.png"),
				new Image("res/monsters/teru/attackMelee/hitRight.png") };
		Animation hitLeft = new Animation(hitLeftImages, 500, true);
		Animation hitRight = new Animation(hitRightImages, 500, true);
		idle.add(idleLeft);
		idle.add(idleRight);
		attack.add(attackLeft);
		attack.add(attackRight);
		battleMove.add(battleMoveLeft);
		battleMove.add(battleMoveRight);
		setSpriteSheets(idle, attack, battleMove);
		setHitAnimations(hitLeft, hitRight);
		setDeathAnimations(deadLeft, deadRight);
	}
}