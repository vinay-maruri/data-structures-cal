import static org.junit.Assert.*;

import org.junit.Test;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
    }
    /** Testing the isSameNumber method */
    @Test
    public void FlikTest(){
        boolean cond = isSameNumber(128, 128);
        assertTrue(cond);
    }
    /** The error is in the Flik class with the isSameNumber method.
     * When isSameNumber(128, 128) is performed, it returns false instead
     * of true. The resolution to this error is to change '==' to equals
     * ensuring checking of values for equality, not checking whether the
     * two objects point to the same memory address.
     */
}
