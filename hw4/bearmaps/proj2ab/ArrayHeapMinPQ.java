package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Priority queue where objects have a priority that is provided
 * extrinsically, i.e. are are supplied as an argument during insertion
 * and can be changed using the changePriority method.
 */

/* sort by priority, how to get item quickly,
 * because we don't know the connection among item and priority.......
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private class Node{
        public T item;
        public double priority;
        public Node(T item, double priority) {
            this.item = item;
            this.priority = priority;
        }
    }

    public ArrayList<Node> arrayHeap;
    private HashMap arrayItemIndex;
    public ArrayHeapMinPQ() {
        arrayHeap = new ArrayList<Node>();
        arrayItemIndex = new HashMap(); //record the index of an item
    }

    @Override
    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if(contains(item)) {
            throw new IllegalArgumentException();
        } else {
            arrayHeap.add(new Node(item, priority));
            int currentNum = arrayHeap.size();
            arrayItemIndex.put(item, currentNum - 1);
            up(currentNum);
        }


    }

    private void up(int currentNum) {
        if(currentNum <= 1) {
            return;
        } else {
            int ancestorNum = currentNum / 2;
            //change order
            if(arrayHeap.get(currentNum - 1).priority < arrayHeap.get(ancestorNum - 1).priority) {
                Node currentNode = arrayHeap.get(currentNum - 1);
                arrayHeap.set(currentNum - 1, arrayHeap.get(ancestorNum - 1));
                arrayItemIndex.replace(arrayHeap.get(ancestorNum - 1).item, currentNum - 1);
                arrayHeap.set(ancestorNum - 1, currentNode);
                arrayItemIndex.replace(currentNode.item, ancestorNum - 1);
                currentNum = ancestorNum;
                up(currentNum);
            }
        }
    }

    @Override
    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return arrayItemIndex.containsKey(item);
    }

    @Override
    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (arrayHeap.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            return(arrayHeap.get(0).item);
        }
    }

    @Override
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (arrayHeap.isEmpty()) {
            throw new NoSuchElementException();
        } else {
            T smallestItem = arrayHeap.get(0).item;
            arrayHeap.set(0, arrayHeap.get(arrayHeap.size() - 1));
            arrayItemIndex.replace(arrayHeap.get(0).item, 0);
            arrayHeap.remove(arrayHeap.size() - 1);
            arrayItemIndex.remove(smallestItem);
            down(1);
            return(smallestItem);
        }
    }

    private void down(int currentNum) {
        int sonNum1 = currentNum * 2;
        int sonNum2 = sonNum1 + 1;
        int sonNum;
        if (sonNum1 > arrayHeap.size()) {
            return;
        } else {
            if (sonNum1 == arrayHeap.size() || arrayHeap.get(sonNum1 - 1).priority < arrayHeap.get(sonNum1 - 2).priority) {
                sonNum = sonNum1;
            } else {
                sonNum = sonNum2;
            }
            //change order
            if (arrayHeap.get(sonNum - 1).priority < arrayHeap.get(currentNum - 1).priority) {
                Node currentNode = arrayHeap.get(currentNum - 1);
                arrayHeap.set(currentNum - 1, arrayHeap.get(sonNum - 1));
                arrayItemIndex.replace(arrayHeap.get(sonNum - 1).item, currentNum - 1);
                arrayHeap.set(sonNum - 1, currentNode);
                arrayItemIndex.replace(currentNode.item, sonNum - 1);
                currentNum = sonNum;
                down(currentNum);
            }
        }
    }

    @Override
    /* Returns the number of items in the PQ. */
    public int size() {
        return arrayHeap.size();
    }

    @Override
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority){
        if (!contains(item)) {
            throw new NoSuchElementException();
        } else {
            int currentNum = (int)arrayItemIndex.get(item) + 1;
            arrayHeap.set(currentNum - 1, new Node(item, priority));
            up(currentNum);
            down(currentNum);
        }
    }
}
