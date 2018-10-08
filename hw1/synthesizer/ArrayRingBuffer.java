
package synthesizer;

import java.util.Iterator;


public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {

        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {

        if (this.fillCount == this.capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        this.fillCount += 1;
        if (this.last == capacity - 1) {
            this.last = 0;
        } else {
            this.last += 1;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {

        if (this.fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        this.fillCount -= 1;
        if (this.first == capacity - 1) {
            this.first = 0;
        } else {
            this.first += 1;
        }
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.fillCount == 0) {
            throw new RuntimeException();
        }
        return rb[first];
    }


    public Iterator<T> iterator() {
        return new KeyIterator();
    }

    private class KeyIterator implements Iterator<T> {
        private int ptr;

        public KeyIterator() {
            ptr = first;
        }

        public boolean hasNext() {
            return (ptr == last);
        }

        public T next() {
            T temp = rb[ptr];
            if (ptr > capacity - 1) {
                ptr = 0;
            } else {
                ptr += 1;
            }
            return temp;
        }
    }

}
