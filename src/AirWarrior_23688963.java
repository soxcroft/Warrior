public class AirWarrior_23688963 extends Warrior_23688963 implements WarriorTypeInterface_23688963 {

	//String type = "Air";
	
    public AirWarrior_23688963(String[] vals) {
        super(vals, 3);
    }
    
    //public String getType() { return type; }

    public void performSpecialAbility() {
    	this.useAbility();
    	this.setOffencePower(this.getOffencePower() + 30);
    	System.out.println("Special ability performed by air warrior!");
    }

    public void specialAbilityCompleted() {
    	this.setOffencePower(this.getOffencePower()-30);
    }
    
}
