package container;

import java.util.concurrent.atomic.AtomicInteger;

public interface ILockFreeSet<T> {
    boolean add(T value);

    boolean remove(T value);

    boolean contains(T value);

//    Element<T> find(T value);

    boolean isEmpty();

    AtomicInteger size();

//    java.util.Iterator<T> iterator();
}
