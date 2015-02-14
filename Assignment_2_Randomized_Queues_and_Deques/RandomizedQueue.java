import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private Stack<Integer> emptyIndex;
    private int size;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        s = ((Item[]) new Object[1]);
        emptyIndex = new Stack<Integer>();
        emptyIndex.push(0);
    }
    
    private class Stack<Item> {
        private Node first = null;
        private int stackSize;
        
        private class Node {
            Item item;
            Node next;
        }
        
        public boolean isEmpty()
        { return stackSize == 0; }
        
        public int size() {
            return stackSize;
        }
        
        public void push(Item item) {
            Node oldfirst = first;
            first = new Node();
            first.item = item;
            first.next = oldfirst;
            stackSize++;
        }
        
        public Item pop() {
            Item item = first.item;
            first = first.next;
            stackSize--;
            return item;
        }
    }
    
    /**
     * is the queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * return the number of items on the queue
     */
    public int size() {
        return size;
    }
    
    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");

        if (emptyIndex.isEmpty()) resize(2 * s.length);
        s[emptyIndex.pop()] = item;
        
        size++;
    }
    
    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        
        int randomIndex = (int) (Math.random() * s.length);
        while (s[randomIndex] == null) {
            randomIndex = (int) (Math.random() * s.length);
        }
        Item item = s[randomIndex];
        s[randomIndex] = null;
        emptyIndex.push(randomIndex);
        size--;
        if (size > 0 && size == s.length / 4) resize(s.length / 2);
        
        return item;
    }
    
    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        
        int randomIndex = (int) (Math.random() * s.length);
        while (s[randomIndex] == null)
            randomIndex = (int) (Math.random() * s.length);
        
        return s[randomIndex];
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int toBeOccupiedIndex = 0;
        emptyIndex = new Stack<Integer>();
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) copy[toBeOccupiedIndex++] = s[i];
        }
        
        for (int i = toBeOccupiedIndex; i < capacity; i++) {
            emptyIndex.push(i);
        }
        
        s = copy;
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] iteratorOrder;
        private int index = 0;
        
        public RandomizedQueueIterator() {
            iteratorOrder = new int[s.length - emptyIndex.size()];
            
            int toBeOccupiedIndex = 0;
            for (int i = 0; i < s.length; i++) {
                if (s[i] != null) iteratorOrder[toBeOccupiedIndex++] = i;
            }
            
            shuffle(iteratorOrder);
        }
        
        private void shuffle(int[] a) {
            int N = a.length;
            for (int i = 0; i < N; i++) {
                int r = (int) (Math.random() * (i + 1));
                exch(a, i, r);
            }
        }
        
        private void exch(int[] a, int i, int r) {
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
            
        public boolean hasNext() { return index != iteratorOrder.length; }
        
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in iteration!");
            }
            
            Item item = s[iteratorOrder[index++]];
            return item;
        }
    }
    
    public static void main(String[] args)  {
        
    }
}