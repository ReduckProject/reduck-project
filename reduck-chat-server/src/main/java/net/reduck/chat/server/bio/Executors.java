package net.reduck.chat.server.bio;

import java.util.concurrent.ExecutorService;

/**
 * @author Reduck
 * @since 2023/1/4 12:50
 */
public class Executors {
    private static final ExecutorService executorService = java.util.concurrent.Executors.newFixedThreadPool(1000);

    public static void execute(Runnable runnable) {
        executorService.execute(runnable);
    }
}
