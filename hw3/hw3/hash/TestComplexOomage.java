package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        int N = 10000;


        //Dead one: XXX0000 is too large, XXXX * 4294967296, which is zero
        //Because the range of int is 4294967296
        //more general, because '256' has same common divisor with int range '4294967296', that is '256'
        //therefore, to fix it, we see a prime number instead, like 257
        for (int i = 0; i < N; i += 1) {
            int num = StdRandom.uniform(5, 10);
            ArrayList<Integer> params = new ArrayList<>(N);
            for (int j = 0; j < num - 4; j += 1) {
                params.add(StdRandom.uniform(0, 255));

            }
            //256^4
            for (int j = num - 4; j < num; j += 1) {
                params.add(0);
            }
            deadlyList.add(new ComplexOomage(params));
            if (i <= 2) {
                System.out.println(params);
            }
        }
        System.out.println(deadlyList.get(0).hashCode());
        System.out.println(deadlyList.get(1).hashCode());
        System.out.println(deadlyList.get(2).hashCode());

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));

        /*
        //prove 256^4 = 256^5 = 256^6
        // 256^4 = 4640470641, larger than 345503345
        //int range [-2147483648, 2147483647], 4294967296
        int test3 = (int) Math.pow(256, 3);
        System.out.println(test3); //16777216
        int test4 = (int) Math.pow(256, 4);
        System.out.println(test4); //2147483647
        int test5 = (int) Math.pow(256, 5);
        System.out.println(test5); //2147483647
        int test4add1 = (int) Math.pow(256, 4) + (int) Math.pow(256, 4);
        System.out.println(test4add1); //-2
        int test4add2 = (int) (Math.pow(256, 4) + Math.pow(256, 4));
        System.out.println(test4add2); //2147483647
        int test4mul1 = (int) (Math.pow(256, 4)) * 256;
        System.out.println(test4mul1); //-256
        */




    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
