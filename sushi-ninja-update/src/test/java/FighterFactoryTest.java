/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import sushi.FighterFactory;
import sushi.FighterType;
import sushi.Fighter;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author kate
 */
public class FighterFactoryTest {
    @Test
    public void fighterFactoryMakeFighterWorks(){
        FighterFactory ff = FighterFactory.getInstance();
        for(FighterType type : FighterType.values()){
            Fighter testFighter = ff.makeFighter(type);
            assertNotNull(testFighter);
        }
    }
}
