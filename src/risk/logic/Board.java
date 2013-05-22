package risk.logic;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import risk.generator.Generator;

public class Board
{
	private static Board instance;
	private static ArrayList<Player> players=new ArrayList<Player>();
	private static int currentPlayer, oldterr=-1;
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
		territories.get(territory).occupy(currentPlayer, armies);
		players.get(currentPlayer).occupy(territories.get(territory));
	}

	public void randomOccupy() {
		
		int occupying=0;

		for(int i=0; i<territories.size(); i++) {
			Card drawn=getCard();
			
			if(occupying==-1 || occupying==players.size()-1)
				occupying=0;
			else occupying+=1;

			territories.get(drawn.getTerritory()).occupy(occupying, drawn.getArmies());
			players.get(occupying).occupy(territories.get(drawn.getTerritory()));
		}
	}

	public Card getCard() {
		return deck.pop();
	}

	public int getTerrArmies(String territory) {
		return territories.get(territory).getArmies();
	}

	public int getTerrPlayer(String territory) {
		return territories.get(territory).getOwner();
	}

	public boolean attack(String origin, String target) {
		if(!checkIfConnected(origin, target))
			return false;



		int maxatck=0, maxdefend=0, roll;

		for(int i=0; i<(territories.get(origin).getArmies())%4-1; i++) {
			roll=Generator.nextInt(6)+1;

			if(roll>maxatck)
				maxatck=roll;
		}

		for(int i=0; i<(territories.get(target).getArmies())%3-1; i++) {
			roll=Generator.nextInt(6)+1;

			if(roll>maxdefend)
				maxatck=roll;
		}



		if(maxatck>maxdefend) {

			if(territories.get(target).removeArmies(-1)) {
				occupy(target,(territories.get(origin).getArmies())%4-1);

				if(players.get(territories.get(target).getOwner()).getNumTerritories()==0) {
					Card[] cards=players.get(territories.get(target).getOwner()).removeCards(null);

					for(Card card: cards)
						players.get(currentPlayer).addCard(card);


				}
			}


		} else territories.get(origin).removeArmies(-1);



		return true;
	}

	public boolean checkIfConnected(String origin, String target) {
		return territories.get(origin).checkIfConnected(target);
	}

	public boolean move(String origin, String target, int armies) {

		if(!checkIfConnected(origin, target) || territories.get(origin).getOwner()!=currentPlayer ||
				territories.get(target).getOwner()!=currentPlayer || armies>territories.get(origin).getArmies()-1)
			return false;

		territories.get(origin).removeArmies(armies);
		territories.get(origin).occupy(currentPlayer, armies);

		return true;
	}
	/*
	private boolean rollDie(String target) {
		//TODO fazer isto
		return true;
	}
	 */

	public boolean nextPlaying() {

		if(players.get(currentPlayer).getNumTerritories()>0)
			if(players.get(currentPlayer).getNumTerritories()>oldterr)
				players.get(currentPlayer).addCard(getCard());

		if(currentPlayer==-1 || currentPlayer==players.size()-1)
			currentPlayer=0;
		else currentPlayer+=1;

		if(players.get(currentPlayer).getNumTerritories()>0) {
			oldterr=players.get(currentPlayer).getNumTerritories();

			reinforce();

			return true;
		}
		else if(players.get(currentPlayer).getNumTerritories()==41)
			return false;

		return nextPlaying();
	}

	public int getNumPlayers() {
		return players.size();
	}

	public boolean startGame() {

		if(getNumPlayers()<2)
			return false;

		for(Player p: players) 
			p.addArmies(40-(getNumPlayers()-2)*5);

		return true;
	}

	public static Board getInstance()
	{
		if (instance == null)
			instance = new Board();

		return instance;
	}

}