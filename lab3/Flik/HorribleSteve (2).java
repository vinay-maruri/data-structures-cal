import static org.junit.Assert.*;

import org.junit.Test;

public class HorribleSteve {
    public static void main(String [] args) {
        int i = 0;
        for (int j = 0; i < 500; ++i, ++j) {
            if (!Flik.isSameNumber(i, j)) {
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
    }
    /**Testing horrible steve's method */
    @Test
    public void HorribleSteveTest(){
        int i = 125;
        for (int j = 125; i < 500; i++, j++){
            boolean test = Flik.isSameNumber(i, j);
            if (test == false){
                break;
            }
        }
        assertEquals(500, i);
    }
}
