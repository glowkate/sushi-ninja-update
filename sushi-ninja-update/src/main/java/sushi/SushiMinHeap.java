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
public class SushiMinHeap {
    
    public ArrayList<Comparable> heap;
    
    public SushiMinHeap(ArrayList<Comparable> initList){
        heap = new ArrayList();
        initList.forEach(c -> { addToHeap(c); });
    }
    
    public void addToHeap(Comparable o){
        heap.add(o);
        bubbleUp(heap.size() - 1);
    }
    
    public Comparable popFromHeap(){
        swap(0, heap.size() - 1);
        Comparable removedComparable = heap.get(heap.size() - 1);
        heap.remove(heap.size() - 1);
        bubbleDown(0);
        return(removedComparable);
    }
    
    public ArrayList<Comparable> get(){
        return (heap);
    }
    
    private void swap(int index1, int index2){
        //No check to see if the element exists because I want it to crash in that case
        Comparable storage = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, storage);
    }
    
    private void bubbleUp(int index){
        int nextIndex = (int)(index / 2 - 0.5);
        if(nextIndex >= 0){
            Comparable nextComparable = heap.get(nextIndex);
            Comparable currentComparable = heap.get(index);
            if(currentComparable.compareTo(nextComparable) < 0){
                swap(index, nextIndex);
                bubbleUp(nextIndex);
            }
        }
    }
    
    private void bubbleDown(int index){
        int indexChild1 = index * 2 + 1;
        int indexChild2 = index * 2 + 2;
        
        /*
        Not making a helper function as to keep all recursive elements
        within one function only.
        */
        if (indexChild1 < heap.size()){
            Comparable nextComparable = heap.get(indexChild1);
            Comparable currentComparable = heap.get(index);
            if (currentComparable.compareTo(nextComparable) > 0){
                swap(index, indexChild1);
                bubbleDown(indexChild1);
            }
        }
        
        if (indexChild2 < heap.size()){
            Comparable nextComparable = heap.get(indexChild2);
            Comparable currentComparable = heap.get(index);
            if (currentComparable.compareTo(nextComparable) > 0){
                swap(index, indexChild2);
                bubbleDown(indexChild2);
            }
        }
    }
}
