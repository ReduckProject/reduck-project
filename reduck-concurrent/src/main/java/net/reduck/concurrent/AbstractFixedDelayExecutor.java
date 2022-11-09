package net.reduck.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Gin
 * @since 2022/11/9 15:11
 */
public abstract class AbstractFixedDelayExecutor implements Runnable {

    private final ScheduledExecutorService service;
    private ScheduledFuture future;
    private final long initialDelay;
    private final long delay;
    private final TimeUnit unit;

    public AbstractFixedDelayExecutor(long initialDelay, long delay, TimeUnit unit) {
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.unit = unit;
        this.service = Executors.newSingleThreadScheduledExecutor();
    }

    public AbstractFixedDelayExecutor(ScheduledExecutorService service, long initialDelay, long delay, TimeUnit unit) {
        this.service = service;
        this.initialDelay = initialDelay;
        this.delay = delay;
        this.unit = unit;
    }

    @Override
    public abstract void run();

    /**
     * 停止当前线程
     */
    public synchronized void stop() {
        if (future == null) {
            throw new IllegalStateException("There is not task in running");

        }
        future.cancel(false);
        this.future = null;
    }

    public synchronized void start() {
        if (future != null) {
            throw new IllegalStateException("Current task is running");
        }

        this.future = service.scheduleWithFixedDelay(this, initialDelay, delay, unit);
    }

    public synchronized boolean startIfIdle() {
        if(running()){
            start();
        }

        return !running();
    }

    public synchronized boolean running() {
        return this.future == null;
    }
}
