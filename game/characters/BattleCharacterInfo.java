package game.characters;

public class BattleCharacterInfo {
	// private static int hp = 100;
		// private static int damage = 20;
		// private static int widthBattle = 300;
		// private static int heightBattle = 200;
		// private static float hurtboxWidth = 80;
		// private static float gapFromCenter = 45;
		// private static int distanceFromTop = 81;
		// private int hitboxWidth = 30;
		// private int hitboxHeight = 65;
	private String name;
	private int hp, damage, widthBattle, heightBattle, hitboxWidth, hitboxHeight, distanceFromTop;
	private float hurtboxWidth, gapFromCenter;
	private int aggression;
	public BattleCharacterInfo(String name, int hp, int damage, int widthBattle, int heightBattle, int hitboxWidth, int hitboxHeight, 
			int distanceFromTop, float hurtboxWidth, float gapFromCenter, int aggression){
		setName(name);
		setHp(hp);
		setDamage(damage);
		setWidthBattle(widthBattle);
		setHeightBattle(heightBattle);
		setHitboxWidth(hitboxWidth);
		setHitboxHeight(hitboxHeight);
		setHurtboxWidth(hurtboxWidth);
		setGapFromCenter(gapFromCenter);
		setDistanceFromTop(distanceFromTop);
		setAggression(aggression * 20);
		
	}
	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public int getWidthBattle() {
		return widthBattle;
	}
	public void setWidthBattle(int widthBattle) {
		this.widthBattle = widthBattle;
	}
	public int getHeightBattle() {
		return heightBattle;
	}
	public void setHeightBattle(int heightBattle) {
		this.heightBattle = heightBattle;
	}
	public int getHitboxWidth() {
		return hitboxWidth;
	}
	public void setHitboxWidth(int hitboxWidth) {
		this.hitboxWidth = hitboxWidth;
	}
	public int getHitboxHeight() {
		return hitboxHeight;
	}
	public void setHitboxHeight(int hitboxHeight) {
		this.hitboxHeight = hitboxHeight;
	}
	public float getHurtboxWidth() {
		return hurtboxWidth;
	}
	public void setHurtboxWidth(float hurtboxWidth) {
		this.hurtboxWidth = hurtboxWidth;
	}
	public float getGapFromCenter() {
		return gapFromCenter;
	}
	public void setGapFromCenter(float gapfromCenter) {
		this.gapFromCenter = gapfromCenter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDistanceFromTop() {
		return distanceFromTop;
	}

	public void setDistanceFromTop(int distanceFromTop) {
		this.distanceFromTop = distanceFromTop;
	}

	public int getAggression() {
		return aggression;
	}

	public void setAggression(int aggression) {
		this.aggression = aggression;
	}
	
}
