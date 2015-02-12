import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;
    
    /**
     * construct an empty deque
     */
    public Deque() { }
    
    private class Node {
        Item item;
        Node next;
        Node prior;
    }
    
    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }
        
    /**
     * return the number of items on the deque
     */
    public int size() {
        return size;
    }
        
    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");
        
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        
        if (isEmpty()) last = first;
        else {
            first.next = oldFirst;
            oldFirst.prior = first;
        }
        
        size++;
    }
        
    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        
        if (isEmpty()) first = last;
        else {
            oldLast.next = last;
            last.prior = oldLast;
        }
        
        size++;
    }
        
    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty!");
        
        Item item = first.item;
        size--;
        
        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prior = null;
        }
        
        return item;
    }
        
    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("The deque is empty!");
        
        Item item = last.item;
        size--;
        
        if (isEmpty()) {
            first = null;
            last = null;
        } else {
            last = last.prior;
            last.next = null;
        }
        
        return item;
    }
    
    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more items in iteration!");
            }
            
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
        
    
    public static void main(String[] args) {
        
    }
}