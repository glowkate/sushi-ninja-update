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
public class TileFactory {
    private static TileFactory instance;
    private int hashCodeCounter;
    
    private HashMap<TileType, BiFunction<Coord, Integer, Tile>> typesToTiles;
    private HashMap<Character, TileType> charToTypes;
    
    private TileFactory(){
        hashCodeCounter = 0;
        
        charToTypes = new HashMap();
        typesToTiles = new HashMap();
        
        charToTypes.put('w', TileType.WALL);
        charToTypes.put('o', TileType.OPENSPACE);
        
        typesToTiles.put(TileType.WALL, (c, h) -> new WallTile(c, h));
        typesToTiles.put(TileType.OPENSPACE, (c, h) -> new OpenspaceTile(c, h));
    }
    
    public static TileFactory getInstance(){
        if (instance == null){
            instance = new TileFactory();
        }
        return (instance);
    }
    
    public Tile makeTile(char type, Coord XY){
        return(makeTile(charToTypes.get(type), XY));
    }
    
    public Tile makeTile(TileType type, Coord XY){
        return(typesToTiles.get(type).apply(XY, nextHashValue()));
    }
    
    private int nextHashValue(){
        hashCodeCounter++;
        return hashCodeCounter;
    }
}
