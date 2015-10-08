import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N = 0;
    
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }

    private void resize(int capacity) {
        assert capacity >= N;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }
    
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Can't add null item");
        if (N == q.length) resize(2 * q.length);
        q[N++] = item;
    }
    
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");
        int dequeueN = StdRandom.uniform(N);
        Item dequeueItem = q[dequeueN];
        q[dequeueN] = q[N-1];
        q[N-1] = null;
        N--;
        if (N > 0 && N == q.length/4) resize(q.length/2);
        return dequeueItem;
    }
    
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Empty queue");
        int dequeueN = StdRandom.uniform(N);
        return q[dequeueN];
    }
    
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        private int[] idx = new int[N];
        
        public ArrayIterator() {
            for (int j = 0; j < N; j++) {
                idx[j] = j;
            }
            StdRandom.shuffle(idx);
        }
        
        public boolean hasNext()  { return i < N;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[idx[i]];
            i++;
            return item;
        }
    }
    
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            q.enqueue(item);
        }
        for (int i = 0; i < 5; i++) {
            System.out.print(q.sample() + " *** ");
        }
        System.out.println();
        Iterator itr0 = q.iterator();
        while (itr0.hasNext()) {
            System.out.print(itr0.next() + " *** ");
        }        
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.print(q.dequeue() + " *** ");
        }
        System.out.println();
        System.out.println(q.size());

        Iterator itr = q.iterator();
        Iterator itr2 = q.iterator();

        while (itr.hasNext()) {
            System.out.print(itr.next() + " *** ");
        }        
        System.out.println();
        while (itr2.hasNext()) {
            System.out.print(itr2.next() + " *** ");
        }        
        System.out.println();
    }
    
}