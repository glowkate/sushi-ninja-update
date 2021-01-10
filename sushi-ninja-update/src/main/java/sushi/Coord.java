/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

/**
 *
 * @author kate
 */

/*
A 2D, hashable Coordinate. Used as keys for hashmaps along with storing locations
of things in the game.
*/
public class Coord {
    private int x;
    private int y;

    public Coord(int initX, int initY){
        x = initX;
        y = initY;
    }

    //FOR TESTING ONLY
    public Coord(){
        x = 0;
        y = 0;
    }

    public int getX(){
        return(x);
    }

    public int getY(){
        return(y);
    }

    @Override
    public String toString(){
        return (x + "," + y);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || obj.getClass()!= this.getClass()) {
            return false;
        }
        else {
            Coord compCoord = (Coord) obj;
            return (x == compCoord.getX() && y == compCoord.getY());
        }
    }

    @Override
    public int hashCode(){
        return(x * 283 + y);
    }
}
