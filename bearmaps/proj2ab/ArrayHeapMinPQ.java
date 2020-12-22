package bearmaps;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private Node<T>[] pq;
    private int size;
    private HashMap<T, Integer> itemIndex;

    private class Node<T> {
        private T item;
        private double priority;

        public Node(T i, double p) {
            item = i;
            priority = p;
        }

        public T getItem() {
            return item;
        }
        public double getPriority() {
            return priority;
        }
    }

    public ArrayHeapMinPQ() {
        pq = (Node<T>[]) new Node[20];
        pq[0] = null;
        itemIndex = new HashMap<>(20);
        size = 1;
    }

    private int parentIndex(int x) {
        return x / 2;
    }

    private int leftIndex(int x) {
        return x * 2;
    }

    private int rightIndex(int x) {
        return x * 2 + 1;
    }

    @Override
    public int size() {
        return size - 1;
    }

    @Override
    public void add(T item, double priority) {
        if (!itemIndex.containsKey(item)) {
            expand();
            pq[size] = new Node(item, priority);
            itemIndex.put(item, size);
            swim(size);
            size++;
        } else {
            throw new IllegalArgumentException("There cannot be duplicates!");
        }
    }

    private void swim(int i) {
        if (i <= 1) {
            return;
        } else {
            int pIndex = parentIndex(i);
            Node<T> curr = pq[i];
            Node<T> parent = pq[pIndex];
            if (curr.priority < parent.priority) {
                swap(curr, parent);
                swim(pIndex);
            }
        }
    }

    private void swap(Node<T> a, Node<T> b) {
        int a1 = itemIndex.get(a.item);
        int b1 = itemIndex.get(b.item);
        Node<T> temp = a;
        pq[a1] = b;
        pq[b1] = temp;
        itemIndex.replace(b.item, a1);
        itemIndex.replace(a.item, b1);
    }

    private int findMin(int parentIndex) {
        int minIndex = parentIndex;
        int left = leftIndex(parentIndex);
        int right = rightIndex(parentIndex);
        if (left <= size() && pq[left].priority < pq[minIndex].priority) {
            minIndex = left;
        }
        if (right <= size() && pq[right].priority < pq[minIndex].priority) {
            minIndex = right;
        }
        return minIndex;
    }

    private void sink(int currentIndex) {
        int sinkIndex = findMin(currentIndex);
        if (sinkIndex != currentIndex) {
            swap(pq[currentIndex], pq[sinkIndex]);
            sink(sinkIndex);
        }
    }

    @Override
    public boolean contains(T item) {
        return itemIndex.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() > 0) {
            return pq[1].item;
        } else {
            throw new NoSuchElementException("There's no items.");
        }
    }

    @Override
    public T removeSmallest() {
        if (size() > 0) {
            T toReturn = getSmallest();
            swap(pq[1], pq[size - 1]);
            pq[size - 1] = null;
            itemIndex.remove(toReturn);
            size--;
            sink(1);
            reduce();
            return toReturn;
        } else {
            throw new NoSuchElementException("There's no items.");
        }
    }



    @Override
    public void changePriority(T item, double newPriority) {
        if (itemIndex.containsKey(item)) {
            int i = itemIndex.get(item);
            Node<T> current = pq[i];
            if (newPriority < current.priority) {
                current.priority = newPriority;
                swim(i);
            } else if (newPriority > current.priority) {
                current.priority = newPriority;
                sink(i);
            }
        } else {
            throw new NoSuchElementException("The item doesn't exist.");
        }
    }

    private void expand() {
        if (size == pq.length) {
            Node<T>[] temp = (Node<T>[]) new Node[pq.length * 2];
            for (int i = 0; i < size; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }
    }

    private void reduce() {
        if ((float) size / (float) pq.length < 0.25 && pq.length > 20) {
            Node<T>[] temp = (Node<T>[]) new Node[pq.length / 2];
            for (int i = 0; i < size; i++) {
                temp[i] = pq[i];
            }
            pq = temp;
        }
    }

}
