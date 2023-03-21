import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Item[] s;
    private int head;
    private int tail;
    private int size;

    // construct an empty deque
    public Deque() {
        s = (Item[]) new Object[4];
        head = 0;
        tail = 0;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException("you cannot add null as element");

        if (size == s.length) resize(s.length * 2);

        if (isEmpty()) {
            head = 0;
            tail = 0;
        }
        else if (head == 0) head = s.length - 1;
        else head--;

        s[head] = item;
        size++;

        // System.out.println(
        //         "head: " + head + " tail: " + tail + " size: " + size + " length: " + s.length);
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) throw new IllegalArgumentException("you cannot add null as element");

        if (size == s.length) resize(s.length * 2);

        if (isEmpty()) {
            head = 0;
            tail = 0;
        }
        else if (++tail >= s.length) tail = 0;

        s[tail] = item;
        size++;

        // System.out.println(
        //         "head: " + head + " tail: " + tail + " size: " + size + " length: " + s.length);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("colletion is empty");

        Item item = s[head];
        s[head] = null;

        if (++head >= s.length) head = 0;

        size--;

        // System.out.println(
        //         "head: " + head + " tail: " + tail + " size: " + size + " length: " + s.length);

        if (size < s.length / 4) resize(s.length / 2);

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("colletion is empty");

        Item item = s[tail];
        s[tail] = null;

        if (isEmpty()) {
            head = 0;
            tail = 0;
        }
        else if (tail <= 0) tail = s.length - 1;
        else tail--;

        size--;

        // System.out.println(
        //         "head: " + head + " tail: " + tail + " size: " + size + " length: " + s.length);

        if (size < s.length / 4) resize(s.length / 2);

        return item;
    }

    private void resize(int capacity) {
        System.out.println("resizing to " + capacity);

        Item[] copy = (Item[]) new Object[capacity];
        int h = head;

        for (int i = 0; i < size; i++) {
            copy[i] = s[h];
            h = (h + 1) % s.length;
        }

        // for (int n = 0; n < s.length; n++) {
        //     System.out.println(copy[n] + " " + s[n]);
        // }
        s = copy;

        head = 0;
        tail = size - 1;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = size();
        private int current = head;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("no next element available");

            Item item = s[current];
            current = (current + 1) % s.length;
            i--;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> item = new Deque<>();

        assert item.size() == 0;

        String[] input = { "1", "2", "3", "4", "5" };
        for (String word : input) {
            item.addFirst(word);
        }

        assert item.size() == 5;

        for (String i : item) {
            System.out.println(i);
        }

        System.out.println("__");

        String w1 = item.removeFirst();
        assert w1.equals("5");

        String w2 = item.removeLast();
        assert w2.equals("1");

        assert item.size() == 3;

        for (String i : item) {
            System.out.println(i);
        }

        System.out.println("__");

        item.addFirst("6");
        item.addFirst("7");
        item.addFirst("8");
        item.addFirst("9");
        item.addFirst("10");
        item.addFirst("11");
        item.addFirst("12");
        item.addFirst("13");

        item.addLast("14");
        item.addLast("15");

        assert item.size() == 13;

        System.out.println("\niterator:");
        for (String i : item) {
            System.out.println(i);
        }

        Deque<Integer> deque = new Deque<>();
        assert deque.isEmpty();
        deque.addFirst(4);
        deque.addFirst(5);
        int last = deque.removeLast();
        assert last == 4;
        assert !deque.isEmpty();
        deque.removeLast();

        Deque<Integer> deque2 = new Deque<>();
        assert deque2.isEmpty();
        deque2.addLast(3);
        deque2.addLast(4);
        deque2.addLast(5);
        deque2.addLast(6);

        Deque<Integer> deque3 = new Deque<>();
        deque3.addFirst(1);
        deque3.removeLast();
        deque3.addFirst(3);
        deque3.removeLast();
        deque3.addFirst(5);
        deque3.addFirst(6);
        deque3.addFirst(7);
        deque3.removeLast();
        int x = deque3.removeLast();
        assert x == 6;

        Deque<Integer> deque4 = new Deque<>();
        assert deque4.isEmpty();
        deque4.addLast(2);
        deque4.removeLast();
        assert deque4.isEmpty();
        deque4.addLast(7);
        deque4.addLast(8);
        deque4.removeLast();
        int k = deque4.removeLast();
        assert k == 7;

        Deque<Integer> deque5 = new Deque<>();
        deque5.addLast(1);
        deque5.addFirst(2);
        deque5.addFirst(3);
        deque5.removeLast();
        deque5.removeFirst();
        deque5.addFirst(6);
        deque5.addLast(7);
    }
}
