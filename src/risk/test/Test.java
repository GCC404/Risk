package risk.test;

import static org.junit.Assert.*;
import risk.generator.Generator;
import risk.logic.*;

public class Test {


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

	@org.junit.Test
	public void testValidAttack() { //ataque ganha
		
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
		
		int[] roll = {6, 5, 5}; //ataque ganha
		Generator.setTest(roll);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);
		
		int[] roll1 = {6, 5}; //ataque ganha
		Generator.setTest(roll1);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Argentina"), 1);
		assertSame(myBoard.getTerrArmies("Brazil"), 1);
		assertSame(myBoard.getTerrPlayer("Argentina"), 1);
	}

	
	@org.junit.Test
	public void testValidAttack1() { //ataque perde
		
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
		
		int[] roll = {2, 5, 5}; //ataque perde
		Generator.setTest(roll);
		myBoard.attack("Brazil", "Argentina");
		assertSame(myBoard.getTerrArmies("Brazil"), 1);	
	}
	
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
		
		myBoard.move("Brazil", "Argentina", 2);
		assertSame(myBoard.getTerrArmies("Brazil"), 8);
		assertSame(myBoard.getTerrArmies("Argentina"), 7);
		assertFalse(myBoard.move("Brazil", "Argentina", 10));
		assertFalse(myBoard.move("Brazil", "Alaska", 8));
	}

	@org.junit.Test
	public void testCards(){
		//passar turno sem conquistar - nao recebe; passar turno e receber;
		//quando tem mais que 5 - trocar; trocar por opcao e falhar; trocar por
		//opcao e conseguir <-> cada vez que trocas recebes numero diferente de reforcos
		
		
		
	}
	
	@org.junit.Test
	public void boardInit() {
		Board myBoard = Board.getInstance();
		assertTrue(myBoard.getCardNr()==41);
	}
	
	@org.junit.Test
	public void testRandomOccupy(){
		//passar turno sem conquistar - nao recebe; passar turno e receber;
		//quando tem mais que 5 - trocar; trocar por opcao e falhar; trocar por
		//opcao e conseguir <-> cada vez que trocas recebes numero diferente de reforcos
		
		Board myBoard = Board.getInstance();
		Player player1=new Player("blue", true);
		Player player2=new Player("red", true);
		myBoard.addPlayer(player1);
		myBoard.addPlayer(player2);
		myBoard.startGame();
		myBoard.randomOccupy();
		
	}
}