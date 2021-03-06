/*
 * An open space where anyone can walk.
 */
package sushi.tiles;

import java.io.File;
import java.util.ArrayList;
import sushi.*;

/**
 *
 * @author kate
 */
public class OpenspaceTile implements Tile {
    
    private final Coord XY;
    private final ArrayList<Tile> linkedTiles = new ArrayList();
    private boolean isOccupied;
    private final int hashCode;
    
    public OpenspaceTile(final int x, final int y, final int hash){
        XY = new Coord(x, y);
        isOccupied = false;
        hashCode = hash;
    }
    
    public OpenspaceTile(final Coord xy, final int hash){
        XY = xy;
        isOccupied = false;
        hashCode = hash;
    }

    //Returns the Tile's XY coordinate.
    @Override
    public Coord getXY(){
        return(XY);
    }
    
    //Returns an arraylist of ajacent tiles.
    @Override
    public ArrayList<Tile> getLinkedTiles(){
        return (linkedTiles);
    }
    
    //Adds a tile to this list's link tiles.
    @Override
    public void addLinkedTile(Tile newTile){
        linkedTiles.add(newTile);
    }
    
    //Returns the image used to represent the Tile.
    @Override
    public File getImageFile(){
        return(new File("sushi/data/OpenspaceTile.png"));
    }
    
    //Returns true if the tile obstructs ranged attacks.
    @Override
    public boolean isObstructing(){
        return(false);
    }
    
    //Returns a number from 1-5 that represents how favorable a path is to take. 
    //< 3 is a more favorable path, > 3 is a less favorable path.
    @Override
    public int getPathWeight(){
        return(3);
    }
    
    //Returns true if the given fighter can enter this tile from the given tile
    @Override
    public boolean canEnter(final Fighter enteringFighter, final Tile orginTile){
        return(!isOccupied);
    }
    
    //Returns true if the given fighter can leave this tile to the given tile.
    @Override
    public boolean canLeave(final Fighter leavingFighter, final Tile destinationTile){
        return(true);
    }
    
    //Is called when a fighter enters this tile.
    @Override
    public void enter(final Fighter enteringFighter, final Tile orginTile){
        isOccupied = true;
    }
    
    //Is called when a fighter leaves this tile. May or may not do something.
    @Override
    public void leave(final Fighter leavingFighter, final Tile leavingTile){
        isOccupied = false;
    }
    
    //Is called at the end of an in-game round
    @Override
    public void roundEnd(){}
    
    @Override
    public String toString(){
        return(XY + " OPENSPACE");
    }
    
    @Override
    public boolean equals(final Object obj){
        if(obj == null || obj.getClass()!= this.getClass()) {
            return (false);
        }
        else {
            Tile compTile = (Tile) obj;
            return (XY.equals(compTile.getXY()));
        }
    }
    
    @Override
    public int hashCode(){
        return(hashCode);
    }
}
