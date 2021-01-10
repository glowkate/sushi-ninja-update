/*
    Represents the fighter's current status, that is to say if the fighter is
    alive, dead, or are despawned.
 */
package sushi;

/**
 *
 * @author kate
 */

/*
An enum used to tell if a fighter is dead or alive. Made as an enum instead of a
boolean to allow for more status options in the future.
*/
public enum FighterStatus {
    ALIVE, DEAD;
}
