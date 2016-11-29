package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;

public class Box {
	private float x, y, width, height;

	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	public Box(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}

	public float getCenterX() {
		return x + (width / 2.0f);
	}

	public float getCenterY() {
		return y + (height / 2.0f);
	}

	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(x, y, width, height);
	}

	public void update(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public Rectangle getBounds(){ return new Rectangle(x, y, width, height);}
}
