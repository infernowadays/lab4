import container.LockFreeSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class LockFreeSetTest {
    private LockFreeSet<String> lockFreeSet = new LockFreeSet<>();

    public void setLockFreeSet(LockFreeSet<String> lockFreeSet) {
        this.lockFreeSet = lockFreeSet;
    }

    @Before
    public void initLockFreeSet(){
        setLockFreeSet(new LockFreeSet<String>());
    }

    @Test
    public void addTest() {

        lockFreeSet.add("test");
        assertTrue(lockFreeSet.contains("test"));
        for(int i = 0; i < 100; i++){

        }
    }

    @Test
    public void removeTest() {
    }

    @Test
    public void searchTest() {
    }

    @Test
    public void containsTest() {
    }

    @Test
    public void isEmptyTest() {
    }

    @Test
    public void getSizeTest() {
    }
}
