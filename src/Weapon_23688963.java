
public class Weapon_23688963 {
	
	private int x;
	private int y;
	private double effect;
	private double warrior_strength; // used to compare offence strength during weapon pickup
	private WarriorTypeInterface_23688963 warrior; // used to move weapon between warriors if necessary
	private boolean available;
	
	public Weapon_23688963(double[] vals) {
		this.x = (int) vals[0];
		this.y = (int) vals[1];
		this.effect = vals[2];
		this.warrior_strength = -1; // so that every warrior can pick up this weapon
		this.available = true;
	}
	
	public int getX() { return this.x; }
	
	public int getY() { return this.y; }
	
	public double getEffect() { return this.effect; }
	
	public double getWarriorStrength() { return this.warrior_strength; }
	
	public WarriorTypeInterface_23688963 getWarrior() { return this.warrior; }
	
	public boolean getAvailable() { return this.available; }
	
	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setWarriorStrength(double strength) { this.warrior_strength = strength; }
	
	public void setWarrior(WarriorTypeInterface_23688963 warrior) { this.warrior = warrior; }
	
	public void setAvailable(boolean bool) { this.available = bool; }

}
