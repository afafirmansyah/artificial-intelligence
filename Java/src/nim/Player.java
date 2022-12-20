/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nim;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
/**
 *
 * @author Kaze
 */
public class Player {
    double egreedy;
    int win;
    
    public Player(int role, String[] stats, int[] parent, double egreedy){
        this.egreedy = egreedy;
        
    }
}
