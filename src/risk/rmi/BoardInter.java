package risk.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import risk.logic.Card;
import risk.logic.Player;

public interface BoardInter extends Remote {
	
		public boolean addPlayer(Player newPlayer) throws RemoteException;
		
		public String getTerrColor(String territory) throws RemoteException;

		public int getCardNr() throws RemoteException;

		public ArrayList<Card> getCards() throws RemoteException;
		
		public boolean redeem() throws RemoteException;
		
		public boolean pickOccupy(String territory, int armies) throws RemoteException;

		public void randomOccupy() throws RemoteException;
		
		public Card getCard() throws RemoteException;
		
		public int getTerrArmies(String territory) throws RemoteException;
		
		public int getTerrPlayer(String territory) throws RemoteException;
		
		public String getPlayerColor() throws RemoteException;
		
		public int getPlayerArmies() throws RemoteException;
		
		public boolean isGameStarted() throws RemoteException;

		public boolean attack(String origin, String target) throws RemoteException;
		
		public boolean reinforce(String territory, int armies) throws RemoteException;

		public boolean move(String origin, String target, int armies) throws RemoteException;

		public boolean nextPlaying() throws RemoteException;

		public int getNumPlayers() throws RemoteException;
		
		public boolean startGame() throws RemoteException;
		
		public void addObserver(GUICallback observer) throws RemoteException;
}
