public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int count = 0;
        while (x < 10) {
            count = count + x;
            System.out.print(count + " ");
            x = x + 1;
        }
    }
}
