/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi.gamemodes;

import sushi.Gamemode;
import sushi.BattleMap;
import sushi.Fighter;

import java.util.ArrayList;
/**
 *
 * @author kate
 */
public class DeathmatchGamemode implements Gamemode{
    
    private BattleMap gameMap;
    private ArrayList<Fighter> gameFighters;
    
    public DeathmatchGamemode(BattleMap gameMap, ArrayList<Fighter> gameFighters){
        this.gameMap = gameMap;
        this.gameFighters = gameFighters;
    }
    
    public boolean isGameDone(){
        return (true);
    }
    
    public void periodic(){
    }
}
