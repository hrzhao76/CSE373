package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ_custom<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ_custom() {
        items = new ArrayList<>();
        itemToIndex = new HashMap<>();
        items.add(new PriorityNode<>(null, -1.));
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        this.items.add(new PriorityNode<>(item, priority)); // Add an item to the end of list
        int pos = GetSwimPos(size()); //Swim to restore the heap invariant
        itemToIndex.put(item, pos);
    }


    private int GetSwimPos(int index) {
        int parent = parent(index);
        double current_priority = items.get(index).priority();
        while (accessible(parent) && (current_priority < items.get(parent).priority())) {
            this.itemToIndex.replace(items.get(parent).item(), index);
            index = parent;
            parent = parent(index); // needed to start next iteration
        }
        return index;
    }

    private static int parent(int index) {
        return index / 2;
    }

    private boolean accessible(int index) {
        return 1 <= index && index <= size();
    }

    @Override
    public boolean contains(T item) {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return itemToIndex.containsValue(item);
    }

    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        return GetKeyfromValue(1);
    }

    private T GetKeyfromValue(Integer value){
        T return_key=null;
        //throw new UnsupportedOperationException("Not implemented yet");
        for (Map.Entry<T, Integer> entry : itemToIndex.entrySet()) {
            if (entry.getValue().equals(value))
                return_key=entry.getKey();
            break;
        }
        return return_key;
    }

    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        // 1st: swap the root and the last
        T root = peekMin();
        T last = GetKeyfromValue(size());
        //itemToIndex.get(min);
        itemToIndex.replace(root, size());
        itemToIndex.replace(last, 1);

        // 2nd: remove the last
        items.remove(size());
        //swap(1, size());
        //items.remove(size());
        //sink(1);
        return root;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int size() {
        // TODO: Replace with your code
        //throw new UnsupportedOperationException("Not implemented yet");
        return this.items.size()-1;
    }
}
