package gr.conference.usersys;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

    private static final int MAX_THREADS = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

    public static void submitTask(Runnable task) {
        executorService.submit(task);
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}
