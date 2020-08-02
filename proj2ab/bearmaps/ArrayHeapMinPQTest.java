package bearmaps;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    //add, getSmallest, contains, removeSmallest
    public void testBasic() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        heap.add('a',5);
        heap.add('b',4);
        heap.add('c',3);
        heap.add('d',2);
        heap.add('e',1);
        assertEquals('e', heap.getSmallest());
        assertTrue(heap.contains('e'));

        assertEquals('e', heap.removeSmallest());
        assertFalse(heap.contains('e'));
        assertEquals('d', heap.getSmallest());

        heap.changePriority('a',0.5);
        assertEquals('a', heap.getSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        NaiveMinPQ pQ = new NaiveMinPQ();
        int numbers = 100; //you can change it
        int add1 = numbers + 1;

        long startHeap = System.currentTimeMillis();
        heap.add("hi" + add1, add1);
        for (int i = numbers; i > 0; i--) {
            heap.add("hi" + i, i);
            //make sure put is working via contains
            assertTrue(heap.contains("hi" + i));
            assertEquals("hi" + i, heap.getSmallest());

            add1 = i + 1;
            heap.changePriority("hi" + add1, i - 0.5);
            assertEquals("hi" + add1, heap.getSmallest());

            heap.changePriority("hi" + numbers, 0);
            assertEquals("hi" + numbers, heap.getSmallest());

            heap.changePriority("hi" + add1, add1);
            heap.changePriority("hi" + numbers, numbers);
            assertEquals("hi" + i, heap.getSmallest());
        }
    }
    @Test
    public void testRunTime() {
        ArrayHeapMinPQ heap = new ArrayHeapMinPQ();
        NaiveMinPQ pQ = new NaiveMinPQ();
        int numbers = 100; //you can change it

        long startHeap = System.currentTimeMillis();
        for (int i = numbers; i > 0; i--) {
            heap.add("hi" + i, i);
            //make sure put is working via contains
            assertTrue(heap.contains("hi" + i));
            assertEquals("hi" + i, heap.getSmallest());
        }
        long endHeap = System.currentTimeMillis();
        System.out.println("Total time elapsed for ExtrinsicMinPQ: " + (endHeap - startHeap)/1000.0 +  " seconds.");

        long startPQ = System.currentTimeMillis();
        for (int i = numbers; i > 0; i--)  {
            pQ.add("hi" + i, i);
            //make sure put is working via contains
            assertTrue(pQ.contains("hi" + i));
            assertEquals("hi" + i, pQ.getSmallest());
        }
        long endPQ = System.currentTimeMillis();
        System.out.println("Total time elapsed for NaiveMinPQ: " + (endPQ - startPQ)/1000.0 +  " seconds.");
    }
}
