package game.characters.monsters;
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
import game.characters.*;

public abstract class Monster extends BattleCharacter {
	private Animation movingLeft, movingRight, idleLeft, idleRight;
	Boolean isMoving;
	private int widthOut = 0;
	private int heightOut = 0;
	private int widthBattle = 0;
	private int heightBattle = 0;
	private float xPosOut = 0;
	private float yPosOut = 0;
	private int direction = 0;
	private int aggression = 0;
	
	public Monster(String name, int hp, int aggression, int width, int height) {
		super(name, hp, aggression, width, height);
		this.aggression = aggression * 20;
	}

	@Override
	public void battle(GameContainer gc, StateBasedGame sbg, int delta) {
		// TODO Auto-generated method stub
		
	}
	public void moveLeft() {
		setAnimation(movingLeft);
	}

	public void moveRight() {
		setAnimation(movingRight);
	}

	public void faceLeft() {
		setAnimation(idleLeft);
	}

	public void faceRight() {
		setAnimation(idleRight);
	}

	public void setSpriteSheets(List<SpriteSheet> move, List<SpriteSheet> idle, List<SpriteSheet> attack,
			List<SpriteSheet> battleMove) {

		this.movingLeft = new Animation(move.get(0), 0);
		this.movingRight = new Animation(move.get(0), 0);

		this.movingLeft.setPingPong(true);
		this.movingRight.setPingPong(true);
	
		Image[] idleLeft = { this.movingLeft.getImage(0), this.movingLeft.getImage(0) };
		Image[] idleRight = { this.movingRight.getImage(0), this.movingRight.getImage(0) };
		
		this.idleLeft = new Animation(idleLeft, 0, false);
		this.idleRight = new Animation(idleRight, 0, false);
		super.setSpriteSheets(idle, attack, battleMove);

	}
}