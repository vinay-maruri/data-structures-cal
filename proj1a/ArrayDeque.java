/**
 * Implementing double ended queue using arrays
 *
 * @author Vinay Maruri <vmaruri1@berkeley.edu>
 * @version 4.2
 */

public class ArrayDeque<T>{

    private int first;
    private int last;
    private int size;
    private T[] array;

    /**
     * method to construct an empty array deque
     */

    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        first = 0;
        last = 0;
    }

    /**
     * method to add element to front of deque
     */
    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            upsize(array.length * 2);
        }
        if (size == 0) {
            array[0] = item;
            first = array.length - 1;
            last = 1;
            size += 1;
        } else {
            if (array[first] != null) {
                first = array.length - 1;
            }
            if (first < 0) {
                first = 0;
            }
            array[first] = item;
            first -= 1;
            size += 1;
        }
        if (first < 0) {
            first = array.length - 1;
        }
    }

    /**
     * method to add element to back of deque
     */
    @Override
    public void addLast(T item) {
        if (size == array.length) {
            upsize(array.length * 2);
        }
        if (size == 0) {
            array[0] = item;
            first = array.length - 1;
            last = 1;
            size += 1;
        } else {
            array[last] = item;
            last += 1;
            size += 1;
        }
        if (last >= array.length) {
            last = 0;
        }
    }

    /**
     * method to check whether deque is empty
     */
    @Override
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * method to resize array if array is full
     */

    private void upsize(int newsize) {
        T[] temp = (T[]) new Object[newsize];
        int lastcopy = first;
        int firstcopy = last;
        for (int i = 0; i < size; i++) {
            temp[i] = array[firstcopy];
            firstcopy += 1;
            if (firstcopy >= array.length) {
                firstcopy = 0;
            }
        }
        array = temp;
        last = size;
        first = array.length - 1;
    }

    /**
     * method to resize array if array is below 25%
     */

    private void downsize(int newsize) {
        T[] temp = (T[]) new Object[newsize];
        int lastcopy = last;
        int firstcopy = first + 1;
        for (int i = 0; i < size; i++) {
            if (firstcopy >= array.length) {
                firstcopy = 0;
            }
            temp[i] = array[firstcopy];
            firstcopy += 1;
        }
        array = temp;
        last = size;
        first = array.length - 1;
    }

    /**
     * method to return size of array
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * method to print elements of array front to
     * back
     */
    @Override
    public void printDeque() {
        int n = 0;
        while (n <= array.length - 1) {
            System.out.println(array[n]);
            n += 1;
        }
    }

    /**
     * method to remove first element from deque
     */
    @Override
    public T removeFirst() {
        if (array.length >= 16 && size <= array.length * 0.25) {
            downsize(array.length / 2);
        }
        if (size == array.length) {
            upsize(array.length * 2);
        }
        T elem;
        if (size == 0) {
            return null;
        }
        if (array[first] == null) {
            first += 1;
        }
        if (first >= array.length) {
            first = 0;
        }
        elem = array[first];
        array[first] = null;
        size -= 1;
        if (first >= array.length) {
            first = 0;
        }
        return elem;
    }

    /**
     * method to remove last element from deque
     */
    @Override
    public T removeLast() {
        if (array.length >= 16 && size <= array.length * 0.25) {
            downsize(array.length / 2);
        }
        if (size == array.length) {
            upsize(array.length * 2);
        }
        T elem;
        if (size == 0) {
            return null;
        }
        if (array[last] == null) {
            last -= 1;
        }
        //last -= 1;
        if (last < 0) {
            last = array.length - 1;
        }
        elem = array[last];
        array[last] = null;
        size -= 1;
        return elem;
    }

    /**
     * method to return element from deque at given index
     */
    @Override
    public T get(int index) {
        if (index > size || index < 0) {
            return null;
        }
        if (first + 1 + index >= array.length) {
            return array[first + 1 + index - array.length];
        }
        return array[first + 1 + index];

    }
}
