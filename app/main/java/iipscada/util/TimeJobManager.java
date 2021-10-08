package iipscada.util;

import iipscada.annotation.Job;
import iipscada.annotation.TimeJob;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static iipscada.util.JobTypeEnum.*;

/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :定时任务调度器
 */
enum JobTypeEnum {
    TIMED_TASK, INTERVAL_TASK
}

public class TimeJobManager {

    private static class TimeJobOp implements Runnable {
        private final JobTypeEnum type;
        private final TimeJob timeJob;
        private final long interval;
        private volatile boolean runningFlag = false;

        public <T extends TimeJob> TimeJobOp(Class<T> timeJob, Job job) {

            //两种状态,指定时间执行,间隔执行
            if (StringUtils.isEmpty(job.at())) {
                type = INTERVAL_TASK;
                interval = Math.max(100, job.interval());
                runningFlag = true;
            } else {
                type = TIMED_TASK;
                interval = 100;
            }
            try {
                this.timeJob = timeJob.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("无法初始化任务!!");
            }
        }

        @Override
        public void run() {
            do {
                switch (type) {
                    case TIMED_TASK:
                        break;
                    case INTERVAL_TASK:
                        try {
                            timeJob.run();
                            TimeUnit.MILLISECONDS.sleep(interval);
                        } catch (InterruptedException e) {
                            throw new RuntimeException("任务执行出错!!");
                        }
                        break;
                    default:
                }
            } while (runningFlag);
        }
    }

    private Map<String, TimeJobOp> jobs = new ConcurrentHashMap<>();

    private TimeJobManager() {
    }

    private static TimeJobManager _instance = new TimeJobManager();

    /**
     * 添加定时任务
     */
    public static <T extends TimeJob> void addJob(Class<T> timeJob) {
        if (!timeJob.isAnnotationPresent(Job.class))
            throw new RuntimeException("timeJob is not extends from Job");
        Job job = timeJob.getAnnotation(Job.class);

        if (StringUtils.isEmpty(job.name()))
            throw new RuntimeException("任务名称不能为空!");

        TimeJobOp TimeJobOp = new TimeJobOp(timeJob, job);
        _instance.jobs.put(job.name(), TimeJobOp);

        Thread th = new Thread(TimeJobOp);
        th.setDaemon(true);
        th.start();
    }

}
