package game.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.characters.BattleCharacter;
import game.characters.heroes.Hero;

public class Staminabar extends Bar {

	public Staminabar(int f, float x, float y, BattleCharacter c) {
		super(f, x, y, c);
	}

	public int getMaxStamina() {
		return getMaxValue();
	}

	public void setCurrentStamina(int hp) {
		setCurrentValue(hp);
	}

	public float getCurrentStamina() {
		return getCurrentValue();
	}
	
	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(x - 1, y - 1, getMaxValue() + 2, 15 + 1);
		g.setColor(Color.green);
		g.fillRect(x, y, ((Hero)c).getStamina(),14);
		g.setColor(Color.black);
		g.drawString(((Hero)c).getStamina() + "/" + getMaxStamina(), getX() + 15, getY() - 2);
	}
}
