package iipscada.util;

/**
 * 每秒定时任务
 *
 * @Auther: mao
 * @Date: 18-10-21 16:16
 */
public class SecondJobUtil {
    /**
     * 闪烁
     **/
    public static volatile boolean BLINK = true;

    private SecondJobUtil() {
    }

    public static void start() {
        Thread job = new Thread(() -> {
            while (true) {
                job();
            }
        });
        job.setDaemon(true);
        job.start();
    }

    private static void job() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        BLINK = !BLINK;
    }
}
