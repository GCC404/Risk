package risk.logic;

import java.io.Serializable;
import java.util.Arrays;


public class Territory implements Serializable {
	private int armies=0, playerId=-1;
	private String name, continent;
	private String[] adjacent; 
	
	public Territory(String... args) {
		this.name=args[0];
		this.continent=args[1];
		adjacent=new String[args.length-1];
		this.adjacent=Arrays.copyOfRange(args, 2, args.length);
	}
	
	public int getArmies() {
		return this.armies;
	}
	
	public int getOwner() {
		return this.playerId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void occupy(int playerId, int armies) {
		this.playerId=playerId;
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
