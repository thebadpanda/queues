import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] s;
    private int tail;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        s = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");

        if (size == s.length) resize(s.length * 2);

        s[tail] = item;

        size++;
        tail++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("empty collection");

        int randomIndex = StdRandom.uniformInt(0, tail);

        // System.out.println(
        //         "  randomIndex: " + randomIndex + " tail: " + tail + " length: " + s.length);

        Item item = s[randomIndex];

        int r = randomIndex;

        for (int i = randomIndex; i < tail; i++) {
            r = (r + 1) % s.length;
            s[i] = s[r];
        }

        s[--tail] = null;
        size--;

        if (size < s.length / 4) resize(s.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("empty collection");

        int randomIndex = StdRandom.uniformInt(0, tail);

        return s[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = size();
        private Item[] arr = (Item[]) new Object[size];

        private ArrayIterator() {
            for (int n = 0; n < size; n++) {
                arr[n] = s[n];
            }
            shuffle(arr);
        }

        private void shuffle(Item[] shuffled) {
            for (int n = 0; n < tail; n++) {
                int r = n + StdRandom.uniformInt(tail - n);
                Item item = shuffled[n];
                shuffled[n] = shuffled[r];
                shuffled[r] = item;
            }
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("there is no more items to iterate over");

            i--;
            return arr[i];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("iterator does not support remove option");
        }
    }

    private void resize(int capacity) {
        Item[] arr = (Item[]) new Object[capacity];

        for (int i = 0; i < tail; i++) arr[i] = s[i];

        s = arr;
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();

        String[] input = { "A", "B", "C", "D", "E", "F", "G", "H", "I" };

        for (String i : input) {
            randomizedQueue.enqueue(i);
        }

        assert randomizedQueue.size() == 9;

        System.out.println("sample:");
        for (int i = 0; i < input.length; i++) {
            System.out.println(randomizedQueue.sample());
        }

        System.out.println("forEach iteration:");
        for (String i : randomizedQueue) {
            System.out.println(i);
        }

        System.out.println("___");

        for (int i = 0; i < input.length; i++) {
            System.out.println(randomizedQueue.dequeue());
        }

        assert randomizedQueue.isEmpty();
        assert randomizedQueue.size() == 0;

        System.out.println("___2___");

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(1);
        queue.dequeue();

        RandomizedQueue<Integer> randomizedQueue1 = new RandomizedQueue<>();
        for (int i = 1; i < randomizedQueue1.size; i++) {
            randomizedQueue1.enqueue(i);
        }

        for (int i : randomizedQueue1) {
            System.out.println(i);
        }

        System.out.println("___3___");

        // for (int i = 0; i < queue2.size; i++) {
        //     System.out.println(queue2.s[i]);
        // }
    }
}
