/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import sushi.SushiMinHeap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author kate
 */
public class SushiMinHeapTest {
    @Test
    //- Initialize a test heap
    //- Check for initial balancing of heap
    //- Ensure that the pop function gets the smallest value in the heap
    //- Double check that the heap is still balanced
    //- More tests to ensure that the sorting/pop works
    
    public void sushiMinHeapWorks(){
        //Initialize a test heap
        final ArrayList<Comparable> arrayInput = new ArrayList(Arrays.asList
        (5,6,2,7,8,3,1,9,4));
        SushiMinHeap heapTest = new SushiMinHeap(arrayInput);     
        
        //Check for initial balancing of heap
        //
        /*
        Our heap should look like this
        
                        1
                    4       2
                  6   8   5   3
                 9 7
        */
        final ArrayList<Comparable> arrayGolden1 = new ArrayList(Arrays.asList
        (1,4,2,6,8,5,3,9,7));        
        assertEquals(arrayGolden1, heapTest.get());
        
        //Tests to ensure that the pop function gets the smallest value in the heap.
        assertEquals(1, heapTest.popFromHeap());
        assertEquals(2, heapTest.popFromHeap());
        
        //Double checking that the heap is still balanced
        /*
        Our heap should now look like this
        
                        3
                    4       5
                  6   8   8   7
        */
        final ArrayList<Comparable> arrayGolden2 = new ArrayList(Arrays.asList
        (3,4,5,6,8,9,7));        
        assertEquals(arrayGolden2, heapTest.get());
        
        //More tests to ensure that the sorting/pop works
        assertEquals(3, heapTest.popFromHeap());
        assertEquals(4, heapTest.popFromHeap());
        assertEquals(5, heapTest.popFromHeap());
        assertEquals(6, heapTest.popFromHeap());
        assertEquals(7, heapTest.popFromHeap());
        assertEquals(8, heapTest.popFromHeap());
        assertEquals(9, heapTest.popFromHeap());
    }
}
