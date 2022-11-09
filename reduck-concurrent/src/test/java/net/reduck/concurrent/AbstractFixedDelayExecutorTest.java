package net.reduck.concurrent;

import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Gin
 * @since 2022/11/9 15:36
 */
public class AbstractFixedDelayExecutorTest {
    @Test
    public void test() throws InterruptedException {
        testFixedDelayExecutor();
        System.gc();
        Thread.sleep(100000);

    }

    @Test
    public void testFixedDelayExecutor() throws InterruptedException {
        TestFixedDelayExecute execute = new TestFixedDelayExecute(0, 3, TimeUnit.SECONDS);
        execute.start();

    }

    public static class TestFixedDelayExecute extends AbstractFixedDelayExecutor{
        public TestFixedDelayExecute(long initialDelay, long delay, TimeUnit unit) {
            super(initialDelay, delay, unit);
        }

        public TestFixedDelayExecute(ScheduledExecutorService service, long initialDelay, long delay, TimeUnit unit) {
            super(service, initialDelay, delay, unit);
        }

        private AtomicInteger integer = new AtomicInteger(0);

        @Override
        public void run() {
            if(integer.getAndIncrement() > 3){
                stop();

                System.out.println("stopped");
            }

            System.out.println(integer.get());
        }
    }
}
