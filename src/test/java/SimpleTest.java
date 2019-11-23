import container.LockFreeSet;
import container.model.Node;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SimpleTest {
    @Test
    public void addRemoveTest(){
        LockFreeSet<String> lockFreeSet = new LockFreeSet<>();
        lockFreeSet.add("test");
        lockFreeSet.remove("test");
        assertTrue(lockFreeSet.isEmpty());
    }

    @Test
    public void addRemoveTwoThreadsTest(){
        LockFreeSet<String> lockFreeSet = new LockFreeSet<>();

        Thread threadAdd1 = new Thread(() -> lockFreeSet.add("test"));
        Thread threadAdd2 = new Thread(() -> lockFreeSet.add("test"));
        Thread threadRemove = new Thread(() -> lockFreeSet.remove("test"));

        threadRemove.run();
        threadRemove.run();
        threadRemove.run();
        threadAdd2.run();
        threadAdd1.run();
        threadAdd1.run();
        threadRemove.run();
        threadAdd1.run();
        lockFreeSet.printSet();
    }

    @Test
    public void iteratorTest(){
        LockFreeSet<String> lockFreeSet = new LockFreeSet<>();

        lockFreeSet.add(String.valueOf(Math.random()));
        lockFreeSet.add(String.valueOf(Math.random()));
        lockFreeSet.add(String.valueOf(Math.random()));
        lockFreeSet.add(String.valueOf(Math.random()));
        lockFreeSet.add(String.valueOf(Math.random()));

        for(String node : lockFreeSet){
            System.out.println(node);
        }
    }
}