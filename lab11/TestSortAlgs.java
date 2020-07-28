import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSortAlgs {
    @Test
    public void testQuickSort() {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Beta");
        tas.enqueue("Alpha");
        tas.enqueue("Omega");
        Queue<String> tasSorted = QuickSort.quickSort(tas);
        assertEquals(tas.size(), tasSorted.size());
        assertTrue(isSorted(tasSorted));
    }

    @Test
    public void testRunTimeQuickSort () {
        int numbers = 100;
        Queue<Integer> tas2 = new Queue <> ();
        for (int i = 0; i < numbers; i++) {
            tas2.enqueue((int)Math.random() * numbers);
        }
        long startQuickSort = System.currentTimeMillis();
        Queue<Integer> tas2Sorted = QuickSort.quickSort(tas2);
        long endQuickSort =  System.currentTimeMillis();
        System.out.println(numbers + ": Seconds elapsed for QuickSort: " + (endQuickSort - startQuickSort) / 1000.0 + " seconds.");

        assertEquals(tas2.size(), tas2Sorted.size());
        assertTrue(isSorted(tas2Sorted));
    }

    @Test
    public void testMergeSort () {
        Queue<String> tas = new Queue<String>();
        tas.enqueue("Beta");
        tas.enqueue("Alpha");
        tas.enqueue("Omega");
        Queue<String> tasSorted = MergeSort.mergeSort(tas);
        assertEquals(tas.size(), tasSorted.size());
        assertTrue(isSorted(tasSorted));
    }

    @Test
    public void testRunTimeMergeSort () {
        int numbers = 100;
        Queue<Integer> tas2 = new Queue <> ();
        for (int i = 0; i < numbers; i++) {
            tas2.enqueue((int)Math.random() * numbers);
        }
        long startMergeSort = System.currentTimeMillis();
        Queue<Integer> tas2Sorted = MergeSort.mergeSort(tas2);
        long endMergeSort =  System.currentTimeMillis();
        System.out.println(numbers + ": Seconds elapsed for MergeSort: " + (endMergeSort - startMergeSort) / 1000.0 + " seconds.");

        assertEquals(tas2.size(), tas2Sorted.size());
        assertTrue(isSorted(tas2Sorted));
    }


    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
