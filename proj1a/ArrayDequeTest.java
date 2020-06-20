/*
    The test class for ArrayDeque
    Author: Yuchen Liu
 */

import org.junit.Test;


import java.lang.reflect.Array;

import static org.junit.Assert.*;


public class ArrayDequeTest {

    // helper function to determine if two deques have exactly the same elements
    // note: they can have different capacity
    private boolean equal(ArrayDeque ad1, ArrayDeque ad2) {
        // a few edge cases
        if (ad1 == null && ad2 == null) {
            return true;
        }
        else if (ad1 == null ^ ad2 == null) { // XOR operator
            return false;
        }
        if (ad1.size() != ad2.size()) {
            return false;
        }

        for (int i = 0; i < ad1.size(); i++) {
            if (ad1.get(i) != ad2.get(i)) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void testDefaultConstructor () {
        ArrayDeque<String> ad1 = new ArrayDeque<>();
        assertEquals("isEmpty() test failed", true, ad1.isEmpty());
        assertEquals("size() test failed", 0, ad1.size());
        assertEquals("capacity() test failed", ArrayDeque.INIT_CAPACITY, ad1.capacity());

        System.out.println("Test PASSED: default constructor. \n");
    }

    @Test
    public void testConstructor () {
        Integer[] items = new Integer[]{56, 23, 18, 9, 0, 2, 55, 44, -5};
        ArrayDeque<Integer> ad1 = new ArrayDeque<>(items);
        assertEquals("isEmpty() test failed", false, ad1.isEmpty());
        assertEquals("size() test failed", items.length, ad1.size());
        assertEquals("capacity() test failed", Math.max(ArrayDeque.INIT_CAPACITY, items.length), ad1.capacity());

        ArrayDeque<Integer> ad2 = new ArrayDeque<>(ad1);
        assertTrue("After deep copy, the elements of two deques are not equal! ", equal(ad1, ad2));
        assertNotEquals("After deep copy, two deques should not point to the same reference! ", ad1, ad2);

        ad1.printDeque();
        ad2.printDeque();

        System.out.println("Test PASSED: non-default constructor. \n");
    }

    @Test
    public void testCapacity () {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        int max_iter = Math.max(ArrayDeque.MIN_SIZE_APPLICABLE, ArrayDeque.INIT_CAPACITY); // by design
        int val = 0;

        // test capacity "overflow"
        for (; val < max_iter; val++) {
            ad1.addFirst(-val);
            ad1.addLast(val);
            if (ad1.capacity() > ArrayDeque.INIT_CAPACITY) {
                assertTrue("After adding the elements" + val + ", the capacity of the array deque should increase", ad1.capacity() > ArrayDeque.INIT_CAPACITY);
            }
            if (ad1.capacity() >= ArrayDeque.MIN_SIZE_APPLICABLE) {
                assertTrue("After adding the elements" + val + ", the usage ratio should be at least " + ArrayDeque.MIN_USAGE, ad1.usage() >= ArrayDeque.MIN_USAGE);
            }
        }
        assertEquals("size() test failed", 2*max_iter, ad1.size());

        // test too low usage ratio
        // int min_capacity = 1 + (int)((double)(ArrayDeque.MIN_SIZE_APPLICABLE + 1) / ArrayDeque.MIN_USAGE);
        while (ad1.capacity() < ArrayDeque.MIN_SIZE_APPLICABLE) {
            // we need to increase the capacity in this case
            ad1.addLast(val);
            val++;
        }
        // now we're ready to test
        while (ad1.capacity() > ArrayDeque.MIN_SIZE_APPLICABLE) { // this is true by design in the first loop
            int elem = ad1.removeLast();
            assertTrue("After removing the element" + elem + ", the usage ratio should be at least " + ArrayDeque.MIN_USAGE, ad1.usage() >= ArrayDeque.MIN_USAGE);
        }

        System.out.println("Test PASSED: capacity and usage ratio management. \n");
    }

    @Test
    public void testAddRemove () {
        String[] items = new String[] {"a", "abcd"};
        ArrayDeque<String> ad1 = new ArrayDeque<>(items);

        String str1 = "x";
        String str2 = "1E39ok_i";

        ad1.addFirst(str1);
        assertEquals("addFirst() test failed", str1, ad1.get(0));
        String elem1 = ad1.removeLast();
        assertEquals("removeLast() test failed", items[items.length-1], elem1);
        ad1.addLast(str2);
        assertEquals("addLast() test failed", str2, ad1.get(ad1.size()-1));
        String elem2 = ad1.removeFirst();
        assertEquals("removeLast() test failed", str1, elem2);

        String[] items_exp = new String[] {"a", str2};
        ArrayDeque<String> ad1_exp = new ArrayDeque<>(items_exp);
        assertTrue("After several operations, the elements of the deque are not expected", equal(ad1, ad1_exp));

        ad1.printDeque();

        // clear the deque
        ad1.removeFirst();
        ad1.removeLast();
        assertEquals("isEmpty() test failed", true, ad1.isEmpty());
        String elem3 = ad1.removeFirst();
        String elem4 = ad1.removeLast();
        assertEquals("Null should be returned by removeFirst()", null, elem3);
        assertEquals("Null should be returned by removeFirst()", null, elem4);

        System.out.println("Test PASSED: add and remove operations. \n");
    }

    @Test
    public void testGetElements () {
        Integer[] items = new Integer[]{56, 23, 18, 9};
        ArrayDeque<Integer> ad1 = new ArrayDeque<>(items);
        ArrayDeque<Integer> ad1_exp = new ArrayDeque<>(items);

        for (int i = 0; i < items.length; i++) {
            assertEquals("get() test falied at position " + i, items[i], ad1.get(i));
        }

        assertTrue("After get() the original deque should not be modified!", equal(ad1, ad1_exp));

        System.out.println("Test PASSED: get operation. \n");
    }

}
