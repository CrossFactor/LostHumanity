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
	 * moveSpeed = .25f
	 * distanceFromTopAttack = 30?
	 * indexLastAttackFrame =  3
	 * indexStartAttackFrame = 2
	 * indexEndAttackFrame = 3
	 */
	public Rabbit() {
		super(new BattleCharacterInfo("rabbit", 40, 10, 200, 150, 30, 47, 0, 20, 0, 20, 15, 0.25f, 0, 3, 2, 3, 50));
	}
	
	public void setMonsterSheets() throws SlickException{
		int battleWidth = 200;
		int battleHeight = 150;
		SpriteSheet idleLeft = new SpriteSheet("res/monsters/rabbit/idle/idleLeft.png", battleWidth, battleHeight);
		SpriteSheet idleRight = new SpriteSheet("res/monsters/rabbit/idle/idleRight.png", battleWidth, battleHeight);
		SpriteSheet attackLeft = new SpriteSheet("res/monsters/rabbit/attackMelee/attackLeft.png", battleWidth, battleHeight);
		SpriteSheet attackRight = new SpriteSheet("res/monsters/rabbit/attackMelee/attackRight.png", battleWidth, battleHeight);
		SpriteSheet battleMoveLeft = new SpriteSheet("res/monsters/rabbit/move/moveLeft.png", battleWidth, battleHeight);
		SpriteSheet battleMoveRight = new SpriteSheet("res/monsters/rabbit/move/moveRight.png", battleWidth, battleHeight);
		SpriteSheet deadLeft = new SpriteSheet("res/monsters/rabbit/death/deadLeft.png", battleWidth, battleHeight);
		SpriteSheet deadRight = new SpriteSheet("res/monsters/rabbit/death/deadRight.png", battleWidth, battleHeight);
		List<SpriteSheet> idle = new ArrayList<SpriteSheet>();
		List<SpriteSheet> attack = new ArrayList<SpriteSheet>();
		List<SpriteSheet> battleMove = new ArrayList<SpriteSheet>();
		Image[] hitLeftImages = { new Image("res/monsters/rabbit/attackMelee/hitLeft.png"),
				new Image("res/monsters/rabbit/attackMelee/hitLeft.png"),
				new Image("res/monsters/rabbit/attackMelee/hitLeft.png"),
				new Image("res/monsters/rabbit/attackMelee/hitLeft.png") };
		Image[] hitRightImages = { new Image("res/monsters/rabbit/attackMelee/hitRight.png"),
				new Image("res/monsters/rabbit/attackMelee/hitRight.png"),
				new Image("res/monsters/rabbit/attackMelee/hitRight.png"),
				new Image("res/monsters/rabbit/attackMelee/hitRight.png") };
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