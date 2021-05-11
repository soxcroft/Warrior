public class StoneWarrior_23688963 extends Warrior_23688963 implements WarriorTypeInterface_23688963 {

	//String type = "Stone"; // Is this private and static?
	private double prev_strength;
	
    public StoneWarrior_23688963(String[] vals) { 
    	super(vals, 4); 
    }
    
    public void performSpecialAbility() {
    	this.useAbility();
    	this.prev_strength = this.getDefenceStrength();
    	this.setDefenceStrength(Math.max(this.getHealth(), 95));
    	this.setHealth(this.getHealth() - 3);
    	System.out.println("Special ability performed by stone warrior!");
    }

    public void specialAbilityCompleted() {
    	this.setDefenceStrength(prev_strength);
    }
    
}
