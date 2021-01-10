/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import sushi.Coord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author kate
 */
public class CoordTest {
    @Test
    public void coordGetXWorks(){
        Coord testCoord = new Coord(12, 8);
        int xTest = testCoord.getX();
        int xGolden = 12;
        assertEquals(xTest, xGolden);
    }

    @Test
    public void coordGetYWorks(){
        Coord testCoord = new Coord(21, 2);
        int yTest = testCoord.getY();
        int yGolden = 2;
        assertEquals(yTest, yGolden);
    }

    @Test
    public void coordToStringWorks(){
        Coord coordTest = new Coord(6,2);
        String strTest = coordTest.toString();
        String strGolden = "6,2";
        assertEquals(strTest, strGolden);
    }

    @Test
    public void coordEqualsWorks(){
        Coord coordTest = new Coord(124, 563);
        Coord coordGolden = new Coord(124, 563);
        assertEquals(coordTest, coordGolden);
    }

    @Test
    public void coordHashCodeWorks(){
        Coord coordTest1 = new Coord(1, 2);
        Coord coordTest2 = new Coord(2, 1);
        int hashTest1 = coordTest1.hashCode();
        int hashTest2 = coordTest2.hashCode();
        int hashGolden1 = 285;
        int hashGolden2 = 567;
        assertEquals(hashTest1, hashGolden1);
        assertEquals(hashTest2, hashGolden2);
    }
}
