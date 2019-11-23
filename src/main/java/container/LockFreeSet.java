package container;

import container.model.Node;
import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeSet<T extends Comparable<T>> implements ILockFreeSet<T> {
    private Node<T> head;

    public LockFreeSet() {
        this.head = new Node<T>(null, null);
    }

    @Override
    public boolean add(T value) {
        while (true) {
            Pair<T> pair = search(value);
            Node<T> prev = pair.prev;
            Node<T> curr = pair.curr;

            if (curr != null && curr.value().compareTo(value) == 0) {
                return false;
            } else {
                Node<T> node = new Node<>(value, curr);
                if (prev.casNext(curr, node)) {
                    return true;
                }
            }
        }
    }

    @Override
    public boolean remove(T value) {
        while (true) {
            Pair<T> pair = search(value);
            Node<T> prev = pair.prev;
            Node<T> curr = pair.curr;

            if (curr == null || curr.value().compareTo(value) != 0) {
                return false;
            } else {
                Node<T> next = curr.getNext();
                if (!curr.mark(next))
                    continue;
                prev.casNext(curr, next);
                return true;
            }
        }
    }

    @Override
    public boolean contains(T value) {
        Node<T> curr = head.getNext();
        while (curr != null && curr.value().compareTo(value) < 0) {
            curr = curr.getNext();
        }
        return curr != null && curr.value().compareTo(value) == 0 && !curr.isMarked();
    }

    @Override
    public boolean isEmpty() {
        return head.getNext() == null;
    }

    @Override
    public AtomicInteger size() {
        Node<T> curr = head.getNext();
        AtomicInteger counter = new AtomicInteger();
        while (curr != null) {
            curr = curr.getNext();
            counter.incrementAndGet();
        }
        return counter;
    }

    private Pair<T> search(T value) {
        retry:
        while (true) {
            Node<T> prev = head;
            Node<T> cur = head.getNext();
            Node<T> next;
            while (cur != null) {
                next = cur.getNext();
                if (cur.isMarked()) {
                    if (!prev.casNext(cur, next))
                        continue retry;
                    cur = next;
                } else {
                    if (cur.value().compareTo(value) >= 0)
                        return new Pair<>(prev, cur);
                    prev = cur;
                    cur = next;
                }
            }
            return new Pair<>(prev, null);
        }
    }

    class Pair<T extends Comparable<T>> {
        final Node<T> prev, curr;

        Pair(Node<T> prev, Node<T> cur) {
            this.prev = prev;
            this.curr = cur;
        }
    }
}