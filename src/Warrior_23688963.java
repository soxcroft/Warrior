public class Warrior_23688963 {
	
	public static int counter; // private static?
	
	private int x, y;
	private int id;
	private String type;
	private int age;
	private double health;
	private double offence_power;
	private double initial_offence_power;
	private double defence_strength;
	private double initial_defence_strength;
	private Weapon_23688963[] inventory;
	private boolean full; // used to store whether or not the warriors inventory is full
	private boolean invisible;
	private int invisible_iterations;
	private boolean confused;
	private String moves;
	
	private int inv_i;
	
	private boolean used_ability;
	private int ability_iterations;
	
	private boolean protect; // Set to true or false depending on whether there is a peacemaker/crystal in a neighbouring cell
	
	public Warrior_23688963(String[] vals, int iterations) {
		// Initialize warrior class. Iterations are hard coded in subclasses
		this.y = Integer.parseInt(vals[0]);
		this.x = Integer.parseInt(vals[1]);
		this.id = Integer.parseInt(vals[2]);
		this.type = vals[3];
		this.age = Integer.parseInt(vals[4]);
		this.health = Double.parseDouble(vals[5]);
		this.offence_power = Double.parseDouble(vals[6]);
		this.defence_strength = Double.parseDouble(vals[7]);
		this.inventory = new Weapon_23688963[Integer.parseInt(vals[8])];
		this.moves = vals[9];
		
		invisible = false;
		confused = false;
		
		this.ability_iterations = iterations;
		
		full = false;
		inv_i = 0;
		used_ability = false;
		
		++counter;
	}
	
	// Get methods
	
	public int getX() { return this.x; }
	
	public int getY() { return this.y; }
	
	public int getID() { return this.id; }
	
	public int getAge() { return this.age; }
	
	public int getIterations() { return this.ability_iterations; }
	
	public String getType() { return this.type; }
	
	public double getHealth() { return this.health; }
	
	public double getDefenceStrength() { return this.defence_strength; }
	
	public double getInitialDefence() { return this.initial_defence_strength; }
	
	public double getOffencePower() { return this.offence_power; }
	
	public double getInitialOffence() { return this.initial_offence_power; }
	
	public boolean getUsedAbility() { return this.used_ability; }
	
	public boolean getProtect() { return this.protect; }
	
	public boolean getInvisible() { return this.invisible; }
	
	public int getInvisibleIterations() { return this.invisible_iterations; }
	
	public boolean getConfused() { return this.confused; }
	
	// Update methods
	
	public void setHealth(double val) { this.health = Math.min(100, val); }
	
	public void setOffencePower(double val) { this.offence_power = Math.min(100, val); }
	
	public void setInitialOffence(double val) { this.initial_offence_power = val; }
	
	public void setDefenceStrength(double val) { this.defence_strength = Math.min(100, val); }
	
	public void setInitialDefence(double val) { this.initial_defence_strength = val; }
	
	public void setProtect(boolean val) { this.protect = val; }
	
	public void setInvisible(boolean val) { this.invisible = val; }
	
	public void setInvisibleIterations(int val) { this.invisible_iterations = val; }
	
	public void setConfused(boolean val) { this.confused = val; }
	
	public void incrementAge() { 
		// Increment age and update defense strength
		++this.age; 
		if (!((this.type.equals("Stone")||this.type.equals("Flame"))&&this.used_ability&&this.ability_iterations>0))
		if (this.age > 50)
			this.defence_strength = Math.min(this.defence_strength, 30);
		else if (this.age > 25)
			this.defence_strength = Math.min(this.defence_strength, 50);
		else if (this.age > 15)
			this.defence_strength = Math.min(this.defence_strength, 70);
	}
	
	public void useAbility() { this.used_ability = true; }
	
	public void decrementIterations() { --this.ability_iterations; }
		
	public void pickupWeapon(Weapon_23688963 weapon) {
		// Pickup weapon, but drop weapon first if inventory is full
		if (full) {
			inventory[inv_i].setAvailable(true);
			inventory[inv_i].setX(x);
			inventory[inv_i].setY(y);
			inventory[inv_i].setWarriorStrength(-1);
			offence_power -= inventory[inv_i].getEffect();
		}
		inventory[inv_i] = weapon;
		weapon.setAvailable(false);
		setOffencePower(offence_power + weapon.getEffect());
		inv_i = (inv_i+1)%inventory.length;
		if (inv_i==0) full = true;
	}
	
	// Other methods
	
	public boolean isBefore(WarriorTypeInterface_23688963 other_warrior) {
		// Use conditions specified in spec to determine if 'this' should be printed
		// before other_warrior when printing statistics
		boolean before;
		before = this.getY() < other_warrior.getY();
		before = before || (this.getY()==other_warrior.getY() && this.getX()<other_warrior.getX());
		before = before || (this.getY()==other_warrior.getY() && this.getX()==other_warrior.getX() && this.getID()<other_warrior.getID());
		return before;		
	}
	
	public boolean neighbours(int x2, int y2, int cols, int rows) {
		// Return true if this warrior is neighbours with cell (x2, y2)
		int dx = Math.abs(this.x-x2);
		int dy = Math.abs(this.y-y2);
		return ((dx<=1 || dx==cols-1) && (dy<=1 || dy==rows-1) && (dx!=0 || dy!=0));
	}
	
	public void move(int iteration, int cols, int rows) {
		char move = moves.charAt(iteration%moves.length());
		//if (iteration < moves.length())
			//move = moves.charAt(iteration);
		if (!confused) {
			switch (move) {
			case 'd': x++; break;
			case 'a': x--; break;
			case 'w': y--; break;
			case 'x': y++; break;
			case 'e': x++; y--; break;
			case 'q': x--; y--; break;
			case 'c': x++; y++; break;
			case 'z': x--; y++; break;
			}
		}
		else {
			switch (move) {
			case 'a': x++; break;
			case 'd': x--; break;
			case 'x': y--; break; 
			case 'w': y++; break;
			case 'z': x++; y--; break; // TODO check you inverted it correctly
			case 'c': x--; y--; break;
			case 'q': x++; y++; break;
			case 'e': x--; y++; break;
			}			
		}
		x = (x+cols)%cols;
		y = (y+rows)%rows;
	}
	
	public String toString() {
		// Return id, age, health, offence power, defense strength, number of weapons
		return (id + ", " + age + ", " + health + ", " + offence_power + ", " + 
		defence_strength + ", " + (full ? inventory.length : inv_i) + ", " + this.type + ", " + 
		y + ", " + x);
	}
	
}
