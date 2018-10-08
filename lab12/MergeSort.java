import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>> makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> qofqs = new Queue<Queue<Item>>();
        for (Item item: items) {
            Queue<Item> holder = new Queue<Item>();
            holder.enqueue(item);
            qofqs.enqueue(holder);
        }
        return qofqs;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> result = new Queue<Item>();
        int size = Math.min(q1.size(), q2.size());
        for (int i = 0; i <= size; i++) {
            Item temp = MergeSort.getMin(q1, q2);
            result.enqueue(temp);
        }
        if (q1.isEmpty() && q2.isEmpty()) {
            return result;
        } else if (q1.isEmpty() && !q2.isEmpty()) {
            while(!q2.isEmpty()) {
                Item temp = q2.dequeue();
                result.enqueue(temp);
            }
        } else if (!q1.isEmpty() && q2.isEmpty()){
            while(!q1.isEmpty()) {
                Item temp = q1.dequeue();
                result.enqueue(temp);
            }
        } else {
            Queue<Item> temp = MergeSort.mergeSortedQueues(q1, q2);
            for (Item temp1: temp) {
                result.enqueue(temp1);
            }
        }
        return result;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> mergeSort(Queue<Item> items) {
        if (items.size() == 0 || items.size() == 1) {
            return items;
        }
        Queue<Item> result = new Queue<Item>();
        Queue<Queue<Item>> split = MergeSort.makeSingleItemQueues(items);
        Queue<Item> temp3 = new Queue<Item>();
        Queue<Item> temp4 = new Queue<Item>();
        for (int i = 0; i <= split.size(); i++) {
            Queue<Item> temp = split.dequeue();
            Queue<Item> temp2 = split.dequeue();
            Queue<Item> merged = MergeSort.mergeSortedQueues(temp, temp2);
            if (i < (split.size()/2)) {
                Item deq = merged.dequeue();
                temp3.enqueue(deq);
                Item deq2 = merged.dequeue();
                temp3.enqueue(deq2);
            } else {
                Item deq = merged.dequeue();
                temp4.enqueue(deq);
                Item deq2 = merged.dequeue();
                temp4.enqueue(deq2);
            }
        }
        while (!split.isEmpty()) {
            Queue<Item> temp = split.dequeue();
            Item hold = temp.dequeue();
            temp3.enqueue(hold);
        }
        result = MergeSort.mergeSortedQueues(temp3, temp4);
        return result;
    }

    /**  A main method for test-driven development purposes */
    public static void main (String[] args) {
        Queue<String> wrong = new Queue<String>();
        wrong.enqueue("Mike Madigan");
        wrong.enqueue("Anthony Rendon");
        wrong.enqueue("Joe Straus");
        wrong.enqueue("Carl Heastie");
        wrong.enqueue("Robin Vos");
        wrong.enqueue("oawe");
        wrong.enqueue("asd");
        Queue<String> sorted = MergeSort.mergeSort(wrong);
        System.out.println(wrong);
        System.out.println(sorted);
    }
}
