/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sushi.TileFactory;
import sushi.TileType;
import sushi.Tile;
import sushi.Coord;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author kate
 */
public class TileFactoryTest {
    @Test
    public void tileFactoryMakeTileWorks(){
        TileFactory tp = TileFactory.getInstance();
        for(TileType type : TileType.values()){
            Tile testTile = tp.makeTile(type, new Coord(1, 1));
            assertNotNull(testTile);
        }
    }
}
