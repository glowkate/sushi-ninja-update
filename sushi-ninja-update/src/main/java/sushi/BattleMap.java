/*
    This class is in charge of storing information about the tiles in a map.
    It's in charge of moving fighters to other tiles and the likes.
 */
package sushi;

import java.util.HashMap;

/**
 *
 * @author kate
 */
public class BattleMap {
    private HashMap<Coord, Tile> tiles;
    private int maxX;
    private int maxY;
}
