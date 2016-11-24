package game.util;

public class Hitbox {
	private float x, y, width, height;
	private Boolean canDamage;

	public Hitbox(float x, float y, float width, float height , Boolean canDamage) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setCanDamage(canDamage);
	}

	public float getCenterX() {
		return x + (width / 2.0f);
	}

	public float getCenterY() {
		return x + (height / 2.0f);
	}

	public void resize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public void repos(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Boolean getCanDamage() {
		return canDamage;
	}

	public void setCanDamage(Boolean canDamage) {
		this.canDamage = canDamage;
	}
}
