package container;

import java.util.concurrent.atomic.AtomicInteger;

public interface ILockFreeSet<T> {
    boolean add(T value);

    boolean remove(T value);

    boolean contains(T value);

    boolean isEmpty();

//    java.util.Iterator<T> iterator();
}
