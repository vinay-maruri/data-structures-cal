<<<<<<< HEAD
import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {    
    @Test
    public void testSmall() {
        Dog d = new Dog(3);
        assertEquals("yip", d.noise());
    }

    @Test
    public void testLarge() {
        Dog d = new Dog(20);
        assertEquals("bark", d.noise());
    }
}
=======
import static org.junit.Assert.*;
import org.junit.Test;

public class DogTest {    
    @Test
    public void testSmall() {
        Dog d = new Dog(3);
        assertEquals("yip", d.noise());
    }

    @Test
    public void testLarge() {
        Dog d = new Dog(20);
        assertEquals("bark", d.noise());
    }
}
>>>>>>> b1691dcfb141d8cc17dff77ef5fe8370982eaac2
