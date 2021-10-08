package iipscada.annotation;

/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :定时任务
 */
public interface TimeJob extends Runnable {
    void run();
}
