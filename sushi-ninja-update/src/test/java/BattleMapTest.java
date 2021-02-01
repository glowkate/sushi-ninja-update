/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import sushi.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;


/**
 *
 * @author kate
 */
public class BattleMapTest {
    
    @Test
    public void BattleMapInitWorks(){
        /*
        This string represents a map.
        A 'w' is a wall (blocks line of sight)
        An 'o' is an openspace (allows line of sight)
        */
        String inputString = 
                "wwwwoow"+
                "woowwow"+
                "oooooow"+
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
        /*
        This string represents a map.
        A 'w' is a wall (blocks line of sight)
        An 'o' is an openspace (allows line of sight)
        
        This test:
        - Inits a map
        - Runs the pathfinder between two points
        - Creates the expected path to have been taken
        - Makes sure the two paths matched
        */
        String inputString = 
                "wwwwoow"+
                "woowwow"+
                "oooooow"+
                "wwooooo";
        
        //Init a map
        BattleMap mapTest = new BattleMap(inputString, 7, 4);
        
        Coord coordIn1 = new Coord(2,1);
        Coord coordIn2 = new Coord(4,0);
        FighterFactory ff = FighterFactory.getInstance();
        Fighter fighterIn = ff.makeFighter(FighterType.DUSTBUNNY);
        
        //Run the pathfinder between two points
        LinkedList<Tile> pathTest = mapTest.findPathBetweenTwoTiles(coordIn1, coordIn2, fighterIn);
        
        //Create the expected path to have been taken
        LinkedList<Tile> pathGolden = new LinkedList<>();
        pathGolden.offer(mapTest.getTile(2,1));
        pathGolden.offer(mapTest.getTile(2,2));
        pathGolden.offer(mapTest.getTile(3,2));
        pathGolden.offer(mapTest.getTile(4,2));
        pathGolden.offer(mapTest.getTile(5,2));
        pathGolden.offer(mapTest.getTile(5,1));
        pathGolden.offer(mapTest.getTile(5,0));
        pathGolden.offer(mapTest.getTile(4,0));
        
        //Make sure they matched
        assertEquals(pathGolden, pathTest);
    }
    
    @Test
    public void BattleMapCheckLineOfSightWorks(){
        /*
        This string represents a map.
        A 'w' is a wall (blocks line of sight)
        An 'o' is an openspace (allows line of sight)
        If the projectile path crosses a wall tile it should
        return false. This test ensures that a projectile
        cannot go through a wall.
        */
        String inputString = 
                "ooowoow"+
                "ooowwow"+
                "oooooow"+
                "ooooooo";
        
        BattleMap mapTest = new BattleMap(inputString, 7, 4);
        
        //Unobstructed column, should return true.
        boolean test1 = mapTest.checkLineOfSight(new Coord(5,0), new Coord(5,3));
        assertTrue(test1);
        
        //Unobstructed column, should return true.
        boolean test2 = mapTest.checkLineOfSight(new Coord(5,3), new Coord(5,0));
        assertTrue(test2);
        
        //Unobstructed row, should return true.
        boolean test3 = mapTest.checkLineOfSight(new Coord(5,3), new Coord(3,3));
        assertTrue(test3);
        
        //Unobstructed row, should return true.
        boolean test4 = mapTest.checkLineOfSight(new Coord(3,3), new Coord(5,3));
        assertTrue(test4);
        
        //Unobstructed diagonal in upper left of map, should return true.
        boolean test5 = mapTest.checkLineOfSight(new Coord(2,3), new Coord(0,0));
        assertTrue(test5);
        
        //Unobstructed diagonal in upper left of map, should return true.
        boolean test6 = mapTest.checkLineOfSight(new Coord(0,0), new Coord(2,3));
        assertTrue(test6);
        
        //Obstructed diagonal in upper half of map, hits wall at 5,1
        boolean test7 = mapTest.checkLineOfSight(new Coord(2,2), new Coord(4,0));
        assertTrue(!test7);
        
        //Obstructed diagonal in upper half of map, hits wall at 5,1
        boolean test8 = mapTest.checkLineOfSight(new Coord(4,0), new Coord(2,2));
        assertTrue(!test8);
    }    
    
    @Test
    public void BattleMapGetProjectilePathWorks(){
        /*
        This is not a test for line of sight, this is testing the
        BattleMap's capability to find all of the tiles that touch
        a line between two points.
        */
        String inputString =
                  "ooo"
                + "ooo"
                + "ooo"
                + "ooo";
        BattleMap mapTest = new BattleMap(inputString, 3, 4);
        
        Coord coordIn1 = new Coord(2,3);
        Coord coordIn2 = new Coord(0,0);
        
        ArrayList<Tile> arrayListTest = mapTest.getProjectilePath(coordIn1, coordIn2);

        /* There should be all the tiles on the line from 2,3 to 0,0
           save for 2,3 in the produced ArrayList
           Origin point is not included as thats where the projectile is
           being fired from.
        */
        ArrayList<Tile> arrayListGolden = new ArrayList();        
        arrayListGolden.add(mapTest.getTile(2,2));
        arrayListGolden.add(mapTest.getTile(1,2));
        arrayListGolden.add(mapTest.getTile(1,1));
        arrayListGolden.add(mapTest.getTile(0,1));
        arrayListGolden.add(mapTest.getTile(0,0));
        
        assertEquals(arrayListTest, arrayListGolden);
    }
    
    
    /*
    This test serializes a battle map and loads it in again. The goal is to
    test to ensure that the BattleMap can be serialized, even when modified.
    
    This test:
    - Inits a map
    - Serializes the map to disk
    - Load the saved map from disk
    - Check to make sure the two maps are identical
    */
    @Test
    public void BattleMapSerializationTest(){
        /*
        This string represents a map.
        A 'w' is a wall (blocks line of sight)
        An 'o' is an openspace (allows line of sight)
        */
        String inputString = 
                "wwwwwww"+
                "oooowow"+
                "owwooow"+
                "ooooooo";
        //Inits a map
        BattleMap mapGolden = new BattleMap(inputString, 7, 4);
        FileOutputStream fos;
        ObjectOutputStream out;
        //Serializes the map to disk
        try
        {
          fos = new FileOutputStream("battlemaptest.ser");
          out = new ObjectOutputStream(fos);
          out.writeObject(mapGolden);
          out.close();
        }
        catch(IOException ex)
        {
          ex.printStackTrace();
        }
        
        FileInputStream fis = null;
        ObjectInputStream in = null;
        BattleMap mapTest = null;
        //Load the saved map from disk
        try
        {
            fis = new FileInputStream("battlemaptest.ser");
            in = new ObjectInputStream(fis);
            mapTest = (BattleMap)in.readObject();
            in.close();
        }
        catch(IOException | ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        
        //Check to make sure the two maps are identical
        assertEquals(mapTest.getMap(), mapGolden.getMap());
    }
}
