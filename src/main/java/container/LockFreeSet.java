package container;

import container.model.Node;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;

public class LockFreeSet<T extends Comparable<T>> implements ILockFreeSet<T>, Iterable<T> {
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

    public void printSet(){
        Node<T> curr = head.getNext();
        while (curr != null) {
            System.out.println(curr.value());
            curr = curr.getNext();
        }
    }

    private Pair<T> search(T value) {
        retry:
        while (true) {
            Node<T> prev = head;
            Node<T> curr = head.getNext();
            Node<T> next;
            while (curr != null) {
                next = curr.getNext();
                if (curr.isMarked()) {
                    if (!prev.casNext(curr, next))
                        continue retry;
                    curr = next;
                } else {
                    if (curr.value().compareTo(value) >= 0)
                        return new Pair<>(prev, curr);
                    prev = curr;
                    curr = next;
                }
            }
            return new Pair<>(prev, null);
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new LockFreeSetIterator<T>(this);
    }

    class Pair<T extends Comparable<T>> {
        final Node<T> prev, curr;

        Pair(Node<T> prev, Node<T> cur) {
            this.prev = prev;
            this.curr = cur;
        }
    }

    class LockFreeSetIterator<T extends Comparable<T>> implements Iterator<T> {
        private Node<T> current;

        LockFreeSetIterator(LockFreeSet<T> lockFreeSet)
        {
            this.current = lockFreeSet.head.getNext();
        }

        public boolean hasNext()
        {
            return current != null;
        }

        public T next()
        {
            T node = current.value();
            current = current.getNext();
            return node;
        }
    }
}