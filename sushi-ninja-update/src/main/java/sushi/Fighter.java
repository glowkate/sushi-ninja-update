/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import java.io.File;

/**
 *
 * @author kate
 */
public interface Fighter {
    
    //Called when a fighter moves to a tile.
    public void whenMoved(final Tile destination);
    
    //Called when a fighter attacks someone.
    //Note that the fighter isn't responcable for dealing the damage.
    public void whenAttacking();
    
    //Called when a fighter is attacked by someone.
    public void whenAttacked(final Fighter attacker);
    
    //Returns the raw amount of damage delt.
    public int calculateDamage();
    
    //Returns the raw amount of damage healed.
    public int calculateHealing();
    
    //Returns a number from 0 to 1 representing an attack's accuracy
    public double calculateAccuracyThreshold(final Fighter target);
    
    //The fighter takes damage. The fighter's defence value is applyed to this.
    public void takeDamage(int damage);
    
    //The fighter heals damage.
    public void healDamage(int healing);
    
    //Returns a number that represents how favorable a fighter is to attack. 
    //< 3 is a more favorable target, > 3 is a less favorable target.
    public int getFighterWeight();
    
    //Returns the fighter's status
    public FighterStatus getFighterStatus();
    
    //Returns the fighter's maximum hit points.
    public int getMaxHP();
    
    //Returns the fighter's maximum current hit points.
    public int getCurrentHP();
    
    //Returns the fighter's current movement points.
    public int getCurrentMove();
    
    //Sets the current movement of the fighter.
    public void setCurrentMove(int newMove);
    
    //Returns the image used to represent the Fighter.
    public File getImageFile();
    
    //Is called at the end of an in-game round
    public void roundEnd();
    
    
    @Override
    public String toString();
    
    @Override
    public boolean equals(final Object obj);
    
    @Override
    public int hashCode();
}
