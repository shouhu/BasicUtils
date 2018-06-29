package shouhu.cn.basicutilmodel;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangXinchao on 2018/1/11.
 */

public class ThreadPoolManager {

    private ThreadPoolManager() {

    }

    // 创建一个私有的静态的对象
    private static ThreadPoolManager instance = new ThreadPoolManager();
    private ThreadPoolProxy longPool;
    private ThreadPoolProxy shortPool;

    // 提供一个公有的得到该对象的方法
    public static ThreadPoolManager getInstance() {
        return instance;
    }

    // 根据需求开启不同的线程池
    // 对外提供一个创建线程池的方法
    // 用来处理本地文件.保证线程同步
    public synchronized ThreadPoolProxy createShortPool() {
        shortPool = new ThreadPoolProxy(3, 3, 5000L);
        return shortPool;
    }

    // 处理联网等耗时的操作的线程池
    public synchronized ThreadPoolProxy createLongPool() {
        longPool = new ThreadPoolProxy(3, 3, 5000L);
        return longPool;
    }

    // 线程池的代理对象
    // 写一个线程池代理的内部类
    public class ThreadPoolProxy {
        // 线程池对象
        private ThreadPoolExecutor pool;
        // 线程池能装的线程的个数
        private int corePoolSize;
        // 如果排队满了，额外开启的线程数
        private int maximumPoolSize;
        // 存活时间
        private long time;

        // 构造
        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long time) {
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.time = time;
        }

        // 执行一个异步任务
        public void execute(Runnable runnable) {
            if (pool == null) {
                // 创建线程池
                /**
                 * corePoolSize：线程池能装的线程的个数
                 * maximumPoolSize：如果排队也满了，额外开启的线程数
                 * keepAliveTime：如果线程没有要执行的任务了，该线程能存活多久
                 * unit： 存活的时间单位
                 * workQueue：如果线程池里面管理的线程都已经用了，那么剩下的任务就存到该对象中排队等候
                 *
                 */
                pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, time, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
            }

            // 调用线程池，执行异步任务
            pool.execute(runnable);
        }

        // 移除一个异步任务
        public void cancle(Runnable runnable) {
            // 判断，当该任务既不能为空，也不能崩溃，更不能停止
            if (pool != null && !pool.isShutdown() && !pool.isTerminated()) {
                // 移除
                pool.remove(runnable);
            }
        }

    }
}
