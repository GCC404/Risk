package risk.test;

/**
 * Tema: Jogo do Risco
 * Autores: Ana Isabel Sousa (ei11068), Gabriel Candal (ei11066)
 */

import static org.junit.Assert.*;
import risk.generator.Generator;
import risk.logic.*;

public class Test {

	/**
	 * Tests board initialization
	 */
	@org.junit.Test
	public void boardInit() {
		Board myBoard = Board.getInstance();
		assertEquals(42,myBoard.getCardsInDeck());
	}
	
	/**
	 * Tests if a player is correctly added
	 */
	@org.junit.Test
	public void testAddPlayer() {
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);

		assertSame(myBoard.getNumPlayers(), 0);
		myBoard.addPlayer(player1);
		assertSame(myBoard.getNumPlayers(), 1);
		
		assertSame(myBoard.startGame(), false);
		myBoard.addPlayer(player2);
		assertSame(myBoard.startGame(), true);
	}

	/**
	 * Tests if territories and armies are correctly added to player
	 */
	@org.junit.Test
	public void testReinforce() {
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);

		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		
		assertSame(player1.getNumTerritories(), 0);
		myBoard.occupy("Alaska", 2);
		assertSame(player1.getNumTerritories(), 1);
		assertSame(player1.getArmies(),40);
		
		myBoard.nextPlaying();
		assertSame(player2.getNumTerritories(), 0);
		myBoard.occupy("Argentina", 2);
		assertSame(player2.getNumTerritories(), 1);
		assertSame(player2.getArmies(),40);

	}

	/**
	 * Tests territories random occupation
	 */
	@org.junit.Test
	public void testRandomOccupy(){
	
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.randomOccupy();
		
		int num1=player1.getArmies();
		myBoard.nextPlaying();
		int num2=player2.getArmies();

		assertSame(num1, num2);
			
	}
	
	/**
	 * Tests invalid attack (attack between territories that aren't adjacent or connected by a sea-lane)
	 */
	@org.junit.Test
	public void testInvalidAttack() {

		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();

		myBoard.occupy("Alaska", 2);
		myBoard.nextPlaying();
		myBoard.occupy("Argentina", 2);
		
		assertSame(myBoard.attack("Alaska", "Argentina"), false);
	}

	
	/**
	 *  Tests valid attack, the player who's attacking wins.
	 */
	@org.junit.Test
	public void testValidAttack() { 
		
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.occupy("Brazil", 2);
		myBoard.nextPlaying();
		myBoard.occupy("Argentina", 2);
		myBoard.nextPlaying();
		
		int[] roll = {6, 5, 5}; //attack wins
		Generator.setTest(roll);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);
		
		int[] roll1 = {6, 5}; //attack wins
		Generator.setTest(roll1);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);
		assertSame(myBoard.getTerrArmies("Brazil"), 1);
		assertSame(myBoard.getTerrPlayer("Argentina"), 1);
	}

	/**
	 *  Tests valid attack, the player who's defending wins.
	 */
	@org.junit.Test
	public void testValidAttack1() {
		
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.occupy("Brazil", 2);
		myBoard.nextPlaying();
		myBoard.occupy("Argentina", 2);
		myBoard.nextPlaying();
		
		int[] roll = {2, 5, 5}; //attack loses
		Generator.setTest(roll);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Brazil"), 1);	
	}
	
	/**
	 * Tests if armies are correctly moved from one territory to another 
	 */
	@org.junit.Test
	public void testMoveArmies() {
		
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.occupy("Brazil", 10);
		myBoard.occupy("Argentina", 5);
		
		System.out.println("brazil antes teste "+myBoard.getTerrArmies("Brazil"));
		myBoard.move("Brazil", "Argentina", 2); // moves two armies from Brazil to Argentina (connected)
		System.out.println("brazil depois teste "+myBoard.getTerrArmies("Brazil"));
		assertSame(myBoard.getTerrArmies("Brazil"), 8); 
		assertSame(myBoard.getTerrArmies("Argentina"), 7);
		assertFalse(myBoard.move("Brazil", "Argentina", 10)); // this move isn't possible because Brazil doesn't have 10 armies
		assertFalse(myBoard.move("Brazil", "Alaska", 8)); // this move isn't possible because Alaska doesn't belong to player1
	}

	
	/**
	 * Tests if cards are correctly given to player when he conquer territories
	 */
	@org.junit.Test
	public void testCards(){
		
		//quando tem mais que 5 - trocar; trocar por opcao e falhar; trocar por
		//opcao e conseguir <-> cada vez que trocas recebes numero diferente de reforcos
		
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		
		myBoard.occupy("Afghanistan", 2);
		myBoard.occupy("India", 1);
		myBoard.occupy("Siberia", 1);
		myBoard.occupy("Brazil", 1);
		myBoard.occupy("Congo", 1);
		
		myBoard.nextPlaying();
		myBoard.occupy("China", 1);
		myBoard.occupy("Middle East", 2);
		myBoard.occupy("Ural", 2);
		myBoard.occupy("Argentina", 2);
		myBoard.occupy("East Africa", 2);
		
		assertSame(player1.getCardNr(), 3);
		assertSame(player2.getCardNr(), 3);
		assertSame(player2.getArmies(), 40);
		
		myBoard.nextPlaying();
		int[] roll = {1, 2}; //attack loses
		Generator.setTest(roll);
		myBoard.attack("Afghanistan", "China");
		assertSame(myBoard.getTerrArmies("Afghanistan"), 1);
		assertSame(player2.getArmies(), 41);
		assertSame(player1.getCardNr(), 3); //doens't receive card
		assertSame(player2.getCardNr(), 4); //receive card
		
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		
		int[] roll1 = {3, 2}; //attack wins
		Generator.setTest(roll1);
		myBoard.attack("Middle East", "India");
		assertSame(player2.getArmies(), 42);
		assertSame(player2.getCardNr(), 5); //receives card and has to exchange for armies 
		assertTrue((myBoard.canRedeem()).size()>0);
		assertSame(player2.getCardNr(), 2);
		assertSame(player2.getArmies(), 42+4); //receives 4 armies (first time)
		
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		
		int[] roll2 = {3, 2}; //attack wins
		Generator.setTest(roll2);
		myBoard.attack("Ural", "Siberia");
		
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		
		int[] roll3 = {3, 2}; //attack wins
		Generator.setTest(roll3);
		myBoard.attack("Brazil", "Argentina");
		
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		
		int[] roll4 = {3, 2}; //attack wins
		Generator.setTest(roll4);
		myBoard.attack("East Africa", "Congo");
		assertSame(player2.getCardNr(), 5); //receives card and has to exchange for armies 
		assertTrue((myBoard.canRedeem()).size()>0);
		assertSame(player2.getCardNr(), 2);
		assertSame(player2.getArmies(), 49+6); //receives 6 armies (second time) 
	}
}