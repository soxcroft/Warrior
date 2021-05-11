public class FlameWarrior_23688963 extends Warrior_23688963 implements WarriorTypeInterface_23688963 {

	//String type = "Flame"; // Is this private and static?
	private double prev_strength; // used in special ability
	
    public FlameWarrior_23688963(String[] vals) {
        super(vals, 2); // Special ability lasts 2 interations
    }
    
    public void performSpecialAbility() {
    	this.useAbility();
    	this.prev_strength = this.getDefenceStrength();
    	this.setDefenceStrength(100);
    	System.out.println("Special ability performed by flame warrior!");
    }
    public void specialAbilityCompleted() {		
    	this.setDefenceStrength(Math.max(this.prev_strength, 70));
    }
}
