package risk.test;

/**
 * Tema: Jogo do Risco
 * Autores: Ana Isabel Sousa (ei11068), Gabriel Candal (ei11066)
 */

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import risk.generator.Generator;
import risk.logic.*;

public class Test {

	/**
	 * Tests board initialization
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void boardInit() throws RemoteException {
		Board myBoard=new Board();
		assertEquals(42,myBoard.getCardsInDeck());
	}

	/**
	 * Tests if a player is correctly added
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testAddPlayer() throws RemoteException {
		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);

		assertSame(myBoard.getNumPlayers(), 0);
		myBoard.addPlayer(player1);
		assertSame(myBoard.getNumPlayers(), 1);

		assertSame(myBoard.startGame(), false);
		myBoard.addPlayer(player2);
		assertSame(myBoard.getNumPlayers(), 2);
	}

	/**
	 * Tests if territories and armies are correctly added to player
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testReinforce() throws RemoteException {
		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);

		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();

		assertSame(player1.getNumTerritories(), 0);
		myBoard.pickOccupy("Alaska", 2);
		assertSame(player1.getNumTerritories(), 1);
		assertSame(player1.getTerrArmies(),2);

		assertSame(player2.getNumTerritories(), 0);
		myBoard.pickOccupy("Argentina", 2);
		assertSame(player2.getNumTerritories(), 1);
		assertSame(player2.getTerrArmies(),2);
	}

	/**
	 * Tests territories random occupation
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testRandomOccupy() throws RemoteException{

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.randomOccupy();

		int num1=player1.getTerrArmies();
		System.out.println(player1.getTerrArmies());
		myBoard.nextPlaying();
		int num2=player2.getTerrArmies();
		System.out.println(player2.getTerrArmies());
		assertSame(num1, num2);

	}

	/**
	 * Occupies entire board
	 * @param myBoard
	 */
	private void occupy(Board myBoard) {
		myBoard.pickOccupy("Alaska", 2);
		myBoard.pickOccupy("Alberta", 2);
		myBoard.pickOccupy("Central America", 2);
		myBoard.pickOccupy("Eastern United States", 2);
		myBoard.pickOccupy("Greenland", 2);
		myBoard.pickOccupy("Northwest Territory", 2);
		myBoard.pickOccupy("Ontario", 2);
		myBoard.pickOccupy("Quebec", 2);
		myBoard.pickOccupy("Western United States", 2);

		myBoard.pickOccupy("Argentina", 2);
		myBoard.pickOccupy("Brazil", 2);
		myBoard.pickOccupy("Peru", 2);
		myBoard.pickOccupy("Venezuela", 2);

		myBoard.pickOccupy("Great Britain", 2);
		myBoard.pickOccupy("Iceland", 2);
		myBoard.pickOccupy("Northern Europe", 2);
		myBoard.pickOccupy("Scandinavia", 2);
		myBoard.pickOccupy("Southern Europe", 2);
		myBoard.pickOccupy("Ukraine", 2);
		myBoard.pickOccupy("Western Europe", 2);

		myBoard.pickOccupy("Congo", 2);
		myBoard.pickOccupy("East Africa", 2);
		myBoard.pickOccupy("Egypt", 2);
		myBoard.pickOccupy("Madagascar", 2);
		myBoard.pickOccupy("North Africa", 2);
		myBoard.pickOccupy("South Africa", 2);

		myBoard.pickOccupy("Afghanistan", 2);
		myBoard.pickOccupy("China", 1);
		myBoard.pickOccupy("India", 2);
		myBoard.pickOccupy("Irkutsk", 2);
		myBoard.pickOccupy("Japan", 2);
		myBoard.pickOccupy("Kamchatka", 2);
		myBoard.pickOccupy("Middle East", 2);
		myBoard.pickOccupy("Mongolia", 1);
		myBoard.pickOccupy("Siam", 2);
		myBoard.pickOccupy("Siberia", 2);
		myBoard.pickOccupy("Ural", 2);
		myBoard.pickOccupy("Yakutsk", 2);

		myBoard.pickOccupy("Eastern Australia", 10);
		myBoard.pickOccupy("Indonesia", 2);
		myBoard.pickOccupy("New Guinea", 5);
		myBoard.pickOccupy("Western Australia", 2);
	}

	/**
	 * Tests invalid attack (attack between territories that aren't adjacent or connected by a sea-lane)
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testInvalidAttack() throws RemoteException {

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);

		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();

		occupy(myBoard);

		assertSame(myBoard.attack("Alaska", "Argentina"), false);
	}

	/**
	 *  Tests valid attack, the player who's attacking wins.
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testValidAttack() throws RemoteException { 

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		occupy(myBoard);

		int[] roll = {6, 5, 5}; //attack wins
		Generator.setTest(roll);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);

		int[] roll1 = {6, 5}; //attack wins
		Generator.setTest(roll1);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);
		assertSame(myBoard.getTerrArmies("Brazil"), 1);
		assertSame(myBoard.getTerrPlayer("Argentina"), 0);
	}


	/**
	 *  Tests valid attack, the player who's defending wins.
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testValidAttack1() throws RemoteException {

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		occupy(myBoard);

		int[] roll = {2, 3, 5}; //attack loses
		Generator.setTest(roll);
		myBoard.attack("Alaska", "Alberta");
		assertSame(myBoard.getTerrArmies("Alberta"), 2);
		assertSame(myBoard.getTerrArmies("Alaska"), 1);
	}

	/**
	 * Tests if armies are correctly moved from one territory to another 
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testMoveArmies() throws RemoteException {

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		occupy(myBoard);

		myBoard.move("Eastern Australia", "New Guinea",  2); // moves two armies from Eastern Australia to New Guinea (connected)
		assertSame(myBoard.getTerrArmies("Eastern Australia"), 8); 
		assertSame(myBoard.getTerrArmies("New Guinea"), 7);
		assertFalse(myBoard.move("Eastern Australia", "New Guinea", 10)); // this move isn't possible because Eastern Australia doesn't have 10 armies
		assertFalse(myBoard.move("Eastern Australia", "Alberta", 8)); // this move isn't possible because Alberta doesn't belong to player1
	}


	/**
	 * Tests if cards are correctly given to player when he conquer territories
	 * @throws RemoteException 
	 */
	@org.junit.Test
	public void testCards() throws RemoteException{

		Board myBoard=new Board();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		occupy(myBoard);

		assertSame(player1.getCardNr(), 0);
		assertSame(player2.getCardNr(), 0);
		
		int[] roll = {1, 2}; //player1 loses
		Generator.setTest(roll);
		myBoard.attack("Afghanistan", "China");
		assertSame(myBoard.getTerrArmies("Afghanistan"), 1);
		//assertSame(player1.getArmies(), 41+(41/3));
		assertSame(player1.getCardNr(), 0); // player1 doens't receive card
		assertSame(player2.getCardNr(), 0); // player2 doens't receive card
	
			
	 
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		

		int[] roll1 = {3, 2}; //player2 wins
		Generator.setTest(roll1);
		System.out.println(myBoard.attack("Middle East", "Mongolia"));
		//assertSame(player2.getArmies(), 42+(42/3));
		//assertSame(player2.getCardNr(), 1); // player2 receives card
		
		myBoard.nextPlaying();
		myBoard.nextPlaying();
		assertSame(player2.getCardNr(), 0);
		assertSame(player2.getCardNr(), 1);
		int[] roll2 = {3, 2}; //player2 wins
		Generator.setTest(roll2);
		myBoard.attack("Ural", "Siberia");

		myBoard.nextPlaying();
		myBoard.nextPlaying();
		//assertSame(player2.getArmies(), 43+(43/3));
		//assertSame(player2.getCardNr(), 2); // player2 receives card

		int[] roll3 = {3, 2}; //attack wins
		Generator.setTest(roll3);
		myBoard.attack("Argentina", "Brazil");

		myBoard.nextPlaying();
		myBoard.nextPlaying();
		//assertSame(player2.getArmies(), 44+(44/3));
		//assertSame(player2.getCardNr(), 3); // player2 receives card


		
   /* assertSame(player2.getArmies(), 42+4); //receives 4 armies (first time)
    //assertTrue((myBoard.canRedeem()).size()>0);
        assertSame(player2.getCardNr(), 2);
    int[] roll4 = {3, 2}; //attack wins
    Generator.setTest(roll4);
    myBoard.attack("East Africa", "Congo");
    assertSame(player2.getCardNr(), 5); //receives card and has to exchange for armies 
    //assertTrue((myBoard.canRedeem()).size()>0);
    assertSame(player2.getCardNr(), 2);
    assertSame(player2.getArmies(), 49+6); //receives 6 armies (second time) */
		//}
	}
}