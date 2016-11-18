package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame {
	
	public static final String gamename = "Lost Humanity";
	public static final int menu = 0;
	public static final int play = 1;
	public static final int battle = 2;
	public static final int test = 3;
	
	public Game(String gamename){
		super(gamename);
		this.addState(new Menu(menu)); //States are the different states of the game. Menu for main menu
		this.addState(new Play(play)); // we have play for game proper
		this.addState(new Battle(battle));
		this.addState(new Test(test));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{ //Initializes the different states
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(battle).init(gc, this);
		this.getState(test).init(gc, this);
		this.enterState(menu); //this shows what state enters first
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc; //container for the game
		
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(800, 600, false); //display settings
			appgc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}

}
