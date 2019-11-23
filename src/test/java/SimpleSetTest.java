import com.devexperts.dxlab.lincheck.LinChecker;
import com.devexperts.dxlab.lincheck.LoggingLevel;
import com.devexperts.dxlab.lincheck.Options;
import com.devexperts.dxlab.lincheck.annotations.Operation;
import com.devexperts.dxlab.lincheck.annotations.Param;
import com.devexperts.dxlab.lincheck.paramgen.IntGen;
import com.devexperts.dxlab.lincheck.strategy.stress.StressCTest;
import com.devexperts.dxlab.lincheck.strategy.stress.StressOptions;
import container.LockFreeSet;
import org.junit.Test;

@Param(name = "value", gen = IntGen.class, conf = "1:1000")
@StressCTest
public class SimpleSetTest {
    private LockFreeSet<Integer> set = new LockFreeSet<>();

    @Operation
    public boolean add(@Param(name = "value") int value) {
        return set.add(value);
    }

    @Operation
    public boolean remove(@Param(name = "value") int value) {
        return set.remove(value);
    }

    @Operation
    public boolean contains(@Param(name = "value") int value) {
        return set.contains(value);
    }

    @Operation
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Test
    public void test() {
        Options opts = new StressOptions()
                .invocationsPerIteration(1)
                .actorsBefore(10)
                .iterations(5)
                .threads(5)
                .actorsPerThread(3)
                .actorsAfter(10)
                .logLevel(LoggingLevel.INFO);
        LinChecker.check(SimpleSetTest.class, opts);
    }
}