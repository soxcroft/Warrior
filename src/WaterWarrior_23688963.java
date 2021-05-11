public class WaterWarrior_23688963 extends Warrior_23688963 implements WarriorTypeInterface_23688963 {

	//String type = "Water";
	
    public WaterWarrior_23688963(String[] vals) {
        super(vals, 0); // ability iterations is set to 0 to avoid specialAbilityCompleted() being called
    }
    
    public void performSpecialAbility() {
    	this.useAbility();
    	this.setHealth(this.getHealth() + 20);
    	System.out.println("Special ability performed by water warrior!");
    }

    public void specialAbilityCompleted() {
    }

}
