package game;

import java.util.Timer;
import java.util.TimerTask;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Time implements Runnable {
	
	Animation b;
	Timer t = new Timer();
	TimerTask task;
	
	public void run(){
		try{
			t.schedule(new TimerTask() {
				  int repeat = 0;
				  public void run() {
				    System.out.println("Done");
				    repeat++;
				    if(repeat == 2){
				    	this.cancel();
				    }
				  }
				}, 1000, 1000 );
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception in Time.java");
		}
	}

	/*public static void main(String[] args){
		Thread t1 = new Thread(new Time());
		t1.start();
	}*/
	
}
