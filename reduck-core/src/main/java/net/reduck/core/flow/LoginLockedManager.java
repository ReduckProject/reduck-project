package net.reduck.core.flow;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author Reduck
 * @since 2023/6/7 23:04
 */
public class LoginLockedManager {

    private final Map<String, ManagerNode> recordQueue = new HashMap<>();

    private int maxFailedTimes;
    private long lockedDuration;
    private long failedDuration;

    private ScheduledFuture<?> future;

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true); // 设置线程为守护线程
        return thread;
    });

    public LoginLockedManager(int maxFailedTimes, long lockedDuration, long failedDuration) {
        this.maxFailedTimes = maxFailedTimes;
        this.lockedDuration = lockedDuration;
        this.failedDuration = failedDuration;

        future = executorService.scheduleAtFixedRate(this::clearExpiredManagerNode, 30, 30, TimeUnit.SECONDS);
    }

    public synchronized void success(String key) {
        recordQueue.remove(key);
    }

    public synchronized void failed(String key) {

        ManagerNode node = recordQueue.getOrDefault(key, new ManagerNode());
        node.failed();
        if (!recordQueue.containsKey(key)) {
            recordQueue.put(key, node);
        }
    }

    private synchronized void clearExpiredManagerNode() {
        for (Map.Entry<String, ManagerNode> entry : recordQueue.entrySet()) {
            entry.getValue().expiredCheck(failedDuration);
            if (entry.getValue().empty()) {
                recordQueue.remove(entry.getKey());
            }
        }
    }

    public synchronized boolean isLocked(String key) {
        if (!recordQueue.containsKey(key)) {
            return false;
        }

        return recordQueue.get(key).isLocked(maxFailedTimes, lockedDuration, failedDuration);
    }

    static class ManagerNode {
        private Queue<Long> failedQueue = new LinkedBlockingQueue<>();

        private Long lockedExpiredTime = Long.MIN_VALUE;

        private boolean locked;

        public boolean isLocked(int maxFailedTimes, long lockedDuration, long failedDuration) {
            long currentTime = System.currentTimeMillis();
            if (locked) {
                if (currentTime > lockedExpiredTime) {
                    locked = false;
                }
            } else {
                expiredCheck(failedDuration);
                if (failedQueue.size() >= maxFailedTimes) {
                    lockedExpiredTime = currentTime + lockedDuration;
                    locked = true;
                    clear();
                }
            }

            return locked;
        }

        public void success() {
            clear();
        }

        public void failed() {
            failedQueue.offer(System.currentTimeMillis());
        }

        public void expiredCheck(long failedDuration) {
            long minValidTime = System.currentTimeMillis() - failedDuration;
            if (failedQueue.size() > 0 && failedQueue.peek() < minValidTime) {
                failedQueue.poll();
            }
        }

        public boolean empty() {
            return failedQueue.size() > 0;
        }

        public void clear() {
            failedQueue = new LinkedBlockingQueue<>();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LoginLockedManager manager = new LoginLockedManager(5, 3 * 1000, 30 * 1000);

        for(int i = 0; i < 1000; i ++) {
            String key = String.valueOf(i % 10);
           if( manager.isLocked(key)) {
               System.out.println((i % 10) + "|" + i + " locked");
               Thread.sleep(1000);
               continue;
           }

           manager.failed(key);

           if(i % 100 == 0) {
               System.out.println(i + "----------");
           }
        }
    }
}
