package game.states;

import java.util.Random;
import java.util.Vector;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import game.characters.heroes.*;
import game.characters.monsters.Monster;
import game.characters.monsters.Rabbit;
import game.characters.monsters.Teru;
import game.Game;
import game.util.Healthbar;
import game.util.Songs;
import game.util.Sounds;

public class Battle extends BasicGameState {
	Hero hero = Slayer.slayer;
	private Image[] battleMap;
	private Image gameOver;
	private Animation victory;
	private int selection = 0;
	private static final int DEFAULT_ENEMYCOUNT = 0;
	private int enemyCount = 0;
	private Vector<Monster> enemies;
	private float gameOverY = -400;
	private float victoryY = -601;
	private Long time = 0L;
	private Boolean playGOMusic = true;
	private Boolean drawHeroHitEffectBlock = false;
	private Boolean playVicMusic = true;

	public Battle(int state) {
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		hero.setBattleSelection(0);
		time = 0L;
		selection = 0;
		hero.getAnimation().stop();
		hero.stopAttack();
		hero.battleFaceRight();
		int enemyCount = new Random().nextInt(2) + 1;
		this.enemyCount = enemyCount;
		playGOMusic = true;
		playVicMusic = true;
		enemies = new Vector<Monster>();
		int pos = 500;
		for (int i = 0; i < enemyCount; i++) {
			addRandomEnemy(container, pos);
			pos += 100; // pos increments 50 to change spawn incase of 2 enemies
		}

		hero.reset();
		hero.getAnimation().start();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);
		enemyCount = DEFAULT_ENEMYCOUNT;
		enemies = null;
		hero.getAnimation().stop();
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		battleMap = new Image[] { new Image("res/backgrounds/battle/background.png"),
				new Image("res/backgrounds/battle/bbBlock.png"), new Image("res/backgrounds/battle/bbPotion0.png"),
				new Image("res/backgrounds/battle/bbPotion1.png"), new Image("res/backgrounds/battle/bbPotion2.png"),
				new Image("res/backgrounds/battle/bbPotion3.png") };
		gameOver = new Image("res/battle/gameOver.png");
		SpriteSheet vectory = new SpriteSheet("res/battle/victory.png", 800, 600);
		victory = new Animation(vectory, 300);
		hero.contructHitEffect();
		hero.battleFaceRight();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (selection < 2) {
			battleMap[selection].draw(0, 0);
		} else {
			battleMap[selection + hero.getPotions()].draw(0, 0);
		}
		if(drawHeroHitEffectBlock){
			hero.drawHitEffect();
			drawHeroHitEffectBlock = false;
		}
		g.setColor(Color.black);
		// g.drawString("HP: ", hero.getHealthbar().getX() - 40,
		// hero.getHealthbar().getY() - 2);
		renderMonsters(gc, sbg, g);
		hero.render(gc, sbg, g);
		hero.getStaminabar().draw(g);
		if (hero.isAlive() == false) {
			gameOver.draw(0, gameOverY);
		}
		if (enemyCount == 0) {
			victory.draw(0, 0);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		this.selection = hero.getBattleSelection();
		hero.update(gc, sbg, delta);
		updateMonsters(gc, sbg, delta);
		hurtboxLogic();
		if (hero.isAlive() == false) {
			if (playGOMusic) {
				Songs.gameOverBgm();
				playGOMusic = false;
			}
			if (gameOverY <= 0) {
				gameOverY += delta * 0.08f;
			}
			if (time >= 15000) {
				sbg.enterState(Game.menu, new FadeOutTransition(), new FadeInTransition());
			}
			time += delta;
		}
		if (enemyCount == 0) {
			victory.update(delta);
			if (playVicMusic) {
				Songs.victoryBgm();
				playVicMusic = false;
			}
			if (time >= 10000) {
				sbg.enterState(Game.play, new FadeOutTransition(), new FadeInTransition());
			}
			time += delta;
		}
	}

	public void updateMonsters(GameContainer gc, StateBasedGame sbg, int delta) {
		for (Monster m : enemies) {
			m.update(gc, sbg, delta);
		}
	}

	public void renderMonsters(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for (Monster m : enemies) {
			m.render(gc, sbg, g);
		}
	}

	public void hurtboxLogic() throws SlickException {
		if (hero.hurtboxIsSpawned() == true) {
			for (Monster m : enemies) {
				if (hero.getGeneralBoxes().getHurtbox().getBounds()
						.intersects(m.getGeneralBoxes().getHitbox().getBounds()) && m.isHit() == false
						&& m.isAlive() == true && m.isHit() == false) {
					hero.attack(m);
					if (m.isAlive() == false) {
						enemyCount--;
					}
					Sounds.hitSound();
				}
			}
		}
		for (Monster m : enemies) {
			if (m.hurtboxIsSpawned() == true) {
				if (hero.isBlocking() && m.getGeneralBoxes().getHurtbox().getBounds()
						.intersects(hero.getGeneralBoxes().getHitbox().getBounds())
						&& hero.isAlive() == true && m.getHitSomething() == false && hero.getStamina() > 0) { //logic for if hero is blocking
					m.setHitSomething(true);
					hero.useStamina(m.getInfo().getDamage()); // stamina taken from hero on block is the damage of the enemy
					drawHeroHitEffectBlock = true;
					
				} 
				else if (m.getHitSomething() == true) {
					// do Nothing
				}else if (m.getGeneralBoxes().getHurtbox().getBounds()
						.intersects(hero.getGeneralBoxes().getHitbox().getBounds()) && hero.isHit() == false
						&& hero.isAlive() == true) {
					m.attack(hero);
					Sounds.hitSound();
				}
			}
		}
	}

	private void addRandomEnemy(GameContainer gc, int pos) throws SlickException {
		int enemyType = new Random().nextInt(2);
		Monster m = (enemyType == 0) ? new Rabbit() : new Teru();
		m.setMonsterSheets();
		m.setBattleDirection(1);
		m.battleFaceLeft();
		m.getAnimation().start();
		m.setxPosBattle(pos);
		m.updateHealthbar(gc);
		m.setTarget(hero);
		m.contructHitEffect();
		enemies.add(m);
	}

	public int getID() {
		return 4;
	}
}
