package mydemo.com.mydemo.network;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Namita on 7/23/2018.
 */
public class ThreadUtils {
    private static final String LOG_TAG = "THREAD_SERVICE";
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static ExecutorService defaultExecutorService = new ThreadPoolExecutor(
            NUMBER_OF_CORES, // Initial
            // pool
            // size
            NUMBER_OF_CORES, // Max
            // pool
            // size
            1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    private static ExecutorService singleThreadedExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());


    private ThreadUtils() {
    }

    public static ExecutorService getDefaultExecutorService() {
        return defaultExecutorService;
    }

    public static ExecutorService getSingleThreadedExecutor() {
        return singleThreadedExecutor;
    }


}

