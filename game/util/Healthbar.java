package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import game.characters.BattleCharacter;
import game.characters.heroes.Hero;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class Healthbar extends Bar {

	public Healthbar(int hp, float x, float y, BattleCharacter c) {
		super(hp, x, y, c);
	}

	public int getMaxHp() {
		return getMaxValue();
	}

	public void setCurrentHp(int hp) {
		setCurrentValue(hp);
	}

	public float getCurrentHp() {
		return getCurrentValue();
	}

	public int adjust(int damage) {
		return getMaxValue() * (damage / c.getInfo().getMaxHp());
	}

	public void takeDamage(int damage) {
		setCurrentValue(getCurrentValue() - (getMaxValue()) * ((float) damage / ((float) c.getInfo().getMaxHp())));
	}

	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(x - 1, y - 1, getMaxValue() + 2, 15 + 1);
		g.setColor(Color.red);
		g.fillRect(x, y, getCurrentValue(), 14);
		g.setColor(Color.black);
		if (c instanceof Hero) {
			g.drawString(c.getInfo().getCurrentHp() + "/" + c.getInfo().getMaxHp(), getX() + 15, getY() - 2);
		} else {
			g.drawString(c.getInfo().getCurrentHp() + "/" + c.getInfo().getMaxHp(), getX() + 25, getY() - 2);
		}
	}
}