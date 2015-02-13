import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private Deque<Integer> emptyIndex;
    private int size;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        s = ((Item[]) new Object[1]);
        emptyIndex = new Deque<Integer>();
        emptyIndex.addFirst(0);
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
        
        if (emptyIndex.isEmpty()) {
            resize(2 * s.length);
            s[size] = item;
        } else {
            s[emptyIndex.removeFirst()] = item;
        }
        
        size++;
    }
    
    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        
        int randomIndex = (int) Math.random() * s.length;
        while (s[randomIndex] == null) randomIndex = (int) Math.random() * s.length;
        Item item = s[randomIndex];
        s[randomIndex] = null;
        emptyIndex.addFirst(randomIndex);
        size--;
        if (size > 0 && size == s.length/4) resize(s.length/2);
        
        return item;
    }
    
    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        int randomIndex = (int) Math.random() * s.length;
        while (s[randomIndex] == null) randomIndex = (int) Math.random() * s.length;
        
        return s[randomIndex];
    }
    
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        emptyIndex = new Deque<Integer>();
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null) copy[i] = s[i];
            else if (i < capacity) emptyIndex.addFirst(i);
        }
        s = copy;
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueue();
    }
    
    private class RandomizedQueue implements Iterator<Item> {
        private int[] iteratorOrder;
        private int i = 0;
        
        public RandomizedQueue() {
            iteratorOrder = new int[s.length - emptyIndex.size()];
            for (int i = 0; i < s.length; i++) {
                if (s[i] != null) iteratorOrder[i] = i;
            }
            
            shuffle(iteratorOrder);
        }
        
        private void shuffle(int[] a) {
            int N = a.length;
            for (int i = 0; i < N; i++) {
                int r = (int) Math.random() * (i + 1);
                exch(a, i, r);
            }
        }
        
        private void exch(int[] a, int i, int r) {
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
            
        public boolean hasNext() { return i != iteratorOrder.length; }
        
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in iteration!");
            }
            
            Item item = s[iteratorOrder[i++]];
            return item;
        }
    }
    
    public static void main(String[] args)  {
        
    }
}