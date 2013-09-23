import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

import swen221.monopoly.*;
import swen221.monopoly.GameOfMonopoly.InvalidMove;
/**
 * JUnit test class to get 100% test coverage of the code.  
 * @author JuiDeshpande
 * 
 */
public class MonopolyTests {
	// this is where you must write your tests; do not alter the package, or the
    // name of this file.  An example test is provided for you.

	@Test
	public void testValidBuyProperty_1() {
		try {
			tryAndBuy(1500, "Park Lane");
		} catch (GameOfMonopoly.InvalidMove e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testValidBuyProperty_2() {
		try {
			tryAndBuy(0, "Park Lane");
			fail();
		} catch (GameOfMonopoly.InvalidMove e) {}
	}

	/**
	 * This is a helper function.  Feel free to modify this as you wish.
	 */
	private void tryAndBuy(int cash, String locationName) throws GameOfMonopoly.InvalidMove {
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location location = board.findLocation(locationName);
		Player player = new Player("Dave","Dog",cash,location);
		game.buyProperty(player);
	}
	
//==================================================================	
	// Board Tests
	@Test
	public void testBoard(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		board.findLocation(start, 1);
		board.findLocation("a");
	}
	
	//
	//Util Tests
	//
	
	@Test
	public void testUtil(){
		Utility prop = new Utility("Electric Company", 150);
		Utility prop2 = new Utility("Water Works", 150);
		Street prop3 = new Street("Old Kent Road", 60, 2);
		prop.mortgage();
		prop.unmortgage();
		prop.isMortgaged();
		prop.hasOwner();
		Player p1 = new Player("Player1", "Dog", 1000, prop);
		p1.buy(prop);
		p1.buy(prop2);
		p1.buy(prop3);
		prop.setOwner(p1);
		prop2.setOwner(p1);
		prop.hasOwner();
		prop.getOwner();
		prop.getRent();
	}
	
	//
	//Street Tests
	//
	
	@Test
	public void testStreet(){
		Street prop = new Street("Old Kent Road", 60, 2);
		Street prop2 = new Street("Whitechapel Road", 60, 4);
		prop.setColourGroup(new ColourGroup("Brown", prop, prop2));
		prop.getColourGroup().getColour();
		prop.getHouses();
		prop.getRent();
		prop.getHotels();
	}
	
	//
	//Player Tests
	//
	
	@Test
	public void testPlayer(){
		Street prop = new Street("Old Kent Road", 60, 2);
		Player p1 = new Player("Player1", "Dog", 1000, prop);
		p1.getName();
		p1.credit(100);
		p1.deduct(100);
		p1.setLocation(prop);
		p1.getToken();
		
		prop.setOwner(p1);
		try{
			p1.buy(prop);
			fail();
		}catch(IllegalArgumentException e){}
		
		Street prop2 = new Street("Whitechapel Road", 60, 4);
		p1.sell(prop);
		try{
			p1.sell(prop2);
			fail();
		}catch(IllegalArgumentException e){}
	}
	
	//
	//Special Area Tests
	//
	
	@Test
	public void testSpecialArea(){
		SpecialArea area = new SpecialArea("GO");
		area.hasOwner();
		try{
			area.getOwner();
		}catch(RuntimeException e){}
		try{
			area.getRent();
		}catch(RuntimeException e){}
	}
	
	//
	//Station Tests
	//
	
	@Test
	public void testStation(){
		Station prop = new Station("Kings Cross Station", 150);
		Station prop2 = new Station("Liverpoop St. Station", 150);
		Street prop3 = new Street("Old Kent Road", 60, 2);
		Player p1 = new Player("Player1", "Dog", 1000, prop);
		p1.buy(prop);
		p1.buy(prop2);
		p1.buy(prop3);
		prop.setOwner(p1);
		prop2.setOwner(p1);
		prop.getRent();
	}
	
	//
	//Move Tests
	//
	
	@Test
	public void testMovePlayer1(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		game.movePlayer(p2, 1);
		game.movePlayer(p1, 40);
	}
	
	@Test
	public void testMovePlayer2(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		((Property) prop).mortgage();
		game.movePlayer(p2, 1);
		game.movePlayer(p1, 40);
	}
	
	//
	//Buy Tests
	//
	@Test
	public void testBuyProperty(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		((Property) prop).mortgage();
		game.movePlayer(p2, 1);
		try{
			game.buyProperty(p2);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Buying something that cannot be bought (jail etc)
	@Test
	public void testBuyProperty2(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		game.movePlayer(p1, 10);
		try{
			game.buyProperty(p1);
			fail();
		}catch(InvalidMove e){}
	}
	
	//
	//Sell Tests
	//
	
	@Test
	public void testSellProperty(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.sellProperty(p1, prop);
			((Property) prop).setOwner(null);
		}catch(InvalidMove e){}
	}
	
	//selling something that the player doesnt own
	@Test
	public void testSellProperty2(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.sellProperty(p2, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Selling something that is mortgaged
	@Test
	public void testSellProperty3(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		((Property) prop).mortgage();
		try{
			game.sellProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Selling something that cannot be sold
	@Test
	public void testSellProperty4(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Jail");
		try{
			game.sellProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//
	//Mortgage Tests
	//
	@Test
	public void testMortgage(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p1, prop);
			((Property) prop).setOwner(null);
		}catch(InvalidMove e){}
	}
	
	//Mortgaging something that cannot be mortgaged
	@Test
	public void testMortgage2(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Jail");
		try{
			game.mortgageProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Mortgaging something that the player doesnt own
	@Test
	public void testMortgage3(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p2, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Mortgaging something that is already mortgaged
	@Test
	public void testMortgage4(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p1, prop);
		}catch(InvalidMove e){}
		
		try{
			game.mortgageProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//
	// Unmortgage Tests
	//
	@Test
	public void testUnmortgage(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p1, prop);
		}catch(InvalidMove e){}
		
		try{
			game.unmortgageProperty(p1, prop);
		}catch(InvalidMove e){}
	}
	
	//Unmortgaging something that that the player doesnt have the money for
	@Test
	public void testUnmortgage2(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 60, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p1, prop);
		}catch(InvalidMove e){}
		
		try{
			game.unmortgageProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Unmortgaging something that isnt mortgaged
	@Test
	public void testUnmortgage3(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 60, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		
		try{
			game.unmortgageProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//unmortgaging something that the player does not belong
	@Test
	public void testUnmortgage4(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 1000, start);
		Player p2 = new Player("Player2", "Iron", 1000, start);
		Location prop = board.findLocation("Old Kent Road");
		game.movePlayer(p1, 1);
		p1.buy((Property) prop);
		((Property) prop).setOwner(p1);
		try{
			game.mortgageProperty(p1, prop);
		}catch(InvalidMove e){}
		
		try{
			game.unmortgageProperty(p2, prop);
			fail();
		}catch(InvalidMove e){}
	}
	
	//Unmortgaging something that cannot be mortgaged
	@Test
	public void testUnmortgage5(){
		GameOfMonopoly game = new GameOfMonopoly();
		Board board = game.getBoard();
		Location start = board.getStartLocation();
		Player p1 = new Player("Player1", "Dog", 60, start);
		Location prop = board.findLocation("Jail");
		try{
			game.unmortgageProperty(p1, prop);
			fail();
		}catch(InvalidMove e){}
	}
}
