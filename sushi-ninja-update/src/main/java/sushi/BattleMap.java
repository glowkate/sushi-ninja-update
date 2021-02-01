/*
    This class is in charge of storing information about the tiles in a map.
    It's in charge of moving fighters to other tiles and the likes.
 */
package sushi;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.io.Serializable;

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
public class BattleMap implements Serializable{
    final private HashMap<Coord, Tile> tiles;
    final private int mapX;
    final private int mapY;
    
    /*
    Init the BattleMap from a string.
    
    @param mapInput A string of charicters to be translated into a map
    @param mapX The x value of the map
    @param mapY The y value of the map
    */
    public BattleMap(String mapInput, int mapX, int mapY){
        tiles = new HashMap();
        this.mapX = mapX;
        this.mapY = mapY;
        makeMap(mapInput);
        linkMap();
    }
    
    /*
    Translates a string into a BattleMap that the class then stores.
    A 'w' is a wall
    An 'o' is an openspace
    Example string:
    wwwww
    oowoo
    owowo
    ooooo
    
    @param mapInput The string of charicters to be translated into a map
    */
    private void makeMap(String mapInput){
        for(int x = 0; x < mapX; x++){
            for(int y = 0; y < mapY; y++){
                char tileTypeChar = mapInput.charAt(x + y*mapX);
                Coord newTileCoord = new Coord(x, y);
                TileFactory tf = TileFactory.getInstance();
                Tile newTile = tf.makeTile(tileTypeChar, newTileCoord);
                tiles.put(newTileCoord, newTile);
            }
        }
    }
    
    /*
    Links each of the tiles in the BattleMap with ajacent tiles.
    
    Used to make the graph for the path finder
    */
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
    
    /*
    Links two tiles.
    
    @param tile1 The first tile to be linked
    @param tile2 The second tile to be linked
    */    
    private void linkTiles(Tile tile1, Tile tile2){
        tile1.addLinkedTile(tile2);
        tile2.addLinkedTile(tile1);
    }
    
    /*
    Gets a linked list of tiles that represents a path between two
    spesified coords. Not all tiles can be walked on. This function
    uses bredth first search to find the shortest valid route between
    the two tiles.
    
    @param origin The starting coordinate
    @param end The destination coordinate
    @param fighter The fighter to be travercing the path (may effect what tiles are walkable)
    @return A LinkedList of tiles representing a path between the two coords.
    */
    public LinkedList<Tile> findPathBetweenTwoTiles
            (Coord origin, Coord end, Fighter fighter){
        //Set up vars
        final Tile endTile = tiles.get(end);
        Tile currentTile = tiles.get(origin);
        //Represents the path of tiles.
        LinkedList<Tile> currentTilePath;
        ArrayList<Tile> currentLinkedTiles;
        LinkedList<Tile> tileQue = new LinkedList();
        HashSet<Tile> visitedTiles = new HashSet();
        HashMap<Tile, LinkedList<Tile>> tilePaths = new HashMap();
        
        tilePaths.put(currentTile, new LinkedList());
        
        boolean shouldKeepSearching = true;
        while(currentTile != endTile && shouldKeepSearching){
            currentLinkedTiles = currentTile.getLinkedTiles();
            //For each of the selected tile's neighbours...
            for(Tile t : currentLinkedTiles){
                //True if they can be walked on and we havn't visited them yet
                boolean canPass = t.canEnter(fighter, currentTile) &&
                        currentTile.canLeave(fighter, t) &&
                        !visitedTiles.contains(t);
                //If they're valid for processing
                if(canPass){
                    //Add it onto the que and record the path to that tile
                    currentTilePath = (LinkedList<Tile>)
                    tilePaths.get(currentTile).clone();
                    currentTilePath.offer(currentTile);
                    //Mark the tile as visited
                    visitedTiles.add(t);
                    tilePaths.put(t, currentTilePath);
                    tileQue.offer(t);
                }
            }
            //Ends if theres no more tiles to visit or if we find the end tile.
            shouldKeepSearching = !tileQue.isEmpty();
            currentTile = shouldKeepSearching? tileQue.pop() : null;
        }
        
        LinkedList<Tile> returnPath;
        LinkedList<Tile> pathToEndTile;
        
        boolean didFindPathToTile = currentTile == endTile;
        
        //Checks to see if the end tile was found
        if(didFindPathToTile){
            //If a path was found, return a path of tiles
            //tilePaths will already have the path stored in, save for the last tile.
            pathToEndTile = tilePaths.get(currentTile);
            pathToEndTile.offer(endTile);
            returnPath = pathToEndTile;
        }
        else{
            //No path was found
            returnPath = null;
        }
        
        return(returnPath);
    }
    
    /*
    Checks the result of getProjectilePath to see if any of
    the tiles the projectile passes through obscure the
    projectile.
    
    @param origin The starting coordinate where the projectile is being fired from
    @param target The destination of the projectile      
    @return True if the point has line of sight on the other
    */
    public boolean checkLineOfSight(Coord origin, Coord target){
        ArrayList<Tile> projectilePath = getProjectilePath(origin, target);
        for (Tile t : projectilePath){
            if(t.isObstructing()){
                return(false);
            }
        }
        return(true);
    }        
            
    /*
    - Figure out the origin and target.
    - Figure out what dirrection we're going.
    - Figure out the T we need to go from a horizontal or vertical grid line.
    - Figure out the T we need to get to the first edge, which is also the next edge.
    - Add grid squares until we arrive at our destination
    
    Link to paper on algorithm used
    https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.42.3443&rep=rep1&type=pdf
    
    @param origin The starting coordinate where the projectile is being fired from
    @param target The destination of the projectile 
    @return An ArrayList of tiles containing each tile touching the projectile's path.
    */
    public ArrayList<Tile> getProjectilePath(Coord origin, Coord target){
        ArrayList<Tile> projectilePath = new ArrayList();
        
        //Figure out the origin and target.
        final double originX = origin.getX() + 0.5;
        final double originY = origin.getY() + 0.5;
        final double targetX = target.getX() + 0.5;
        final double targetY = target.getY() + 0.5;
        
        //Figure out what dirrection we're going
        final boolean isTargetXBigger = originX < targetX;
        final boolean isTargetYBigger = originY < targetY;
        
        final int xIncrease = isTargetXBigger? 1 : -1;
        final int yIncrease = isTargetYBigger? 1 : -1;
        
        //Vector between the two points
        final double routeX = targetX - originX;
        final double routeY = targetY - originY;
        
        //How much to increment XT and YT by when we cross a grid edge.
        final double incrementXT = routeX == 0? 5 : Math.abs(1/routeX);
        final double incrementYT = routeY == 0? 5 : Math.abs(1/routeY);
        
        //Figure out the T we need to go from a horizontal or vertical grid line
        double nextXT = incrementXT/2;
        double nextYT = incrementYT/2;
        
        int currentX = origin.getX();
        int currentY = origin.getY();
        
        Coord crntCoord = origin;
        Tile crntTile;
        
        while(!crntCoord.equals(target)){
            //Figure out the T we need to get to the next edge.
            if(nextXT < nextYT){
                //Here we cross an X-edge
                currentX = currentX + xIncrease;
                nextXT += incrementXT;
            }
            else{
                //Here we cross an Y-edge
                currentY = currentY + yIncrease;
                nextYT += incrementYT;
            }
            //Add grid squares until we arrive at our destination
            crntCoord = new Coord(currentX, currentY);
            crntTile = tiles.get(crntCoord);
            projectilePath.add(crntTile);
        }
        return(projectilePath);
    }
            
    /*
    @param x The x value of the requested tile
    @param y The y value of the requested tile
    @return The tile at the requested x and y
    */
    public Tile getTile(int x, int y){
        return getTile(new Coord(x, y));
    }
    
    /*
    @param xy The coord of the requested tile
    @return The tile at the requested coord
    */
    public Tile getTile(Coord xy){
        return tiles.get(xy);
    }
    
    /*
    For testing use ONLY
    @return The HashMap containing the tiles.
    */
    public HashMap<Coord, Tile> getMap(){
        return (tiles);
    }
}
