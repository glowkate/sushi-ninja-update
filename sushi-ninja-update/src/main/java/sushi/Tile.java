/*
    This interface is used with the map class. Instances of this interface
    make up the playable game board.
 */
package sushi;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author kate
 */

/*
An interface for creating the tiles that make up the battle map. Classes that
extend this interface should be put in the tiles folder and have Tile at the
end of their name. Tiles are created using the TileFactory singleton.
*/
public interface Tile {
    
    //Returns the Tile's XY coordinate.
    public Coord getXY();
    
    //Returns an arraylist of ajacent tiles.
    public ArrayList<Tile> getLinkedTiles();
    
    //Adds a tile to this list's link tiles.
    public void addLinkedTile(Tile newTile);
    
    //Returns the image used to represent the Tile.
    public File getImageFile();
    
    //Returns true if the tile obstructs ranged attacks.
    public boolean isObstructing();
    
    //Returns a number from 1-5 that represents how favorable a path is to take. 
    //< 3 is a more favorable path, > 3 is a less favorable path.
    public int getPathWeight();
    
    //Returns true if the given fighter can enter this tile from the given tile
    public boolean canEnter(final Fighter enteringFighter, final Tile orginTile);
    
    //Returns true if the given fighter can leave this tile to the given tile.
    public boolean canLeave(final Fighter leavingFighter, final Tile destinationTile);
    
    //Is called when a fighter enters this tile.
    public void enter(final Fighter enteringFighter, final Tile orginTile);
    
    //Is called when a fighter leaves this tile. May or may not do something.
    public void leave(final Fighter leavingFighter, final Tile leavingTile);
    
    //Is called at the end of an in-game round
    public void roundEnd();
    
    @Override
    public String toString();
    
    @Override
    public boolean equals(final Object obj);
    
    @Override
    public int hashCode();
}