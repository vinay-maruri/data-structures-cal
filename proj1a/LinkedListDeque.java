/**
 * Implementing double ended queue using Linked Lists
 *
 * @author Vinay Maruri <vmaruri1@berkeley.edu>
 * @version 3.2
 */
public class LinkedListDeque<T> implements {

    private TNode<T> sentinelBack;
    private TNode<T> sentinelFront;
    private TNode<T> temp;
    private int size;
    public LinkedListDeque() {
        sentinelFront = new TNode(null, null, null);
        sentinelBack = new TNode(null, null, null);
        sentinelFront.next = sentinelBack;
        sentinelBack.prev = sentinelFront;
    }

    /**
     * adds item to the front of the deque
     */
    @Override
    public void addFirst(T item) {
        temp = sentinelFront.next;
        sentinelFront.next = new TNode(item, temp, sentinelFront);
        size += 1;
        if (size != 1) {
            sentinelFront.next.next.prev = sentinelFront.next;
        } else {
            sentinelBack.prev = sentinelFront.next;
        }
    }

    /**
     * adds item to the back of the deque
     */
    @Override
    public void addLast(T item) {
        temp = sentinelBack.prev;
        sentinelBack.prev = new TNode(item, sentinelBack, temp);
        temp.next = sentinelBack.prev;
        size += 1;
    }

    /**
     * checks whether the deque is empty
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * returns the size of the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * prints items from front to back in the deque
     */
    @Override
    public void printDeque() {
        while (sentinelFront.next != null) {
            if (sentinelFront.item == null) {
                sentinelFront = sentinelFront.next;
            } else {
                System.out.println(sentinelFront.item);
                sentinelFront = sentinelFront.next;
            }
        }
    }

    /**
     * removes and returns item at back of the deque
     */
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        temp = sentinelBack.prev;
        T returnitem = sentinelBack.prev.item;
        sentinelBack.prev.prev.next = sentinelBack;
        sentinelBack.prev = sentinelBack.prev.prev;
        temp.prev = null;
        temp.next = null;
        temp = null;
        size -= 1;
        return returnitem;
    }

    /**
     * removes and returns item at front of deque
     */
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        temp = sentinelFront.next;
        T returnitem = sentinelFront.next.item;
        sentinelFront.next = sentinelFront.next.next;
        sentinelFront.next.prev = sentinelFront;
        temp.next = null;
        temp.prev = null;
        temp = null;
        size -= 1;
        return returnitem;
    }

    /**
     * retrieves item at given deque position
     */
    @Override
    public T get(int index) {
        index += 1;
        if (index == 0) {
            return sentinelFront.item;
        }
        temp = sentinelFront;
        while (index > 0) {
            //SentinelFront.next.prev = null;
            //SentinelFront = SentinelFront.next;
            temp = temp.next;
            index -= 1;
        }
        return temp.item;
    }

    /**
     * retrieves item at given deque position recursively
     */
    private T recursivehelper(int index2, TNode<T> holder) {
        if (index2 == 0) {
            return holder.item;
        }
        index2 -= 1;
        holder = holder.next;
        return recursivehelper(index2, holder);
    }

    public T getRecursive(int index) {
        if (index == 0) {
            return sentinelFront.next.item;
        }
        temp = sentinelFront.next;
        return recursivehelper(index, temp);
    }

    private class TNode<T> {
        private T item;
        private TNode<T> next;
        private TNode<T> prev;

        private TNode(T i, TNode<T> n, TNode<T> p) {
            item = i;
            next = n;
            prev = p;
        }
    }
}
