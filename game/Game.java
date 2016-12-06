package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import game.states.Battle;
import game.states.Credits;
import game.states.Menu;
import game.states.Opening;
import game.states.Opening2;
import game.states.Pause;
import game.states.Play;
import game.states.SceneOne;
import game.states.SceneTwo;
import game.util.Debug;

public class Game extends StateBasedGame {
	
	public static final String gamename = "Lost Humanity";
	public static final int op = 0;
	public static final int op2 = 1;
	public static final int menu = 2;
	public static final int play = 3;
	public static final int battle = 4;
	public static final int credits = 5;
	public static final int pause = 6;
	public static final int scene1 = 11;
	public static final int scene2 = 12;
	
	
	public Game(String gamename){
		super(gamename);
		Debug.debugMode(true);
		this.addState(new Menu(menu)); //States are the different states of the game. Menu for main menu
		this.addState(new Play(play)); // we have play for game proper
		this.addState(new Battle(battle)); //battle for battle game
		this.addState(new Opening(op));
		this.addState(new Opening2(op2));
		this.addState(new Pause(pause));
		this.addState(new SceneOne(play));
		this.addState(new Credits(credits));
		this.addState(new SceneOne(scene1));
		this.addState(new SceneTwo(scene2));
	}
	
	public void initStatesList(GameContainer gc) throws SlickException{ //Initializes the different states
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.getState(battle).init(gc, this);
		this.getState(op).init(gc, this);
		this.getState(op2).init(gc, this);
		this.getState(pause).init(gc, this);
		this.getState(scene1).init(gc, this);
		this.getState(scene2).init(gc, this);
		this.getState(credits).init(gc, this);
		this.enterState(battle); //this shows what state enters first
	}

	public static void main(String[] args) {
		AppGameContainer appgc = null; //container for the game
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(800, 600, false); //display settings
			appgc.setShowFPS(false);
			appgc.start();
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
}
