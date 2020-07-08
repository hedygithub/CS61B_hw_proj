package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;



    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    @Override
    /**
     * return size of the buffer
     */
    public int capacity() {
        return (rb.length);
    }

    @Override
    /**
     * return number of items currently in the buffer
     */
    public int fillCount() {
        return (fillCount);
    }


    @Override
    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        } else {
            rb[last] = x;
            last = (last + 1 + capacity()) % capacity();
            fillCount += 1;
        }
    }

    @Override
    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            T firstItem = rb[first];
            first = (first + 1 + capacity()) % capacity();
            fillCount -= 1;
            return (firstItem);
        }

    }

    @Override
    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        } else {
            return (rb[first]);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferItertor();
    }

    private class BufferItertor implements Iterator<T> {
        int index;
        @Override
        public boolean hasNext() {
            return (index < fillCount());
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            index += 1;
            int realIndex = (first + index - 1 + capacity()) % capacity();
            return rb[realIndex];
        }
    }


    @Override
    public boolean equals(Object o) {
        boolean isEquels = true;

        //check type
        if (this.getClass() == o.getClass()) {
            ArrayRingBuffer other = (ArrayRingBuffer) o;

            //check length
            if (!(this.fillCount() == other.fillCount())) {
                isEquels = false;
            } else {
                int i = 0;
                Iterator thisIterator = iterator();
                for (Object b : other) {
                    Object a = thisIterator.next();
                    if (!(a == b)) {
                        isEquels = false;
                        break;
                    }
                }
            }

        } else {
            isEquels = false;
        }

        return (isEquels);
    }
}
