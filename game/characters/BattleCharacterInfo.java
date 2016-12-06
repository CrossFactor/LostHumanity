package game.characters;

public class BattleCharacterInfo {
	private String name;
	private int maxHp, damage, widthBattle, heightBattle, hitboxWidth, hitboxHeight, distanceFromTop;
	private float hurtboxWidth, gapFromCenter;
	private int healthBarDistance, aggression;
	private float moveSpeed;
	private int currentHp; //, attackRangeBox;
	private float distanceFromTopAttack;
	private int indexLastFrame, indexStartAttackFrame, indexEndAttackFrame;
	private int adjust;

	public BattleCharacterInfo(String name, int hp, int damage, int widthBattle, int heightBattle, int hitboxWidth,
			int hitboxHeight, int distanceFromTop, float hurtboxWidth, float gapFromCenter, int healthBarDistance,
			int aggression, float moveSpeed, float distanceFromTopAttack, int indexLastFrame, int indexStartAttackFrame,
			int indexEndAttackFrame, int adjust) {
		setName(name);
		setMaxHp(hp);
		setDamage(damage);
		setWidthBattle(widthBattle);
		setHeightBattle(heightBattle);
		setHitboxWidth(hitboxWidth);
		setHitboxHeight(hitboxHeight);
		setHurtboxWidth(hurtboxWidth);
		setGapFromCenter(gapFromCenter);
		setDistanceFromTop(distanceFromTop);
		setHealthBarDistance(healthBarDistance);
		setAggression(aggression * 17);
		setCurrentHp(hp);
		setMoveSpeed(moveSpeed);
		setDistanceFromTopAttack(distanceFromTopAttack);
		setIndexLastFrame(indexLastFrame);
		setIndexStartAttackFrame(indexStartAttackFrame);
		setIndexEndAttackFrame(indexEndAttackFrame);
		setAdjust(adjust); //adjust adjusts the yPosition in case y pos doesn't center
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

	public int getHealthBarDistance() {
		return healthBarDistance;
	}

	public void setHealthBarDistance(int healthBarDistance) {
		this.healthBarDistance = healthBarDistance;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public void setCurrentHp(int currentHp) {
		this.currentHp = currentHp;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(float moveSpeed2) {
		this.moveSpeed = moveSpeed2;
	}

//	public int getAttackRangeBox() {
//		return attackRangeBox;
//	}
//
//	public void setAttackRangeBox(int attackRangeBox) {
//		this.attackRangeBox = attackRangeBox;
//	}

	public float getDistanceFromTopAttack() {
		return distanceFromTopAttack;
	}

	public void setDistanceFromTopAttack(float distanceFromTopAttack) {
		this.distanceFromTopAttack = distanceFromTopAttack;
	}

	public int getIndexLastFrame() {
		return indexLastFrame;
	}

	public void setIndexLastFrame(int indexLastFrame) {
		this.indexLastFrame = indexLastFrame;
	}

	public int getIndexEndAttackFrame() {
		return indexEndAttackFrame;
	}

	public void setIndexEndAttackFrame(int indexEndAttackFrame) {
		this.indexEndAttackFrame = indexEndAttackFrame;
	}

	public int getIndexStartAttackFrame() {
		return indexStartAttackFrame;
	}

	public void setIndexStartAttackFrame(int indexStartAttackFrame) {
		this.indexStartAttackFrame = indexStartAttackFrame;
	}

	public int getAdjust() {
		return adjust;
	}

	public void setAdjust(int adjust) {
		this.adjust = adjust;
	}

}
