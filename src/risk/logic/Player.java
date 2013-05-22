package risk.logic;
import java.util.ArrayList;
import java.util.List;

public class Player {

	private List<Territory> territories=new ArrayList<Territory>();
	private String color;
	private List<Card> cards=new ArrayList<Card>();
	private int armies=0;
	private boolean human;
	
	public Player(String color, boolean human) {
		this.color=color;
		this.human=human;
		this.armies=0;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public boolean isHuman() {
		return this.human;
	}
	
	public int getCardNr() {
		return cards.size();
	}

	public void occupy(Territory territory) {
		territories.add(territory);
	}
	
	public int getNumTerritories() {
		return territories.size();
	}
	
	public void addArmies(int armies) {
		this.armies+=armies;
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	public Card[] removeCards(Card[] cards) {
		Card[] res;
		
		if(cards==null) {
			res=new Card[this.cards.size()];
			this.cards.toArray();
			
			return res;
		}
			
		res=new Card[cards.length];
		
		for(Card card: cards) {
			this.cards.remove(card);
		}
		
		return res;
	}
	
	public void unoccupy(String territory) {
		territories.remove(territory);
	}
	
	public ArrayList<Card[]> canRedeem() {
		//TODO fazer isto
		
		Card[] intermediate=new Card[3];
		ArrayList<Card[]> res=new ArrayList<Card[]>();
		
		return res;
	}
	
	public int getArmies() {
		return this.armies;
	}
}