import org.junit.Test;
import static org.junit.Assert.*;

public class TestUnionFind {
    @Test
    public void testUnionFind() {
        UnionFind set1 = new UnionFind(10);
        set1.union(0,1);
        set1.union(1,2);
        set1.union(3,4);
        set1.union(2,4);
//        set1.find(12);
        assertEquals(1, set1.find(3));
        assertEquals(5, set1.sizeOf(0));
        assertFalse(set1.connected(0,5));
        assertTrue(set1.connected(0,3));
    }
}
