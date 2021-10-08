import iipscada.jobs.ClearShareTimeJob;
import iipscada.util.TimeJobManager;
import org.junit.Test;

/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :不可描述
 */
public class TimeManagerTest {
    @Test
    public synchronized void funTest() throws InterruptedException {
        TimeJobManager.addJob(ClearShareTimeJob.class);
        wait();
    }
}
