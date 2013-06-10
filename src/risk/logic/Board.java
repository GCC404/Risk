package risk.logic;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import risk.generator.Generator;
import risk.rmi.BoardInter;
import risk.rmi.GUICallback;

@SuppressWarnings("serial")
public class Board extends UnicastRemoteObject implements BoardInter
{
	private ArrayList<Player> players=new ArrayList<Player>();
	private int currentPlayer, reinforcements=4;
	private Map<String,Territory> territories=new HashMap<String,Territory>();
	private Stack<Card> deck=new Stack<Card>(), stash=new Stack<Card>();
	private ArrayList<GUICallback> observers=new ArrayList<GUICallback>();
	private boolean gamestarted=false;

	private void notifyObservers(String phase) {
		for(GUICallback o: observers)
			try {
				o.notify(phase);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	/**
	 * Board constructor
	 */
	public Board() throws RemoteException {
		currentPlayer=0;
		initTerritories();
		initDeck();
		Collections.shuffle(deck);
	}

	/**
	 * Initialize deck
	 */
	private void initDeck() {
		deck.push(new Card("Alaska",10));
		deck.push(new Card("Alberta",1));
		deck.push(new Card("Central America",1));
		deck.push(new Card("Eastern United States",10));
		deck.push(new Card("Greenland",10));
		deck.push(new Card("Northwest Territory",10));
		deck.push(new Card("Ontario",5));
		deck.push(new Card("Quebec",10));
		deck.push(new Card("Western United States",1));

		deck.push(new Card("Argentina",5));
		deck.push(new Card("Brazil",5));
		deck.push(new Card("Peru",10));
		deck.push(new Card("Venezuela",1));

		deck.push(new Card("Great Britain",10));
		deck.push(new Card("Iceland",10));
		deck.push(new Card("Northern Europe",5));
		deck.push(new Card("Scandinavia",5));
		deck.push(new Card("Southern Europe",1));
		deck.push(new Card("Ukraine",5));
		deck.push(new Card("Western Europe",1));

		deck.push(new Card("Congo",5));
		deck.push(new Card("East Africa",10));
		deck.push(new Card("Egypt",5));
		deck.push(new Card("Madagascar",10));
		deck.push(new Card("North Africa",1));
		deck.push(new Card("South Africa",1));

		deck.push(new Card("Afghanistan",1));
		deck.push(new Card("China",5));
		deck.push(new Card("India",5));
		deck.push(new Card("Irkutsk",5));
		deck.push(new Card("Japan",10));
		deck.push(new Card("Kamchatka",10));
		deck.push(new Card("Middle East",1));
		deck.push(new Card("Mongolia",1));
		deck.push(new Card("Siam",10));
		deck.push(new Card("Siberia",5));
		deck.push(new Card("Ural",1));
		deck.push(new Card("Yakutsk",5));

		deck.push(new Card("Eastern Australia",5));
		deck.push(new Card("Indonesia",1));
		deck.push(new Card("New Guinea",10));
		deck.push(new Card("Western Australia",1));
	}

	/**
	 * Initialize territories
	 */
	private void initTerritories() {
		territories.put("Alaska", new Territory("Alaska", "North America", "Alberta", "Northwest Territory", "Kamchatka"));
		territories.put("Alberta", new Territory("Alberta", "North America", "Alaska", "Northwest Territory", "Ontario", "Western United States"));
		territories.put("Central America", new Territory("Central America", "North America", "Eastern United States", "Western United States", "Venezuela"));
		territories.put("Eastern United States", new Territory("Eastern United States", "North America", "Central America", "Ontario", "Quebec", "Western United States", "Iceland"));
		territories.put("Greenland", new Territory("Greenland", "North America", "Northwest Territory", "Ontario", "Quebec"));
		territories.put("Northwest Territory", new Territory("Northwest Territory", "North America", "Alaska", "Alberta", "Ontario", "Greenland"));
		territories.put("Ontario", new Territory("Ontario", "North America", "Alberta", "Greenland", "Northwest Territory", "Quebec", "Eastern United States", "Western United States"));
		territories.put("Quebec", new Territory("Quebec", "North America", "Greenland", "Ontario", "Eastern United States"));
		territories.put("Western United States", new Territory("Western United States", "North America", "Alberta", "Central America", "Eastern United States", "Ontario"));

		territories.put("Argentina", new Territory("Argentina", "South America", "Brazil", "Peru"));
		territories.put("Brazil", new Territory("Brazil", "South America", "Argentina", "Peru", "Venezuela", "North Africa"));
		territories.put("Peru", new Territory("Peru", "South America", "Argentina", "Brazil", "Venezuela"));
		territories.put("Venezuela", new Territory("Venezuela", "South America", "Argentina", "Brazil", "Peru", "Central America"));

		territories.put("Great Britain", new Territory("Great Britain", "Europe", "Iceland", "Northern Europe", "Scandinavia", "Western Europe"));
		territories.put("Iceland", new Territory("Iceland", "Europe", "Great Britain", "Scandinavia", "Greenland"));
		territories.put("Northern Europe", new Territory("Northern Europe", "Europe", "Great Britain", "Western Europe", "Southern Europe", "Ukraine"));
		territories.put("Scandinavia", new Territory("Scandinavia", "Europe", "Great Britain", "Iceland", "Northern Europe", "Ukraine"));
		territories.put("Southern Europe", new Territory("Southern Europe", "Europe", "Western Europe", "Northern Europe", "Ukraine", "Egypt", "North Africa", "Middle East"));
		territories.put("Ukraine", new Territory("Ukraine", "Europe", "Northern Europe", "Scandinavia", "Southern Europe", "Afghanistan", "Middle East", "Ural"));
		territories.put("Western Europe", new Territory("Western Europe", "Europe", "Great Britain", "Northern Europe", "Southern Europe", "North Africa"));

		territories.put("Congo", new Territory("Congo", "Africa", "East Africa", "North Africa", "South Africa"));
		territories.put("East Africa", new Territory("East Africa", "Africa", "Congo", "Egypt", "Madagascar", "North Africa", "south Africa", "Middle East"));
		territories.put("Egypt", new Territory("Egypt", "Africa", "East Africa", "North Africa", "Southern Europe", "Middle East"));
		territories.put("Madagascar", new Territory("Madagascar", "Africa", "East Africa", "South Africa"));
		territories.put("North Africa", new Territory("North Africa", "Africa", "Congo", "East Africa", "Egypt", "Brazil"));
		territories.put("South Africa", new Territory("South Africa", "Africa", "Congo", "East Africa", "Madagascar"));

		territories.put("Afghanistan", new Territory("Afghanistan", "Asia", "China", "India", "Middle East", "Ural", "Ukraine"));
		territories.put("China", new Territory("China", "Asia", "Afghanistan", "India", "Mongolia", "Siam", "Siberia", "Ural"));
		territories.put("India", new Territory("India", "Asia", "Afghanistan", "China", "Middle East", "Siam"));
		territories.put("Irkutsk", new Territory("Irkutsk", "Asia", "Kamchatka", "Mongolia", "Siberia", "Yakutsk"));
		territories.put("Japan", new Territory("Japan", "Asia", "Kamchatka", "Mongolia"));
		territories.put("Kamchatka", new Territory("Kamchatka", "Asia", "Irkutsk", "Japan", "Mongolia", "Yakutsk", "Alaska"));
		territories.put("Middle East", new Territory("Middle East", "Asia", "Afghanistan", "India", "Southern Europe", "Ukraine", "East Africa", "Egypt"));
		territories.put("Mongolia", new Territory("Mongolia", "Asia", "China", "Irkutsk", "Japan", "Siberia", "Ural"));
		territories.put("Siam", new Territory("Siam", "Asia", "China", "India", "Indonesia"));
		territories.put("Siberia", new Territory("Siberia", "Asia", "China", "Irkutsk", "Mongolia", "Ural", "Yakutsk"));
		territories.put("Ural", new Territory("Ural", "Asia", "Afghanistan", "China", "Siberia", "Ukraine"));
		territories.put("Yakutsk", new Territory("Yakutsk", "Asia", "Irkutsk", "Kamchatka", "Siberia"));

		territories.put("Eastern Australia", new Territory("Eastern Australia", "Australia", "New Guinea", "Western Australia"));
		territories.put("Indonesia", new Territory("Indonesia", "Australia", "New Guinea", "Siam"));
		territories.put("New Guinea", new Territory("New Guinea", "Australia", "Eastern Australia", "Indonesia", "Western Australia"));
		territories.put("Western Australia", new Territory("Western Australia", "Australia", "Eastern Australia", "New Guinea"));
	}
	
	public boolean isGameStarted() {
		return gamestarted;
	}

	/**
	 * 
	 * @return board's number of players
	 */
	public int getNumPlayers() {
		return players.size();
	}

	public boolean startGame() {
		if(getNumPlayers()<2)
			return false;

		for(Territory territory: territories.values())
			if(territory.getOwner()==-1)
				return false;

		for(Player p: players) 
			p.addArmies(40-(getNumPlayers()-2)*5);

		gamestarted=true;
		notifyObservers("Gamestart");	
		
		return true;
	}

	/**
	 * 
	 * @return current player number of armies not placed on board
	 */
	public int getPlayerArmies() {
		return players.get(currentPlayer).getArmies();
	}

	/**
	 * 
	 * @return current player's color
	 */
	public String getPlayerColor() {
		return players.get(currentPlayer).getColor();
	}

	/**
	 * 
	 * @param territory
	 * @return territory's owner color
	 */
	public String getTerrColor(String territory) {
		try {
			return players.get(territories.get(territory).getOwner()).getColor();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Removes card from deck
	 * @return card removed
	 */
	public Card getCard() {
		if(deck.size()==0)
			initDeck();
		
		return deck.pop();
	}

	/**
	 * 
	 * @param territory
	 * @return number armies placed on territory 
	 */
	public int getTerrArmies(String territory) {
		return territories.get(territory).getArmies();
	}

	/**
	 * 
	 * @param territory
	 * @return territory owner id
	 */
	public int getTerrPlayer(String territory) {
		return territories.get(territory).getOwner();
	}

	/**
	 * 
	 * @return current player number of cards
	 */
	public int getCardNr() {
		return players.get(currentPlayer).getCardNr();
	}

	/**
	 * 
	 * @return deck size
	 */
	public int getCardsInDeck() {
		return deck.size();
	}

	/**
	 * 
	 * @return current player's cards
	 */
	public ArrayList<Card> getCards() {
		if(players.size()<1)
			return new ArrayList<Card>();

		if(getCardNr()>=5)
			players.get(currentPlayer).redeem();

		return players.get(currentPlayer).getCards();
	}

	/**
	 * Add player to board
	 * @param newPlayer player to add
	 * @return true if correctly added, false otherwise
	 */
	public boolean addPlayer(Player newPlayer) {
		for(Player player: players)
			if(player.getColor().equals(newPlayer.getColor()))
				return false;

		players.add(newPlayer);

		return true;
	}

	/**
	 * Adds the number of armies to a player
	 * that he should received, based on
	 * the territories he has
	 */
	private void reinforce() {
		int nr=Math.round(players.get(currentPlayer).getNumTerritories());
		nr+=checkContinents();

		players.get(currentPlayer).addArmies(nr);
	}

	/**
	 * Having full continents deals bonus armies.
	 * Calculates how many of those armies there are
	 * to receive.
	 * @return number of extra armies to add
	 */
	@SuppressWarnings("unused")
	private int checkContinents() {
		int nr=0;
		int na=0,sa=0, europe=0, africa=0, asia=0, australia=0;

		for (Map.Entry<String, Territory> entry : territories.entrySet())
			switch(entry.getValue().getContinent()) {
			case "North America":
				na+=1;
				break;
			case "South America":
				sa+=1;
				break;
			case "Africa":
				africa+=1;
				break;
			case "Asia":
				asia+=1;
				break;
			case "Europe":
				europe+=1;
				break;
			case "Australia":
				australia+=1;
				break;
			}

		return nr;
	}

	/**
	 * Checks if there is a valid combination to redeem the cards of the current player.
	 * If there is, adds up the bonus armies resulting from redeeming them.
	 * @return True if redeeming was possible.
	 */
	public boolean redeem() {
		if(players.get(currentPlayer).redeem()) {
			players.get(currentPlayer).addArmies(reinforcements);
			advanceGolden();

			notifyObservers("Redeem");
			return true;
		}
		return false;
	}

	/**
	 * Number of reinforcements to receive by card
	 * redeeming raises on each redeem. This makes
	 * that raise.
	 */
	private void advanceGolden() {
		if(reinforcements<15)
			reinforcements+=2;
		else if(reinforcements==15)
			reinforcements+=3;
		else reinforcements+=5;
	}

	/**
	 * Occupy territory with armies
	 * @param territory territory to occupy
	 * @param armies number of armies to place on territory
	 */
	private void occupy(String territory,int armies) {
		territories.get(territory).occupy(currentPlayer, armies);
		players.get(currentPlayer).occupy(territories.get(territory));
	}

	/**
	 * Randomly occupy entire board
	 */
	@SuppressWarnings("unchecked")
	public void randomOccupy() {

		int occupying=0;

		for(int i=0; i<territories.size(); i++) {
			Card drawn=getCard();

			if(occupying==-1 || occupying==players.size()-1)
				occupying=0;
			else occupying+=1;

			territories.get(drawn.getTerritory()).occupy(occupying, 1);
			players.get(occupying).occupy(territories.get(drawn.getTerritory()));
			players.get(occupying).addArmies(-1);

			stash.push(drawn);
		}

		//Cleans track of newly occupied territories
		//in order to receive a card he shouldn't
		for(Player p:players)
			p.mustReceiveCard();

		deck=(Stack<Card>) stash.clone();
		stash.clear();
		Collections.shuffle(deck);
		notifyObservers("Occupation");
	}

	/**
	 * Occupy territory with armies (used for tests)
	 * @param territory territory to occupy
	 * @param armies number of armies to place on territory
	 * @return true if success, false otherwise
	 */
	public boolean pickOccupy(String territory, int armies) {
		if(territories.get(territory).getOwner()!=-1)
			return false;

		occupy(territory,armies);

		if(currentPlayer==-1 || currentPlayer==players.size()-1)
			currentPlayer=0;
		else currentPlayer+=1;
		
		notifyObservers("Reinforce");

		return true;
	}

	/**
	 * Create attack from origin to target
	 * @param origin name of origin territory
	 * @param target name of target territory
	 * @return true if success, false otherwise
	 */
	public boolean attack(String origin, String target) {
		if(origin=="" || target=="")
			return false;

		if(!checkIfConnected(origin, target) || territories.get(origin).getOwner()!=currentPlayer ||
				territories.get(target).getOwner()==currentPlayer || territories.get(origin).getArmies()<2)
			return false;

		int maxatck=0, maxdefend=0, roll;

		for(int i=0; i<Math.min(3,territories.get(origin).getArmies()-1); i++) {
			roll=Generator.nextInt(6);

			if(roll>maxatck)
				maxatck=roll;
		}

		for(int i=0; i<Math.min(2,territories.get(target).getArmies()); i++) {
			roll=Generator.nextInt(6);

			if(roll>maxdefend)
				maxdefend=roll;
		}

		if(maxatck>maxdefend) {

			if(territories.get(target).removeArmies(1)) {
				occupy(target,Math.min(territories.get(origin).getArmies()-1,3));
				territories.get(origin).removeArmies(Math.min(territories.get(origin).getArmies()-1,3));
				if(players.get(territories.get(target).getOwner()).getNumTerritories()==0) {
					Card[] cards=players.get(territories.get(target).getOwner()).removeCards(null);

					for(Card card: cards)
						players.get(currentPlayer).addCard(card);
				}
			}
		} else territories.get(origin).removeArmies(1);
		
		notifyObservers("Attack");

		return true;
	}

	/**
	 * Check if origin territory is connected to the one specified on target 
	 * @param origin name of origin territory
	 * @param target name of target territory
	 * @return true if connected, false otherwise
	 */
	private boolean checkIfConnected(String origin, String target) {
		return territories.get(origin).checkIfConnected(target);
	}

	/**
	 * Reinforce armies placed on territory owned by current player
	 * @param territory territory to reinforce
	 * @param armies number of armies to add
	 * @return true if success, false otherwise
	 */
	public boolean reinforce(String territory, int armies) {
		if(territory=="")
			return false;

		if(territories.get(territory).getOwner()!=currentPlayer || armies>players.get(currentPlayer).getArmies())
			return false;

		territories.get(territory).removeArmies(-armies);
		players.get(currentPlayer).addArmies(-armies);
		
		notifyObservers("Reinforce");

		return true;
	}

	/**
	 * Move armies from one territory to another, both owned by current player
	 * @param origin name of origin territory
	 * @param target name of target territory
	 * @param armies number of armies to move
	 * @return true if success, false otherwise
	 */
	public boolean move(String origin, String target, int armies) {

		if(origin=="" || target=="")
			return false;

		if(!checkIfConnected(origin, target) || territories.get(origin).getOwner()!=currentPlayer ||
				territories.get(target).getOwner()!=currentPlayer || armies>territories.get(origin).getArmies()-1)
			return false;

		territories.get(origin).removeArmies(armies);
		territories.get(target).occupy(currentPlayer, armies);
		
		notifyObservers("Move");

		return true;
	}

	/**
	 * Pass the player's turn
	 * @return true if possible, false game is over
	 */
	public boolean nextPlaying() {

		if(players.get(currentPlayer).getNumTerritories()==41 || players.get(currentPlayer).getArmies()>0)
			return false;

		if(players.get(currentPlayer).getNumTerritories()>0) {	
			reinforce();

			if(players.get(currentPlayer).mustReceiveCard())
				players.get(currentPlayer).addCard(getCard());
		}

		if(currentPlayer==-1 || currentPlayer==players.size()-1)
			currentPlayer=0;
		else currentPlayer+=1;

		notifyObservers("Turn");
		
		return true;
	}

	@Override
	public void addObserver(GUICallback observer) throws RemoteException {
		observers.add(observer);
	}
}