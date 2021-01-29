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
        String inputString = 
                "wwwwoow"+
                "woowwow"+
                "oooooow"+
                "wwooooo";
        
        BattleMap mapTest = new BattleMap(inputString, 7, 4);
        
        Coord coordIn1 = new Coord(2,1);
        Coord coordIn2 = new Coord(4,0);
        FighterFactory ff = FighterFactory.getInstance();
        Fighter fighterIn = ff.makeFighter(FighterType.DUSTBUNNY);
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
    
    @Test
    public void BattleMapCheckLineOfSightWorks(){
        String inputString = 
                "ooowoow"+
                "ooowwow"+
                "oooooow"+
                "ooooooo";
        
        BattleMap mapTest = new BattleMap(inputString, 7, 4);
        Coord coordIn1 = new Coord(5,0);
        Coord coordIn2 = new Coord(5,3);
        Coord coordIn3 = new Coord(3,3);
        Coord coordIn4 = new Coord(2,3);
        Coord coordIn5 = new Coord(0,0);
        Coord coordIn6 = new Coord(2,2);
        Coord coordIn7 = new Coord(4,0);
        
        
        boolean test1 = mapTest.checkLineOfSight(coordIn1, coordIn2);
        boolean test2 = mapTest.checkLineOfSight(coordIn2, coordIn1);
        boolean test3 = mapTest.checkLineOfSight(coordIn2, coordIn3);
        boolean test4 = mapTest.checkLineOfSight(coordIn3, coordIn2);
        boolean test5 = mapTest.checkLineOfSight(coordIn4, coordIn5);
        boolean test6 = mapTest.checkLineOfSight(coordIn5, coordIn4);
        boolean test7 = mapTest.checkLineOfSight(coordIn6, coordIn7);
        boolean test8 = mapTest.checkLineOfSight(coordIn7, coordIn6);
        
        assertTrue(test1);
        assertTrue(test2);
        assertTrue(test3);
        assertTrue(test4);
        assertTrue(test5);
        assertTrue(test6);
        assertTrue(!test7);
        assertTrue(!test8);
    }    
    
    @Test
    public void BattleMapGetProjectilePathWorks(){
        String inputString =
                  "ooo"
                + "ooo"
                + "ooo"
                + "ooo";
        BattleMap mapTest = new BattleMap(inputString, 3, 4);
        
        Coord coordIn1 = new Coord(2,3);
        Coord coordIn2 = new Coord(0,0);
        
        ArrayList<Tile> arrayListTest = mapTest.getProjectilePath(coordIn1, coordIn2);
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
    */
    @Test
    public void BattleMapSerializationTest(){
        String inputString = 
                "wwwwwww"+
                "oooowow"+
                "owwooow"+
                "ooooooo";
        BattleMap mapGolden = new BattleMap(inputString, 7, 4);
        FileOutputStream fos;
        ObjectOutputStream out;
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
        
        assertEquals(mapTest.getMap(), mapGolden.getMap());
    }
}
