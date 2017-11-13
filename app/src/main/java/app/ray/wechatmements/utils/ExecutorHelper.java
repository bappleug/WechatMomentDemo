package app.ray.wechatmements.utils;

import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Ray on 2017/11/12.
 */

public class ExecutorHelper {

    public static final ThreadPoolExecutor LOW;
    public static final ThreadPoolExecutor BALANCE;
    public static final ThreadPoolExecutor HIGH;

    public ExecutorHelper() {
    }

    static {
        LOW = new ThreadPoolExecutor(
                3,
                3,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new ExecutorHelper.PayLoadThreadFactory("low"));
        LOW.allowCoreThreadTimeOut(true);
        int countOfCPU = Runtime.getRuntime().availableProcessors();
        BALANCE = new ThreadPoolExecutor(
                countOfCPU,
                countOfCPU * 2 + 1,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ExecutorHelper.PayLoadThreadFactory("balance"));
        HIGH = new ThreadPoolExecutor(
                0,
                2147483647,
                60L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ExecutorHelper.PayLoadThreadFactory("high"));
    }

    private static class PayLoadThreadFactory implements ThreadFactory {
        private AtomicInteger incrementAndGet = new AtomicInteger();
        private final String poolName;

        public PayLoadThreadFactory(String poolName) {
            this.poolName = poolName;
        }

        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, this.poolName + "-pool-thread-" + this.incrementAndGet.getAndIncrement());
            t.setDaemon(true);
            return t;
        }
    }
}
