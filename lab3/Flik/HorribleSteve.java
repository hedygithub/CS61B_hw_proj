public class HorribleSteve {
    public static void main(String [] args) throws Exception {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                throw new Exception(
                        String.format("i:%d not same as j:%d ??", i, j));
            }
        }
        System.out.println("i is " + i);
    }
}

/** ok the problem is that the library takes "Integer" as inputs, but the user provides them with int type
    In this case, Java compiler will do something like Integer ii = Integer.valueOf(i) where i is an int
    Note that the "value" of ii is only equal to the "value" of i when -128 <= i <= 127 according to
    https://docs.oracle.com/javase/7/docs/api/java/lang/Integer.html .
    If i is outside the boundary, ii can be anything, so the library starts malfunctioning from 128
    The fix can be either:
    1) Declare the function to take int as inputs, rather than Integer; or
    2) change == to be .equal()
 */