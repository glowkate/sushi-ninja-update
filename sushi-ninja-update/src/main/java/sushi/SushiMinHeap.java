/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sushi;

import java.util.ArrayList;

/**
 *
 * @author kate
 */

/*
This is a class for the creation of heaps. A min heap is a tree where any one
child is higher in value then its parent, with the lowest valued object in
the heap being at the top of the tree. This heap will use any object that
implements the comparable interface as that is the interface used to
figure what of two objects is greater in value.
*/
public class SushiMinHeap {
    
    public ArrayList<Comparable> heap;
    
    /*
    Init statement for the heap.
    
    @param initList An unsorted list to be put into the heap.
    */
    public SushiMinHeap(ArrayList<Comparable> initList){
        heap = new ArrayList();
        initList.forEach(c -> { addToHeap(c); });
    }
    
    /*
    Adds a comparable object to the heap.
    
    @param o the comparable object to be added.
    */
    public void addToHeap(Comparable o){
        heap.add(o);
        bubbleUp(heap.size() - 1);
    }
    
    /*
    Removes the topmost item from the heap and returns it, resorting
    itself by calling bubbleDown afterwards.
    
    @return The removed comparable item.
    */
    public Comparable popFromHeap(){
        swap(0, heap.size() - 1);
        Comparable removedComparable = heap.get(heap.size() - 1);
        heap.remove(heap.size() - 1);
        bubbleDown(0);
        return(removedComparable);
    }
    
    /*
    Returns the ArrayList that holds the data for the heap.
    
    @return A tree stored in an ArrayList 
    */
    public ArrayList<Comparable> get(){
        return (heap);
    }
    
    /*
    Returns true if the heap is empty
    
    @return A boolean. 
    */
    public boolean isEmpty(){
        return(heap.isEmpty());
    }
    
    /*
    Switches the items at the two spesified indexes within the heap.
    
    @param index1 The index of the first item to be swapped.
    @param index2 The index of the second item to be swapped.
    */
    private void swap(int index1, int index2){
        //No check to see if the element exists because I want it to crash in that case
        Comparable storage = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, storage);
    }
    
    /*
    Swaps the item at the spesified index with its parent nodes until the
    item is balanced within the heap. Recursive.
    
    @param index The index currently being analized and possably swapped.
    */
    private void bubbleUp(int index){
        if ( index == 0 ) {
            return; // stop recursing at the top
        }
        
        int nextIndex = ( index - 1 ) / 2;
        Comparable nextComparable = heap.get(nextIndex);
        Comparable currentComparable = heap.get(index);
        
        if(currentComparable.compareTo(nextComparable) > 0){
            return;
        }
        swap(index, nextIndex);
        bubbleUp(nextIndex);
    }
    
    /*
    Swaps the item at the spesified index with its smaller children it in order
    to maintan the heap.
    
    @param index The index currently being analized and possably swapped.
    */
    private void bubbleDown(int index){
        final int indexChild1 = index * 2 + 1;
        final int indexChild2 = index * 2 + 2;
        
        final boolean indexChild1Exists = indexChild1 < heap.size();
        final boolean indexChild2Exists = indexChild2 < heap.size();
        
        // Back out of recursion if we're at the bottom of the heap
        if (!indexChild1Exists && !indexChild2Exists){
            return;
        }
        
        /*
        Not making a helper function as to keep all recursive elements
        within one function only.
        */
        if (indexChild1Exists && indexChild2Exists){
            // If both indexes exist, pick the smaller of the two to compare
            Comparable nextComparable1 = heap.get(indexChild1);
            Comparable nextComparable2 = heap.get(indexChild2);
            boolean isComparable1Higher = nextComparable1.compareTo(nextComparable2) > 0;
            Comparable selectedComparable = isComparable1Higher? nextComparable2 : nextComparable1;
            int selectedIndexChild = isComparable1Higher? indexChild2 : indexChild1;
            Comparable currentComparable = heap.get(index);
            // Check to see if the child is smaller then the parent
            if (currentComparable.compareTo(selectedComparable) > 0){
                swap(index, selectedIndexChild);
                bubbleDown(selectedIndexChild);
            }
        }
        else if(indexChild1Exists){
            // If only the first index exists, compare it to the parent node
            Comparable nextComparable = heap.get(indexChild1);
            Comparable currentComparable = heap.get(index);
            // Check to see if the child is smaller then the parent
            if (currentComparable.compareTo(nextComparable) > 0){
                swap(index, indexChild1);
                bubbleDown(indexChild1);
            }
        }    
    }
}
