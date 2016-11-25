package game.characters.heroes;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import game.characters.BattleCharacterInfo;

public class Slayer extends Hero {
	public static final Slayer slayer = new Slayer();
	/*
	 * hp = 100 damage = 20 widthBattle = 300 heightBattle = 200 hitboxWidth =
	 * 30 hitboxHeight = 65 distanceFromTop = 81 hurtboxWidth = 80 gapFromCenter
	 * = 45 healthBarDistance = 0 aggression = 0 //player controlled moveSpeed =
	 * 0.2f distanceFromTopAttack = 30 indexOfLastFrame = 4;
	 * 
	 * indexLastAttackFrame = 4 indexStartAttackFrame = 2 indexEndAttackFrame =
	 * ?
	 */

	public Slayer() {
		super(new BattleCharacterInfo("slayer", 100, 20, 300, 200, 30, 65, 81, 80, 45, 0, 0, 0.2f, 30, 4, 2, 4));
	}

	public void setHeroSprites() throws SlickException {
		SpriteSheet moveUp = new SpriteSheet("res/slayer/out/moveUp.png", 45, 80);
		SpriteSheet moveDown = new SpriteSheet("res/slayer/out/moveDown.png", 45, 80);
		SpriteSheet moveLeft = new SpriteSheet("res/slayer/out/moveLeft.png", 45, 80);
		SpriteSheet moveRight = new SpriteSheet("res/slayer/out/moveRight.png", 45, 80);
		SpriteSheet idleLeft = new SpriteSheet("res/slayer/attack/idleLeft.png", 300, 200);
		SpriteSheet idleRight = new SpriteSheet("res/slayer/attack/idleRight.png", 300, 200);
		SpriteSheet attackLeft = new SpriteSheet("res/slayer/attack/attackLeft.png", 300, 200);
		SpriteSheet attackRight = new SpriteSheet("res/slayer/attack/attackRight.png", 300, 200);
		SpriteSheet battleMoveLeft = new SpriteSheet("res/slayer/attack/moveLeft.png", 300, 200);
		SpriteSheet battleMoveRight = new SpriteSheet("res/slayer/attack/moveRight.png", 300, 200);
		List<SpriteSheet> move = new ArrayList<SpriteSheet>();
		List<SpriteSheet> idle = new ArrayList<SpriteSheet>();
		List<SpriteSheet> attack = new ArrayList<SpriteSheet>();
		List<SpriteSheet> battleMove = new ArrayList<SpriteSheet>();
		Image[] hitLeftImages = { new Image("res/slayer/attack/hitLeft.png"),
				new Image("res/slayer/attack/hitLeft.png"), new Image("res/slayer/attack/hitLeft.png"),
				new Image("res/slayer/attack/hitLeft.png") };
		Image[] hitRightImages = { new Image("res/slayer/attack/hitRight.png"),
				new Image("res/slayer/attack/hitRight.png"), new Image("res/slayer/attack/hitRight.png"),
				new Image("res/slayer/attack/hitRight.png") };
		Animation hitLeft = new Animation(hitLeftImages, 500, true);
		Animation hitRight = new Animation(hitRightImages, 500, true);
		move.add(moveUp);
		move.add(moveDown);
		move.add(moveLeft);
		move.add(moveRight);
		idle.add(idleLeft);
		idle.add(idleRight);
		attack.add(attackLeft);
		attack.add(attackRight);
		battleMove.add(battleMoveLeft);
		battleMove.add(battleMoveRight);
		setSpriteSheets(move, idle, attack, battleMove);
		setHitAnimations(hitLeft, hitRight);
	}
}
