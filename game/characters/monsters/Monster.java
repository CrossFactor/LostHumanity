package game.characters.monsters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;
import game.characters.*;

public abstract class Monster extends BattleCharacter {
	private BattleCharacterInfo info;
	Boolean isMoving;
	private int widthOut = 0;
	private int heightOut = 0;
	private int widthBattle = 0;
	private int heightBattle = 0;
	private float xPosOut = 0;
	private float yPosOut = 0;
	private int direction = 0;
	
	public Monster(BattleCharacterInfo info) {
		super(info);
		this.info = info;
	}

	@Override
	public void battle(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
	}

	public void setMonsterSheets() throws SlickException {
		SpriteSheet idleLeft = new SpriteSheet("res/monsters/" + getInfo().getName() + "/idle/idleLeft.png", 300, 200);
		SpriteSheet idleRight = new SpriteSheet("res/monsters/" + getInfo().getName() + "/idle/idleRight.png", 300, 200);
		SpriteSheet attackLeft = new SpriteSheet("res/monsters/" + getInfo().getName() + "/attackMelee/attackMeleeLeft.png", 300, 200);
		SpriteSheet attackRight = new SpriteSheet("res/monsters/" + getInfo().getName() + "/attackMelee/attackMeleeRight.png", 300, 200);
		SpriteSheet battleMoveLeft = new SpriteSheet("res/monsters/" + getInfo().getName() + "/move/moveLeft.png", 300, 200);
		SpriteSheet battleMoveRight = new SpriteSheet("res/monsters/" + getInfo().getName() + "/move/moveRight.png", 300, 200);
		List<SpriteSheet> idle = new ArrayList<SpriteSheet>();
		List<SpriteSheet> attack = new ArrayList<SpriteSheet>();
		List<SpriteSheet> battleMove = new ArrayList<SpriteSheet>();
		idle.add(idleLeft);
		idle.add(idleRight);
		attack.add(attackLeft);
		attack.add(attackRight);
		battleMove.add(battleMoveLeft);
		battleMove.add(battleMoveRight);
		super.setSpriteSheets(idle, attack, battleMove);
	}
}