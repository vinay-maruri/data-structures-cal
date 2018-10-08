package hw3.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;


public class TestSimpleOomage {

    /**
     * Calls tests for SimpleOomage.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {

        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        List<Oomage> oomage = new ArrayList<>();
        List<Oomage> oomage2 = new ArrayList<>();
        int N = 51;
        for (int i = 0; i <= N; i++) {
            oomage.add(new SimpleOomage(i * 5, i * 5, i * 5));
        }
        for (int k = 0; k <= N; k += 2) {
            oomage2.add(new SimpleOomage(k * 5, k * 5, k * 5));
        }
        assertEquals(oomage.get(0), oomage2.get(0));
        assertNotEquals(oomage.get(40), oomage2.get(22));
        assertNotEquals(oomage.get(27), oomage.get(22));
        SimpleOomage x = new SimpleOomage(0, 0, 155);
        SimpleOomage y = new SimpleOomage(0, 5, 0);
        assertNotEquals(x.hashCode(), y.hashCode());
    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    @Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<>();
        hashSet.add(ooA);
        hashSet.add(ooA2);
        assertTrue(hashSet.contains(ooA));
    }


    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(SimpleOomage.randomSimpleOomage());
        }
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }
}
