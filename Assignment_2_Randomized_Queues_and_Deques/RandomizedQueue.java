import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;
    private int size;
    
    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        
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
        
         
        
        size++;
    }
    
    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
        
        
    }
    
    /**
     * return (but do not remove) a random item
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("The RQueue is empty!");
    }
    
    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        
    }
    
    public static void main(String[] args)  {
        
    }
}