package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        this.pq.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        // A little tricky here; need to construct a new PriorityNode to comapre
        if(this.pq.contains(new PriorityNode<>(item, 0.0)))
            return true;
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return this.pq.peek().item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return this.pq.poll().item();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        //for (PriorityNode stored_item : this.pq) { // remove() in for-loop doesn't work
        //    if (stored_item.equals(new PriorityNode<>(item, priority)))
        //        //stored_item.setPriority(priority); //Doesn't work!
        //        this.pq.remove(stored_item);
        //        break;
        //}
        this.pq.remove(new PriorityNode<>(item, 0.0));
        this.pq.add(new PriorityNode<>(item, priority));
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return this.pq.size();
    }
}
