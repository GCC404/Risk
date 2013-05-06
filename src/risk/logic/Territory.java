package risk.logic;

import java.util.Arrays;


public class Territory {
	private int armies=0;
	private String name, ownerColor, continent;
	private String[] adjacent; 
	
	public Territory(String... args) {
		this.name=args[0];
		this.continent=args[1];
		adjacent=new String[args.length-1];
		this.adjacent=Arrays.copyOfRange(args, 2, args.length);
		ownerColor="Blank";
	}
	
	public int getArmies() {
		return this.armies;
	}
	
	public String getOwner() {
		return this.ownerColor;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void occupy(String playerColor, int armies) {
		this.ownerColor=playerColor;
		this.armies+=armies;
	}
	
	public boolean removeArmies(int armies) {
		
		this.armies-=armies;
		
		return this.armies==0;
	}
	
	public boolean checkIfConnected(String target) {
		
		for(String territory: adjacent)
			if(territory==target)
				return true;
		
		return false;
	}
	
	public String getContinent() {
		return this.continent;
	}
}
