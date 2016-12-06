package game.util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import game.characters.BattleCharacter;
import game.characters.heroes.Hero;

public abstract class Bar {
	int maxValue;
	float currentValue;
	float x;
	float y;
	float centerX;
	BattleCharacter c;

	public Bar(int barValue, float x, float y, BattleCharacter c) {
		setMaxValue(barValue * 100 / barValue);
		setCurrentValue(maxValue);
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
		this.centerX = x + (maxValue / 2.0f);
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getY() {
		return y;
	}

	public void setMaxValue(int value) { // the variable name refers to the
											// characters max hp, variable name
											// may vary
		this.maxValue = value;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setCurrentValue(float value) {
		this.currentValue = value;
	}

	public float getCurrentValue() {
		return currentValue;
	}

}
