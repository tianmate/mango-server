package cn.sky1998.mango.framework.config;

import cn.sky1998.mango.common.utils.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author tcy@1753163342@qq.com
 * @Date 16-12-2021
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    // 核心线程池大小
    private int corePoolSize = 50;

    /**
     * 线程池大小
     */
    private Integer poolsize=2;

    /**
     * 缓存执行任务的队列
     */
    private Integer QueueCapacity=2;

    /**
     * 线程空闲时间
     */
    private Integer KeepAliveSeconds=10;
    /**
     * 配置线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor ThreadPool(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolsize);
        /**
         * 最大线程池大小
         */
        Integer maxpoolsize = 2;
        executor.setMaxPoolSize(maxpoolsize);
        executor.setQueueCapacity(QueueCapacity);
        executor.setKeepAliveSeconds(KeepAliveSeconds);
        executor.setThreadNamePrefix("otherThread......");
        //线程池满拒绝策略
    //    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //自定义拒绝策略
        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                if (!e.isShutdown()){
                    while (e.getQueue().remainingCapacity()==0){
                        e.execute(r);
                    }
                }
            }
        });
        return executor;
    }


    /**
     * 执行周期性或定时任务
     */
    @Bean(name = "scheduledExecutorService")
    protected ScheduledExecutorService scheduledExecutorService()
    {
        return new ScheduledThreadPoolExecutor(corePoolSize,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build(),
                new ThreadPoolExecutor.CallerRunsPolicy())
        {
            @Override
            protected void afterExecute(Runnable r, Throwable t)
            {
                super.afterExecute(r, t);
                Threads.printException(r, t);
            }
        };
    }
}