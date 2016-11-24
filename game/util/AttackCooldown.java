package game.util;

import java.util.Timer;
import java.util.TimerTask;

import game.characters.BattleCharacter;

public class AttackCooldown implements Runnable {

	Timer t = new Timer();
	TimerTask task;
	BattleCharacter c;
	int repeat = 0;

	public AttackCooldown(BattleCharacter c) {
		this.c = c;
	}

	@Override
	public void run(){
		try{
			t.schedule(new TimerTask() {

				@Override
				public void run() {
					c.offCooldown();
				}
				}, 500);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception in Time.java");
		}
	}
}
