package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.states.Battle;
import game.states.Menu;
import game.states.Opening;
import game.states.Opening2;
import game.states.Play;
import game.util.Debug;

public class Game extends StateBasedGame {
	
	public static final String gamename = "Lost Humanity";
	public static final int op = 0;
	public static final int op2 = 1;
	public static final int menu = 2;
	public static final int play = 3;
	public static final int battle = 4;
	public static final int test = 5;
	
	
	public Game(String gamename){
		super(gamename);
		Debug.debugMode(true);
		this.addState(new Menu(menu)); //States are the different states of the game. Menu for main menu
		this.addState(new Play(play)); // we have play for game proper
		this.addState(new Battle(battle)); //battle for battle game
		this.addState(new Test(test));
		this.addState(new Opening(op));
		this.addState(new Opening2(op2));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{ //Initializes the different states
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(battle).init(gc, this);
		this.getState(test).init(gc, this);
		this.getState(op).init(gc, this);
		this.getState(op2).init(gc, this);
		this.enterState(battle); //this shows what state enters first
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
