package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class MinPQ<T extends Comparable<T>> {
    private List<T> items;

    /** Initializes an empty priority queue. */
    public MinPQ() {
        items = new ArrayList<>();
        items.add(null);
    }

    /** Adds a new key to this priority queue. */
    public void add(T item) {
        // This implementation allows duplicate items.
        items.add(item);
        swim(size());
    }

    /** Returns a smallest key on this priority queue. */
    public T peekMin() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(1);
    }

    /** Removes and returns a smallest key on this priority queue. */
    public T removeMin() {
        if (size() == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        T min = peekMin();
        swap(1, size());
        items.remove(size());
        sink(1);
        return min;
    }

    /** Returns the number of keys on this priority queue. */
    public int size() {
        return items.size() - 1;
    }

    /** Returns the index of the given index's parent node. */
    private static int parent(int index) {
        return index / 2;
    }

    /** Returns the index of the given index's left child. */
    private static int left(int index) {
        return index * 2;
    }

    /** Returns the index of the given index's right child. */
    private static int right(int index) {
        return left(index) + 1;
    }

    /** Returns true if and only if the index is accessible. */
    private boolean accessible(int index) {
        return 1 <= index && index <= size();
    }

    /** Returns the index with the lower priority, or 0 if neither is accessible. */
    private int min(int index1, int index2) {
        if (!accessible(index1) && !accessible(index2)) {
            return 0;
        } else if (accessible(index1) && (!accessible(index2)
                || items.get(index1).compareTo(items.get(index2)) < 0)) {
            return index1;
        } else {
            return index2;
        }
    }

    /** Swap the nodes at the two indices. */
    private void swap(int index1, int index2) {
        T temp = items.get(index1);
        items.set(index1, items.get(index2));
        items.set(index2, temp);
    }

    /** Bubbles up the node currently at the given index. */
    private void swim(int index) {
        int parent = parent(index);
        while (accessible(parent) && items.get(index).compareTo(items.get(parent)) < 0) {
            swap(index, parent);
            index = parent;
            parent = parent(index);
        }
    }

    /** Bubbles down the node currently at the given index. */
    private void sink(int index) {
        int child = min(left(index), right(index));
        while (accessible(child) && items.get(index).compareTo(items.get(child)) > 0) {
            swap(index, child);
            index = child;
            child = min(left(index), right(index));
        }
    }
}