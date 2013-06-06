package risk.logic;

import java.io.Serializable;

public class Card implements Serializable {
	private String territory;
	private int armies;
	
	public Card(String territory, int armies) {
		this.territory=territory;
		this.armies=armies;
	}
	
	public String getTerritory() {
		return this.territory;
	}
	
	public int getArmies() {
		return this.armies;
	}
}
