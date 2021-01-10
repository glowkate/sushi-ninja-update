/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.LinkedList;
import sushi.*;
import sushi.tiles.*;
import sushi.fighters.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 *
 * @author kate
 */
public class BattleMapTest {
    
    @Test
    public void BattleMapInitWorks(){
        String inputString = 
                "wwwwoow"+
                "woowwow"+
                "oooooow" +
                "wwooooo";
        BattleMap testMap = new BattleMap(inputString, 7, 4);
        TileFactory tf = TileFactory.getInstance();
        
        
        Tile testTile1 = testMap.getTile(3, 1);
        Tile testTile2 = testMap.getTile(5, 3);
        
        Tile goldenTile1 = tf.makeTile(TileType.WALL, new Coord(3, 1));
        Tile goldenTile2 = tf.makeTile(TileType.OPENSPACE, new Coord(5, 3));
        
        assertEquals(testTile1, goldenTile1);
        assertEquals(testTile2, goldenTile2);
    }
    
    @Test
    public void BattleMapFindPathBetweenTwoTilesWorks(){
        String inputString = 
                "wwwwoow"+
                "woowwow"+
                "oooooow" +
                "wwooooo";
        
        BattleMap mapTest = new BattleMap(inputString, 7, 4);
        
        Coord coordIn1 = new Coord(2,1);
        Coord coordIn2 = new Coord(4,0);
        Fighter fighterIn = new DustbunnyFighter();
        LinkedList<Tile> pathTest = mapTest.findPathBetweenTwoTiles(coordIn1, coordIn2, fighterIn);
        LinkedList<Tile> pathGolden = new LinkedList<>();
        pathGolden.offer(mapTest.getTile(2,1));
        pathGolden.offer(mapTest.getTile(2,2));
        pathGolden.offer(mapTest.getTile(3,2));
        pathGolden.offer(mapTest.getTile(4,2));
        pathGolden.offer(mapTest.getTile(5,2));
        pathGolden.offer(mapTest.getTile(5,1));
        pathGolden.offer(mapTest.getTile(5,0));
        pathGolden.offer(mapTest.getTile(4,0));
        assertEquals(pathTest, pathGolden);
    }
}
