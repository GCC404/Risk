package risk.logic;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a player
 * @author Ana Sousa e Gabriel Candal
 *
 */
@SuppressWarnings("serial")
public class Player implements Serializable {

	private List<Territory> territories=new ArrayList<Territory>();
	private String color;
	private List<Card> cards=new ArrayList<Card>();
	private int armies=0;
	private boolean human, receive_card=false;

	
	/**
	 * Player's constructor
	 * @param color player's color
	 * @param human human player or computer
	 */
	public Player(String color, boolean human) {
		this.color=color;
		this.human=human;
		this.armies=0;
	}

	/**
	 * 
	 * @return player's color
	 */
	public String getColor() {
		return this.color;
	}

	/**
	 * Checks if player is human or not
	 * @return true if is human, false otherwise
	 */
	public boolean isHuman() {
		return this.human;
	}

	/**
	 * 
	 * @return number of armies that player hasn't placed on board
	 */
	public int getArmies() {
		return this.armies;
	}
	
	/**
	 * 
	 * @return number of player's armies placed on board
	 */
	public int getTerrArmies() {
		
		int count=0;
		
		for(Territory t: territories)
			count+=t.getArmies();

		return count;
	}
	
	/**
	 * Removes cards from player's set of cards
	 * @param cards cards to remove
	 * @return player's new set of cards
	 */
	public Card[] removeCards(Card[] cards) {
		Card[] res;

		if(cards==null) {
			res=new Card[this.cards.size()];
			res=(Card[]) this.cards.toArray();

			return res;
		}

		res=new Card[cards.length];

		for(Card card: cards) {
			this.cards.remove(card);
		}
		
		return res;
	}
	
	/**
	 * 
	 * @return player's cards
	 */
	public ArrayList<Card> getCards() {
		return (ArrayList<Card>) cards;
	}
	
	/**
	 * 
	 * @return number of cards that player owns
	 */
	public int getCardNr() {
		return cards.size();
	}

	/**
	 * 
	 * @return number of territories that player owns
	 */
	public int getNumTerritories() {
		return territories.size();
	}
	
	/**
	 * Adds territory to player territories list
	 * @param territory 
	 */
	public void occupy(Territory territory) {
		territories.add(territory);
		receive_card=true;
	}
	
	/**
	 * Removes territory from territories that player owns
	 * @param territory
	 */
	public void unoccupy(String territory) {
		territories.remove(territory);
	}

	/**
	 * Adds armies (not placed on board) to player
	 * @param armies number of armies
	 */
	public void addArmies(int armies) {
		this.armies+=armies;
	}

	/**
	 * Adds card to player's set of cards
	 * @param card card to add
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}

	/**
	 * Tries to find a valid combination of cards to redeem.
	 * If it exists, removes them.
	 * @return True if there was a valid combination.
	 */
	public boolean redeem() {
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

			return true;
		}

		if(c1>=3) {
			for(Card c: cards)
				if(c.getArmies()==1) {
					cards.remove(c);
					c1--;
					if(c1==0)
						return true;
				}
		}

		if(c2>=3) {
			for(Card c: cards)
				if(c.getArmies()==5) {
					cards.remove(c);
					c2--;
					if(c2==0)
						return true;
				}
		}

		for(Card c: cards)
			if(c.getArmies()==10) {
				cards.remove(c);
				c3--;
				if(c3==0)
					return true;
			}

		return false;
	}
	
	/**
	 * @return True if player must receive a card this round.
	 */
	public boolean mustReceiveCard() {
		if(receive_card) {
			receive_card=false;
			return true;
		}
		
		return false;
	}
}