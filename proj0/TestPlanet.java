public class TestPlanet {
/** creating 2 new planets, samh and samy 
* expected pairwise force is 45G
* @Param String[] args
* @return distance
* @source: alvinalexander.com/java/how-to-round-float-double-to-int-integer-in-java
*/
public static void main(String[] args) {
    Planet samh = new Planet(0.0, 3.0, 5.0, 7.0, 9.0, "");
    Planet samy = new Planet(1.0, 4.0, 6.0, 8.0, 10.0, "!");
    double force = samh.calcForceExertedBy(samy)/6.67e-11;
    System.out.println("Expected value was 45. Actual value was " + Math.round(force)); 
}

}