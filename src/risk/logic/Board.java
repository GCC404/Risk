package risk.logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class Board
{
	private static Board instance;
	private static ArrayList<Player> players=new ArrayList<Player>();
	private static int currentPlayer;
	private static Map<String,Territory> territories=new HashMap<String,Territory>();
	private static Stack<Card> deck=new Stack<Card>();

	private Board()
	{
		currentPlayer=-1;
		initTerritories();
		initDeck();
		Collections.shuffle(deck);
	}

	private void initDeck() {
		//TODO acabar de preencher isto
		deck.push(new Card("Alaska",1));
	}

	private void initTerritories() {
		//TODO acabar de preencher isto
		territories.put("Alaska", new Territory("Alaska","Northwest Territory","Kamchatka"));
		territories.put("Alberta", new Territory("Alberta","Northwest Territory","Ontario","Western United States"));
		territories.put("Central America", new Territory("Central America"));
		territories.put("Eastern United States", new Territory("Eastern United States"));
		territories.put("Greenland", new Territory("Greenland"));
		territories.put("Northwest Territory", new Territory("Northwest Territory"));
		territories.put("Ontario", new Territory("Ontario"));
		territories.put("Quebec", new Territory("Quebec"));
		territories.put("Western United States", new Territory("Western United States"));
	}

	public boolean addPlayer(Player newPlayer) {
		for(Player player: players)
			if(player.getColor()==newPlayer.getColor())
				return false;

		players.add(newPlayer);

		return true;
	}

	public void reinforce() {
		int nr=Math.round(players.get(currentPlayer).getNumTerritories());
		nr+=checkContinents();

		players.get(currentPlayer).addArmies(nr);
	}

	@SuppressWarnings("unused")
	private int checkContinents() {
		//TODO Eventualmente thread, para ver todos os continentes ao mesmo tempo
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

	public Player getPlayer() {
		return players.get(currentPlayer);
	}
	
	public int getCardNr() {
		return players.get(currentPlayer).getCardNr();
	}
	
	public ArrayList<Card[]> canRedeem() {
		return players.get(currentPlayer).canRedeem();
	}
	
	public void occupy(String territory,int armies) {
		territories.get(territory).occupy(players.get(currentPlayer).getColor(), armies);
		players.get(currentPlayer).occupy(territories.get(territory));
	}

	public void randomOccupy() {
		Card drawn=getCard();

		territories.get(drawn.getTerritory()).occupy(players.get(currentPlayer).getColor(), drawn.getArmies());
		players.get(currentPlayer).occupy(territories.get(drawn.getTerritory()));
	}

	public Card getCard() {
		return deck.pop();
	}

	public void attack(String origin, String target) {
		//TODO fazer
	}
	
	public boolean checkIfConnected(String origin, String target) {
		return territories.get(origin).checkIfConnected(target);
	}
	
	public boolean rollDie(String target) {
		//TODO fazer isto
		return true;
	}
	
	public void nextPlaying() {
		if(currentPlayer==-1 || currentPlayer==players.size()-1)
			currentPlayer=0;
		else currentPlayer+=1;
	}

	public static Board getInstance()
	{
		if (instance == null)
			instance = new Board();

		return instance;
	}

}