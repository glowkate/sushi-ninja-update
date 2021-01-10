/*
    This class is in charge of storing information about the tiles in a map.
    It's in charge of moving fighters to other tiles and the likes.
 */
package sushi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import sushi.tiles.*;

/**
 *
 * @author kate
 */

/*
A class for holding information about the map that is played on in game.
This class is responcable for finding the shortest path between two
tiles, calculating if one tile has line of sight on another, and creating/storing
all of the tiles used in a particular battle.
*/
public class BattleMap {
    private HashMap<Coord, Tile> tiles;
    private int mapX;
    private int mapY;
    
    public BattleMap(String mapInput, int mapX, int mapY){
        tiles = new HashMap();
        this.mapX = mapX;
        this.mapY = mapY;
        makeMap(mapInput);
        linkMap();
    }
    
    private void makeMap(String mapInput){
        for(int x = 0; x < mapX; x++){
            for(int y = 0; y < mapY; y++){
                char tileTypeChar = mapInput.charAt(x + y*mapX);
                Coord newTileCoord = new Coord(x, y);
                TileFactory tf = TileFactory.getInstance();
                Tile newTile = tf.makeTile(tileTypeChar, newTileCoord);
                System.out.println(newTile);
                tiles.put(newTileCoord, newTile);
            }
        }
      }
        
      private void linkMap(){
        Coord lastCoord;
        Coord nextCoord;
        Tile tile1;
        Tile tile2;
        //Linking the Xs
        for(int y = 0; y < mapY; y++){
            lastCoord = new Coord(0, y);
            for(int x = 1; x < mapX; x++){
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);

                linkTiles(tile1, tile2);
                lastCoord = nextCoord;
            }
        }

        // Linking the Ys
        for(int x = 0; x < mapX; x++){
            lastCoord = new Coord(x, 0);
            for(int y = 1; y < mapY; y++){
                nextCoord = new Coord(x, y);
                tile1 = (Tile)tiles.get(lastCoord);
                tile2 = (Tile)tiles.get(nextCoord);

                linkTiles(tile1, tile2);

                lastCoord = nextCoord;
            }
        }
      }
    
        
    private void linkTiles(Tile tile1, Tile tile2){
        tile1.addLinkedTile(tile2);
        tile2.addLinkedTile(tile1);
    }
    
    public LinkedList<Tile> findPathBetweenTwoTiles
            (Coord orgin, Coord end, Fighter fighter){
        final Tile endTile = tiles.get(end);
        Tile currentTile = tiles.get(orgin);
        LinkedList<Tile> currentTilePath;
        ArrayList<Tile> currentLinkedTiles;
        LinkedList<Tile> tileQue = new LinkedList();
        HashSet<Tile> visitedTiles = new HashSet();
        HashMap<Tile, LinkedList<Tile>> tilePaths = new HashMap();
        
        tilePaths.put(currentTile, new LinkedList());
        
        boolean shouldKeepSearching = true;
        while(currentTile != endTile && shouldKeepSearching){
            System.out.println(currentTile);
            currentLinkedTiles = currentTile.getLinkedTiles();
            for(Tile t : currentLinkedTiles){
                boolean canPass = t.canEnter(fighter, currentTile) &&
                        currentTile.canLeave(fighter, t) &&
                        !visitedTiles.contains(t);
                if(canPass){
                    currentTilePath = (LinkedList<Tile>)
                    tilePaths.get(currentTile).clone();
                    currentTilePath.offer(currentTile);
                    visitedTiles.add(t);
                    tilePaths.put(t, currentTilePath);
                    tileQue.offer(t);
                }
            }
            shouldKeepSearching = !tileQue.isEmpty();
            currentTile = shouldKeepSearching? tileQue.pop() : null;
        }
        
        LinkedList<Tile> returnPath;
        LinkedList<Tile> pathToEndTile;
        
        boolean didFindPathToTile = currentTile == endTile;
        
        if(didFindPathToTile){
            pathToEndTile = tilePaths.get(currentTile);
            pathToEndTile.offer(endTile);
            returnPath = pathToEndTile;
        }
        else{
            returnPath = null;
        }
        
        return(returnPath);
    }
    
    public Tile getTile(int x, int y){
        return getTile(new Coord(x, y));
    }
    
    public Tile getTile(Coord xy){
        return tiles.get(xy);
    }
}
