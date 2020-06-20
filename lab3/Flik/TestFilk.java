import static org.junit.Assert.*;

import org.junit.Test;

public class TestFilk {
    /** Test Filk library */

    /** Test isSameNumber() method */

    @Test
    public void testIsSameNumber(){
        int a = 128;
        int b = 128;
        assertTrue(String.format("%d not same as %d ?? (using ==)", a, b), (a == b));
        assertTrue(String.format("%d not same as %d ?? (using library)" , a, b), Flik.isSameNumber(a,b));

    }

}
