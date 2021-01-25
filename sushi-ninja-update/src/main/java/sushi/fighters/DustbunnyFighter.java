/*
 * The default enemy in the game.
 */
package sushi.fighters;

import java.io.File;
import sushi.*;
import java.util.Random;

/**
 *
 * @author kate
 */
public class DustbunnyFighter implements Fighter{
    private final int maxHP = 10;
    private final int maxMove = 2;
    private final int hashCode;
    private int hp;
    private int move;
    private Coord xy;
    private FighterStatus status;
    
    
    public DustbunnyFighter(int inputCode){
        hp = maxHP;
        move = maxMove;
        xy = new Coord(-1, -1);
        status = FighterStatus.ALIVE;
        
        hashCode = inputCode;
    }

    //Called when a fighter moves to a tile.
    //Isn't in charge of removing movement.
    @Override
    public void whenMoved(final Tile destination){
        xy = destination.getXY();
    }
    
    //Called when a fighter attacks someone.
    //Note that the fighter isn't responcable for dealing the damage.
    @Override
    public void whenAttacking(){}
    
    //Called when a fighter is attacked by someone.
    @Override
    public void whenAttacked(final Fighter attacker){}
    
    //Returns the raw amount of damage delt.
    @Override
    public int calculateDamage(){
        final Random RNG = new Random();
        final double randomNumber = RNG.nextDouble();
        final double damageMultiplyer = (randomNumber/2) + 0.75;
        final int damage = (int) Math.round(5.0 * damageMultiplyer);
        return(damage);
    }
    
    //Returns the raw amount of damage healed.
    @Override
    public int calculateHealing(){
        return(0);
    }
    
    //Returns a number from 0 to 1 representing an attack's accuracy
    @Override
    public double calculateAccuracyThreshold(final Fighter target){
        return(0.9);
    }
    
    //The fighter takes damage. The fighter's defence value is applyed to this.
    @Override
    public void takeDamage(int damage){
        hp -= damage;
        final boolean isDead = hp <= 0;
        status = isDead ? FighterStatus.DEAD : FighterStatus.ALIVE;
        hp = isDead ? 0 : hp;
    }
    
    //The fighter heals damage.
    @Override
    public void healDamage(int healing){
        hp += healing;
        final boolean doesExceedLimit = hp > maxHP;
        hp = doesExceedLimit ? maxHP : hp;
    }
    
    //Returns a number that represents how favorable a fighter is to attack. 
    //< 3 is a more favorable target, > 3 is a less favorable target.
    @Override
    public int getFighterWeight(){
        return (3);
    }
    
    //Returns the fighter's status
    @Override
    public FighterStatus getFighterStatus(){
        return(status);
    }
    
    //Returns the fighter's maximum hit points.
    @Override
    public int getMaxHP(){
        return(maxHP);
    }
    
    //Returns the fighter's maximum current hit points.
    @Override
    public int getCurrentHP(){
        return(hp);
    }
    
    //Returns the fighter's current movement points.
    @Override
    public int getCurrentMove(){
        return(move);
    }
    
    @Override
    public void setCurrentMove(int newMove){
        move = newMove;
    }
    
    public File getImageFile(){
        return (new File("sushi/data/DustbunnyFighter.jpg"));
    }
    
    //Is called at the end of an in-game round
    @Override
    public void roundEnd(){
        move = maxMove;
    }
    
    @Override
    public String toString(){
        return("<DustbunnyFighter>" + xy);
    }
    
    @Override
    public boolean equals(final Object obj){
        return(obj == this);
    }
    
    @Override
    public int hashCode(){
        return(hashCode);
    }
}
