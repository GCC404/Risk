package risk.logic;

import java.io.Serializable;

/**
 * Class that represents a game card
 * @author Ana Sousa e Gabriel Candal
 *
 */
@SuppressWarnings("serial")
public class Card implements Serializable {
	private String territory;
	private int armies;
	
	/**
	 * Card's constructor
	 * @param territory 
	 * @param armies 
	 */
	public Card(String territory, int armies) {
		this.territory=territory;
		this.armies=armies;
	}
	
	/**
	 * 
	 * @return card's territory
	 */
	public String getTerritory() {
		return this.territory;
	}
	
	/**
	 * 
	 * @return card's number of armies 
	 */
	public int getArmies() {
		return this.armies;
	}
}
