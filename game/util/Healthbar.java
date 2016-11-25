package game.util;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Color;

public class Healthbar{
	int maxHealth;
	int currentHealth;
	float x;
	float y;
	float centerX;
	int charWidth;
	
	public Healthbar(int hp, float x, float y, int charWidth) {
		setMaxHp(hp);
		setCurrentHp(hp);
		this.charWidth = charWidth;
		setX(x);
		setY(y);
	}
	
	public void update(float x, float y){
		setX(x);
		setY(y);
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float x) {
		this.centerX = x + (maxHealth / 2.0f);
	}

	public void setY(float y){
		this.y = y;
	}
	
	public void setMaxHp(int maxHealth) { //the variable name refers to the characters max hp, variable name may vary
		this.maxHealth = maxHealth;
	}
	
	public int getMaxHp() {
		return maxHealth;
	}
	
	public void setCurrentHp(int hp){
		this.currentHealth = hp;
	}
	
	public int getCurrentHp(){
		return currentHealth;
	}
	
	public void draw(Graphics g) throws SlickException {
		  g.setColor(Color.red);
		  g.fillRect(x - 1, y - 1, maxHealth + 2, 15 + 1);
		  g.setColor(Color.green);
		  g.fillRect(x, y, currentHealth, 14);
	   }
	  
}