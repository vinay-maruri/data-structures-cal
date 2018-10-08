import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void tester() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('B', 'A'));
        assertTrue(offByOne.equalChars('C', 'B'));
        assertTrue(offByOne.equalChars('B', 'C'));
        assertFalse(offByOne.equalChars('C', 'e'));
        assertFalse(offByOne.equalChars('e', 'C'));
        assertFalse(offByOne.equalChars('#', '!'));
        assertFalse(offByOne.equalChars('!', '#'));
        assertTrue(offByOne.equalChars('*', '+'));
        assertTrue(offByOne.equalChars('+', '*'));
        assertTrue(offByOne.equalChars('9', ':'));
        assertTrue(offByOne.equalChars(':', '9'));
        assertTrue(offByOne.equalChars('/', '0'));
        assertTrue(offByOne.equalChars('@', 'A'));
        assertFalse(offByOne.equalChars('a', '{'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertFalse(offByOne.equalChars('A', 'b'));
        assertFalse(offByOne.equalChars('b', 'C'));
    }

    // Your tests go here.

    //Uncomment this class once you've created your
    // CharacterComparator interface and OffByOne class.
}
