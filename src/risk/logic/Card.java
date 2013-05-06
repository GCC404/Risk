package risk.logic;

public class Card {
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
