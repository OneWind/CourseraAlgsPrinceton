import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;
    
    private class Node {
        private Item item;
        private Node previous;
        private Node next;
    }
    
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size() {
        return N;
    }
    
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Can't add null value");
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        first.next = oldfirst;
        if (oldfirst == null) {
            last = first;
        } else if (oldfirst != null) {
            oldfirst.previous = first;            
        }
        N++;
    }
    
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Can't add null value");
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.previous = oldlast;
        last.next = null;
        if (oldlast == null) {
            first = last;
        } else if (oldlast != null) {
            oldlast.next = last;
        }
        N++;
    }
    
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Empty Deque");
        Item item = first.item;
        first = first.next;
        if (!isEmpty()) first.previous = null;
        N--;
        if (first == null) last = null;
        return item;
    }
    
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Empty Deque");
        Item item = last.item;
        last = last.previous;
        if (last == null) first = null;
        if (!isEmpty()) last.next = null;
        N--;
        return item;
    }
    
    public Iterator<Item> iterator() { return new ListIterator(); }
    
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    
    public static void main(String[] args) {
        Deque<String> dq = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-")) dq.addLast(item);
            else dq.addFirst(item);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("#######################");
        while (!dq.isEmpty()) {
            System.out.println(dq.removeLast());
        }
    }
    
}