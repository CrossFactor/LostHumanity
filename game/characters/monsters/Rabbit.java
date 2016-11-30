package game.characters.monsters;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.characters.BattleCharacterInfo;

public class Rabbit extends Monster {
	/*
	 * hp = 40
	 * damage = 10
	 * widthBattle = 200
	 * heightBattle = 150
	 * hitboxWidth = 30
	 * hitboxHeight = 47
	 * distanceFromTop = 4
	 * hurtboxWidth = 10?
	 * gapFromCenter = 0?
	 * healthbarDistance = 20
	 * aggression = 10
	 * moveSpeed = .3f
	 * distanceFromTopAttack = 30?
	 * indexLastAttackFrame =  5 TEMP
	 * indexStartAttackFrame = 2 TEMP
	 * indexEndAttackFrame = 3 TEMP
	 */
	public Rabbit() {
		super(new BattleCharacterInfo("teru", 40, 10, 100, 80, 40, 55, 4, 10, 0, 20, 10, 0.15f, 30, 2, 2, 3));
	}
	
	public void setMonsterSheets() throws SlickException{
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