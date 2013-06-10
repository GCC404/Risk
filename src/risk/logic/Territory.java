package risk.logic;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class that represents a territory
 * @author Ana Sousa e Gabriel Candal
 *
 */
@SuppressWarnings("serial")
public class Territory implements Serializable {
	private int armies=0, playerId=-1;
	private String name, continent;
	private String[] adjacent; 
	
	/**
	 * Territory constructor
	 * @param args name of territory, continent of territory, territories that are adjacent or connected by sea lane 
	 */
	public Territory(String... args) {
		this.name=args[0];
		this.continent=args[1];
		adjacent=new String[args.length-1];
		this.adjacent=Arrays.copyOfRange(args, 2, args.length);
	}
	
	/**
	 * 
	 * @return number of armies placed on territory
	 */
	public int getArmies() {
		return this.armies;
	}
	
	/**
	 * 
	 * @return owner id
	 */
	public int getOwner() {
		return this.playerId;
	}
	
	/**
	 * 
	 * @return territory's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @return territory's continent
	 */
	public String getContinent() {
		return this.continent;
	}
	
	/**
	 * Occupy territory
	 * @param playerId territory owner
	 * @param armies number of armies placed on territory
	 */
	public void occupy(int playerId, int armies) {
		this.playerId=playerId;
		this.armies+=armies;
	}
	
	/**
	 * Remove armies from territory
	 * @param armies number of armies to remove from territory
	 * @return true if territory has no armies placed
	 */
	public boolean removeArmies(int armies) {
		this.armies-=armies;
		
		return this.armies==0;
	}
	
	/**
	 * Check if territory is connected to the one specified on target 
	 * @param target name of territory
	 * @return true if connected, false otherwise
	 */
	public boolean checkIfConnected(String target) {
		
		for(String territory: adjacent)
			if(territory.equals(target))
				return true;
		
		return false;
	}
}
