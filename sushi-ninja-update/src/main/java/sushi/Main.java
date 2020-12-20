/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import sushi.fighters.DustbunnyFighter;

/**
 *
 * @author kate
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String testImport = 
                 "oooo"
                +"owwo"
                +"owwo"
                +"ooow";
        BattleMap test = new BattleMap(testImport,4,4);
        
        Fighter testFighter = new DustbunnyFighter();
        System.out.println(test.findPathBetweenTwoTiles(new Coord(2, 0), new Coord(2, 3), testFighter));
    }
    
}
