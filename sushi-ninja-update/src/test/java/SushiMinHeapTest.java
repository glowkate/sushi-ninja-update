/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import sushi.SushiMinHeap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author kate
 */
public class SushiMinHeapTest {
    @Test
    public void sushiMinHeapWorks(){
        ArrayList<Comparable> arrayInput = new ArrayList();
        ArrayList<Comparable> arrayGolden1 = new ArrayList();
        ArrayList<Comparable> arrayGolden2 = new ArrayList();
        
        arrayInput.add(5);
        arrayInput.add(6);
        arrayInput.add(2);
        arrayInput.add(7);
        arrayInput.add(8);
        arrayInput.add(3);
        arrayInput.add(1);
        arrayInput.add(9);
        arrayInput.add(4);
        
        arrayGolden1.add(1);
        arrayGolden1.add(3);
        arrayGolden1.add(2);
        arrayGolden1.add(4);
        arrayGolden1.add(8);
        arrayGolden1.add(6);
        arrayGolden1.add(5);
        arrayGolden1.add(9);
        arrayGolden1.add(7);
        
        arrayGolden2.add(3);
        arrayGolden2.add(7);
        arrayGolden2.add(4);
        arrayGolden2.add(9);
        arrayGolden2.add(8);
        arrayGolden2.add(6);
        arrayGolden2.add(5);
        
        SushiMinHeap heapTest = new SushiMinHeap(arrayInput);
        ArrayList<Comparable> arrayTest = heapTest.get();
        
        assertEquals(arrayTest, arrayGolden1);
        
        assertEquals(heapTest.popFromHeap(), 1);
        assertEquals(heapTest.popFromHeap(), 2);
        
        assertEquals(arrayTest, arrayGolden2);
    }
}
