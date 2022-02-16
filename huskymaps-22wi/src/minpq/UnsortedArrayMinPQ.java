package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // TODO: Replace with your code
        // throw new UnsupportedOperationException("Not implemented yet");
        items.add(new PriorityNode<>(item, priority));
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        // throw new UnsupportedOperationException("Not implemented yet");
        for (PriorityNode<T> stored_item : this.items) {
            if (stored_item.equals(new PriorityNode<>(item,0.0))){
                return true;
            }
        }
        return false;
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        double priority_min = this.items.get(0).priority();
        int index_min = 0;

        for (int i=0; i< this.items.size();i++){
            if (priority_min > this.items.get(i).priority()){
                priority_min = this.items.get(i).priority();
                index_min = i;
                break;
            }
        }

        return this.items.get(index_min).item();
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        double priority_min = this.items.get(0).priority();
        int index_min = 0;

        for (int i=0; i< this.items.size();i++){
            if (priority_min > this.items.get(i).priority()){
                priority_min = this.items.get(i).priority();
                index_min = i;
                break;
            }
        }
        T item_min = this.items.get(index_min).item();
        items.remove(index_min);
        return item_min;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        for (PriorityNode<T> stored_item : this.items) {
            if (stored_item.equals(new PriorityNode<>(item,0.0))){
                stored_item.setPriority(priority);
                break;
            }
        }
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return items.size();
    }
}
