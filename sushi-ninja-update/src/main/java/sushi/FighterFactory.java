/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import java.util.HashMap;
import java.util.function.Function;
import sushi.fighters.DustbunnyFighter;

/**
 *
 * @author kate
 */
public class FighterFactory {
    private static FighterFactory instance;
    private int hashCodeCounter;
    
    private HashMap<FighterType, Function<Integer, Fighter>> typesToFighters;
    
    private FighterFactory(){
        hashCodeCounter = 0;
        
        typesToFighters = new HashMap();
        
        typesToFighters.put(FighterType.DUSTBUNNY, h -> new DustbunnyFighter(h));
    }
    
    public static FighterFactory getInstance(){
        if (instance == null){
            instance = new FighterFactory();
        }
        return (instance);
    }
    
    public Fighter makeFighter(FighterType type){
        return(typesToFighters.get(type).apply(nextHashValue()));
    }
    
    private int nextHashValue(){
        hashCodeCounter++;
        return hashCodeCounter;
    }
}
