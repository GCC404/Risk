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

	public int getArmies() {
		return this.armies;
	}

	public void redeem(String t1, String t2, String t3) {
		for(Card c: cards)
			if(c.getTerritory()==t1 || c.getTerritory()==t2 || c.getTerritory()==t3)
				cards.remove(c);
	}

	public void redeem() {
		int c1=0, c2=0, c3=0;
		Card i1=null, i2=null, i3=null;

		for(Card c: cards)
			if(c.getArmies()==1) {
				c1++;
				i1=c;
			}


		for(Card c: cards)
			if(c.getArmies()==5) {
				c2++;
				i2=c;
			}

		for(Card c: cards)
			if(c.getArmies()==10) {
				c3++;
				i3=c;
			}

		if(c1>0 && c2>0 && c3>0) {
			cards.remove(i1);
			cards.remove(i2);
			cards.remove(i3);

			return;
		}

		if(c1>=3) {
			for(Card c: cards)
				if(c.getArmies()==1) {
					cards.remove(c);
					c1--;
					if(c1==0)
						return;
				}
		}

		if(c2>=3) {
			for(Card c: cards)
				if(c.getArmies()==5) {
					cards.remove(c);
					c2--;
					if(c2==0)
						return;
				}
		}

		for(Card c: cards)
			if(c.getArmies()==10) {
				cards.remove(c);
				c3--;
				if(c3==0)
					return;
			}

	}

	public ArrayList<Card> getCards() {
		return (ArrayList<Card>) cards;
	}
}