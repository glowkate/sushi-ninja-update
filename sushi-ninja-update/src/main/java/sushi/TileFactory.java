/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import java.util.HashMap;
import java.util.function.BiFunction;
import sushi.tiles.*;

/**
 *
 * @author kate
 */

/*
A singleton used to produce objects that implement the Tile interface.
It is responcable for giving each one a unique hash code, it does this
using a global counter that is called exclusivly durring tile creation.

It uses two hashmaps, one for converting charicters into tile types and
one for converting tile types into lambdas. These lambdas are what make the
actual tile. There's a unique lambda for each potential tile to be created.
*/
public class TileFactory {
    private static TileFactory instance;
    private int hashCodeCounter;
    
    private final HashMap<TileType, BiFunction<Coord, Integer, Tile>> typesToTiles;
    private final HashMap<Character, TileType> charToTypes;
    
    /*
    Init for the singleton. Gives values for each of the varuables.
    */
    private TileFactory(){
        hashCodeCounter = 0;
        
        charToTypes = new HashMap();
        typesToTiles = new HashMap();
        
        charToTypes.put('w', TileType.WALL);
        charToTypes.put('o', TileType.OPENSPACE);
        
        typesToTiles.put(TileType.WALL, (c, h) -> new WallTile(c, h));
        typesToTiles.put(TileType.OPENSPACE, (c, h) -> new OpenspaceTile(c, h));
    }
    
    /*
    Returns the instance of the TileFactory. Will always return a pointer to
    the same instance.
    
    @return The instance of the TileFactory.
    */
    public static TileFactory getInstance(){
        if (instance == null){
            instance = new TileFactory();
        }
        return (instance);
    }
    
    /*
    Creates a tile, the kind of tile created is based on the input char
    w - wall, o - openspace
    
    @param type The charicter to be translated into a tile
    @param XY The XY coordinates of the new tile
    @return The created tile
    */
    public Tile makeTile(char type, Coord XY){
        return(makeTile(charToTypes.get(type), XY));
    }
    
    /*
    Creates a tile, the kind of tile created is spesified in parameters
    
    @param type The TileType to be translated into a tile
    @param XY The XY coordinates of the new tile
    @return The created tile
    */
    public Tile makeTile(TileType type, Coord XY){
        return(typesToTiles.get(type).apply(XY, nextHashValue()));
    }
    
    /*
    Generates a number. Each generated number will always be diffrent.
    */
    private int nextHashValue(){
        hashCodeCounter++;
        return hashCodeCounter;
    }
}
