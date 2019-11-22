package container.model;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class Node<T extends Comparable<T>> {
    private T value;
    private final AtomicMarkableReference<Node<T>> next;

    public Node(T value, Node<T> next) {
        this.value = value;
        this.next = new AtomicMarkableReference<Node<T>>(next, false);
    }

    public T value() {
        return value;
    }

    public Node<T> getNext() {
        return next.getReference();
    }

    public boolean casNext(Node<T> old, Node<T> next) {
        return this.next.compareAndSet(old, next, false, false);
    }

    public boolean mark(Node<T> next) {
        return this.next.compareAndSet(next, next, false, true);
    }

    public boolean isMarked() {
        return next.isMarked();
    }
}
