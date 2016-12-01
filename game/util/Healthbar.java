package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import game.characters.BattleCharacter;
import game.characters.heroes.Hero;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class Healthbar {
	int maxHealth;
	float currentHealth;
	float x;
	float y;
	float centerX;
	BattleCharacter c;

	public Healthbar(int hp, float x, float y, BattleCharacter c) {
		setMaxHp(hp * 100 / hp);
		setCurrentHp(maxHealth);
		this.c = c;
		setX(x);
		setY(y);

	}

	public void update(float x, float y) {
		setX(x);
		setY(y);
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getX() {
		return x;
	}

	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float x) {
		this.centerX = x + (maxHealth / 2.0f);
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setMaxHp(int maxHealth) { // the variable name refers to the
											// characters max hp, variable name
											// may vary
		this.maxHealth = maxHealth;
	}

	public int getMaxHp() {
		return maxHealth;
	}

	public void setCurrentHp(int hp) {
		this.currentHealth = hp;
	}

	public float getCurrentHp() {
		return currentHealth;
	}

	public int adjust(int damage) {
		return maxHealth * (damage / c.getInfo().getMaxHp());
	}

	public void draw(Graphics g) throws SlickException {
		g.setColor(Color.red);
		g.fillRect(x - 1, y - 1, maxHealth + 2, 15 + 1);
		g.setColor(Color.green);
		g.fillRect(x, y, currentHealth, 14);
		g.setColor(Color.black);
		if (c instanceof Hero) {
			g.drawString(c.getInfo().getCurrentHp() + "/" + c.getInfo().getMaxHp(), getX() + 15, getY() - 2);
		}
		else{ 
			g.drawString(c.getInfo().getCurrentHp() + "/" + c.getInfo().getMaxHp(), getX() + 25, getY() - 2);
		}
	}

	public void takeDamage(int damage) {
		currentHealth = currentHealth - (maxHealth * ((float) damage / (float) c.getInfo().getMaxHp()));
		System.out.println(currentHealth);
	}

}