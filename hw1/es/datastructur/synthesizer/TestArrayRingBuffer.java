package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer a = new ArrayRingBuffer(4);
        a.enqueue(0);
        a.enqueue(1);
        a.enqueue(2);
        assertEquals(0, a.dequeue());
        assertEquals(1, a.peek());
        for (Object i : a){
            System.out.println(i);
        }

        ArrayRingBuffer b = new ArrayRingBuffer(4);
        b.enqueue(5);
        b.enqueue(1);
        b.enqueue(2);
        b.dequeue();
        assertTrue(a.equals(b));

    }
}
