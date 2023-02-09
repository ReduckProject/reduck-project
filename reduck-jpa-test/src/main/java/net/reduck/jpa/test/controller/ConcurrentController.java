package net.reduck.jpa.test.controller;

import net.reduck.concurrent.AbstractFixedDelayExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Reduck
 * @since 2022/11/9 16:28
 */
@RestController
@RequestMapping(value = "/concurrent")
public class ConcurrentController {
    @GetMapping(value = "/test")
    public String test(@RequestParam(required = false) long wait) throws InterruptedException {
        CountExecutor executor = new CountExecutor(0, 3, TimeUnit.SECONDS);
        executor.start();
        Thread.sleep(wait);
        return "success";
    }

    public static class CountExecutor extends AbstractFixedDelayExecutor{

        private final AtomicInteger counter = new AtomicInteger(0);

        public CountExecutor(long initialDelay, long delay, TimeUnit unit) {
            super(initialDelay, delay, unit);
        }

        public CountExecutor(ScheduledExecutorService service, long initialDelay, long delay, TimeUnit unit) {
            super(service, initialDelay, delay, unit);
        }

        @Override
        public void run() {
            if(counter.get() > 3){
                stop();
            }

            System.out.println(counter.getAndIncrement());
        }
    }
}
