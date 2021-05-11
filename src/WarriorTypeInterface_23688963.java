public interface WarriorTypeInterface_23688963 {

	// Given
    void performSpecialAbility();
    void specialAbilityCompleted();

    // Other
    public String getType();
    
    public int getX();
    public int getY();
    public int getID();
    public int getAge();
    public int getIterations();
    public double getHealth();
    public double getDefenceStrength();
    public double getInitialDefence();
    public double getOffencePower();
    public double getInitialOffence();
    public boolean getUsedAbility();
    public boolean getProtect();
    public boolean getInvisible();
    public int getInvisibleIterations();
    public boolean getConfused();
    
    public void setHealth(double val);
    public void setOffencePower(double val);
    public void setInitialOffence(double val);
    public void setDefenceStrength(double val);
    public void setInitialDefence(double val);
    public void setProtect(boolean val);
    public void setInvisible(boolean val);
    public void setInvisibleIterations(int val);
    public void setConfused(boolean val);
    public void useAbility();
    public void incrementAge();
    public void decrementIterations();
    public void pickupWeapon(Weapon_23688963 weapon);
        
    public boolean isBefore(WarriorTypeInterface_23688963 other_warrior);
    public boolean neighbours(int x2, int y2, int cols, int rows);
    
    public void move(int iteration, int cols, int rows);
    
    public String toString();
    
}
