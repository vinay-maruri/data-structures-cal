public class ArrayDequeTest {
    public static void simpletest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(3);
        System.out.println("passed simple test");
    }

    public static void resizetest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(3);
        input.addFirst(4);
        input.addFirst(5);
        input.addLast(6);
        input.addFirst(7);
        input.addFirst(9);
        input.addLast(10);
        input.addFirst(11);
        input.addLast(12);
        for (int i = 0; i < 2000; i++) {
            input.addFirst(i);
        }
        System.out.println("passed resize test");
    }

    public static void addtest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.addLast(1);
        input.addLast(2);
        input.addLast(3);
        input.addLast(4);
        input.addLast(5);
        System.out.println("passed add test");
    }

    public static void gettest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        int n = 10;
        for (int i = 0; i < n; i++) {
            input.addLast(i);
        }
        int val = input.get(0);
        System.out.println("passed get test");
    }

    public static void removetest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        /**
         for(int i = 0; i < 3000; i++){
         if (i % 2 == 0){
         input.addFirst(i);
         }
         else {
         input.addLast(i);
         }
         }
         int x = 2500;
         while (x > 0){
         input.removeLast();
         x -= 1;
         } */
        input.addLast(1);
        input.addFirst(3);
        input.addFirst(5);
        int x = 2500;
        while (x > 0) {
            input.removeLast();
            x -= 1;
        }
        System.out.println("passed remove test");
    }

    public static void randomtest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.size();
        input.addFirst(2);
        input.addFirst(3);
        input.addLast(4);
        input.addLast(5);
        input.addLast(6);
        input.size();
        input.addFirst(8);
        input.addLast(9);
        System.out.println("passed random test");
    }

    public static boolean tester(int expected, int actual) {
        return expected == actual;
    }

    public static void overloadtest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addFirst(0);
        input.addFirst(1);
        input.addFirst(3);
        input.addFirst(4);
        input.addFirst(5);
        input.addFirst(6);
        input.addFirst(7);
        input.addFirst(8);
        input.addFirst(9);
        input.removeLast();
        /** input.addLast(0);
         input.addLast(1);
         input.addLast(2);
         input.addLast(3);
         input.addLast(4);
         input.addLast(5);
         input.addLast(6);
         input.addLast(7);
         input.addLast(8);
         input.removeFirst(); */
        System.out.println("passed overload test");
    }

    public static void overloadtwotest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addFirst(0);
        input.removeFirst();
        input.addFirst(2);
        input.removeLast();
        input.addLast(4);
        input.addLast(5);
        int val = input.get(1);
        input.addLast(7);
        input.removeLast();
        input.addLast(9);
        input.addLast(10);
        input.addLast(11);
        val = input.get(3);
        input.addLast(13);
        input.addLast(14);
        input.addFirst(15);
        input.removeFirst();
        input.addFirst(17);
        val = input.get(4);
        input.addLast(19);
        input.removeFirst();
    }

    public static void fillremovetest() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        for (int i = 0; i < 99; i++) {
            input.addFirst(i);
        }
        for (int x = 0; x < 99; x++) {
            int y = input.removeFirst();
            //input.removeFirst();
        }
    }

    public static void test1() {
        ArrayDeque<Integer> input = new ArrayDeque<>();
        input.addLast(0);
        input.addLast(1);
        input.addLast(2);
        input.addLast(3);
        input.isEmpty();
        input.addLast(5);
        input.addLast(6);
        input.addLast(7);
        input.addLast(8);
        int y = input.removeFirst();
        if (y == 0) {
            System.out.println("true");
        }
    }

    public static void lasttest() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(0);
        arrayDeque.removeLast();
        arrayDeque.addLast(3);
        arrayDeque.addLast(5);
        arrayDeque.addLast(6);
        arrayDeque.removeLast();
        arrayDeque.addLast(8);
        arrayDeque.addLast(9);
        arrayDeque.addLast(11);
        arrayDeque.addFirst(12);
        arrayDeque.addLast(13);
        arrayDeque.addFirst(14);
        arrayDeque.addFirst(16);
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.removeLast();
        arrayDeque.removeLast();
        arrayDeque.removeLast();
        int x = arrayDeque.get(0);
        arrayDeque.isEmpty();
    }

    public static void main(String[] args) {
        simpletest();
        resizetest();
        addtest();
        gettest();
        removetest();
        randomtest();
        overloadtest();
        overloadtwotest();
        fillremovetest();
        test1();
        lasttest();

        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addLast(0);
        arrayDeque.addLast(1);
        arrayDeque.addLast(2);
        arrayDeque.addLast(3);
        arrayDeque.removeLast();
        arrayDeque.addLast(5);
        arrayDeque.addFirst(6);
        arrayDeque.get(2);
        arrayDeque.addLast(8);
        arrayDeque.addFirst(9);
        arrayDeque.removeLast();
        arrayDeque.addFirst(11);
        arrayDeque.addFirst(12);
        arrayDeque.removeFirst();
        arrayDeque.removeLast();
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.get(3);
        arrayDeque.removeFirst();
        arrayDeque.removeFirst();
        arrayDeque.addLast(20);
        arrayDeque.addLast(21);
        arrayDeque.get(0);
        arrayDeque.get(2);
    }
}
