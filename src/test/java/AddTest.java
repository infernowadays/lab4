import container.LockFreeSet;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AddTest {
    @Test
    public void multiplicationOfZeroIntegersShouldReturnZero() {
        LockFreeSet<String> set = new LockFreeSet<>();

        set.add("test");
        assertTrue(set.contains("test"));
    }
}
