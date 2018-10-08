import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    //You must use this palindrome, and not instantiate
    //new Palindromes, or the autograder might be upset.

    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testPalindrome() {
        assertFalse(palindrome.isPalindrome("cat"));
        assertTrue(palindrome.isPalindrome("lal"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertTrue(palindrome.isPalindrome("r a c e c a r"));
        assertFalse(palindrome.isPalindrome("RaceCar"));
        assertTrue(palindrome.isPalindrome("a1##1a"));
    }
    @Test
    public void offbyonetest() {
        CharacterComparator xd = new OffByOne();
        assertFalse(palindrome.isPalindrome("cat", xd));
        assertFalse(palindrome.isPalindrome("r a c e c a r", xd));
        assertTrue(palindrome.isPalindrome("flake", xd));
    }
    @Test
    public void offbyntest() {
        CharacterComparator xd = new OffByN(5);
        assertFalse(palindrome.isPalindrome("cat", xd));
        assertFalse(palindrome.isPalindrome("ate", xd));
        assertTrue(palindrome.isPalindrome("!A&", xd));
    }
    // Uncomment this class once you've created your Palindrome class.
}
