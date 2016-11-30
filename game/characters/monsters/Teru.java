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
	 * aggression = 10 
	 * moveSpeed = .15f
	 * distanceFromTopAttack = 10 //distance from top of height to the top of hurtbox 
	 * indexLastAttackFrame = 5 TEMPORARY 
	 * indexStartAttackFrame = 3 TEMPORARY 
	 * indexEndAttackFrame = 5 TEMPORARY
	 */
	public Teru() {
		super(new BattleCharacterInfo("teru", 80, 15, 100, 80, 40, 55, 4, 50, 0, 20, 15, 0.15f, 10, 5, 3, 5));
	}

	public void setMonsterSheets() throws SlickException {
		SpriteSheet idleLeft = new SpriteSheet("res/monsters/teru/idle/idleLeft.png", 100, 80);
		SpriteSheet idleRight = new SpriteSheet("res/monsters/teru/idle/idleRight.png", 100, 80);
		SpriteSheet attackLeft = new SpriteSheet("res/monsters/teru/attackMelee/attackLeft.png", 100, 80);
		SpriteSheet attackRight = new SpriteSheet("res/monsters/teru/attackMelee/attackRight.png", 100, 80);
		SpriteSheet battleMoveLeft = new SpriteSheet("res/monsters/teru/move/moveLeft.png", 100, 80);
		SpriteSheet battleMoveRight = new SpriteSheet("res/monsters/teru/move/moveRight.png", 100, 80);
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
	}
}