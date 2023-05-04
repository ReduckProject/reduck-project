package net.reduck.jpa.test;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Reduck
 * @since 2022/10/27 16:32
 */
public class ScheduleTest {

    @Test
    public void runNTimes(Runnable task, int maxRunCount, long period, TimeUnit unit, ScheduledExecutorService executor) {
        new FixedExecutionRunnable(task, maxRunCount).runNTimes(executor, period, unit);
    }

    class FixedExecutionRunnable implements Runnable {
        private final AtomicInteger runCount = new AtomicInteger();
        private final Runnable delegate;
        private volatile ScheduledFuture<?> self;
        private final int maxRunCount;

        public FixedExecutionRunnable(Runnable delegate, int maxRunCount) {
            this.delegate = delegate;
            this.maxRunCount = maxRunCount;
        }

        @Override
        public void run() {
            delegate.run();
            System.out.println("--------");
            if (runCount.incrementAndGet() == maxRunCount) {
                boolean interrupted = false;
                try {
                    while (self == null) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    }
                    self.cancel(false);
                } finally {
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public void runNTimes(ScheduledExecutorService executor, long period, TimeUnit unit) {
            self = executor.scheduleAtFixedRate(this, 0, period, unit);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger i = new AtomicInteger(1);
        AtomicInteger j = new AtomicInteger(1);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleWithFixedDelay(() -> {
            System.out.println("i" + i.get());
            if(i.incrementAndGet() > 5){
                throw new RuntimeException();
            }
        }, 0, 3, TimeUnit.SECONDS);

        service.scheduleWithFixedDelay(() -> {
            System.out.println("j" + j.get());
            if(j.incrementAndGet() > 5){
                throw new RuntimeException();
            }
        }, 0, 3, TimeUnit.SECONDS);

        Thread.sleep(300000L);
    }
}
