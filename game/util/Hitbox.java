package game.util;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.Color;

public class Hitbox {
	private float x, y, width, height;
	private Boolean canDamage = false;

	public Hitbox(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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
		this.x = x;
		this.y = y;
	}
	
	public Rectangle getBounds(){ return new Rectangle(x, y, width, height);}

	public Boolean getCanDamage() {
		return canDamage;
	}

	public void setCanDamage(Boolean canDamage) {
		this.canDamage = canDamage;
	}
}
