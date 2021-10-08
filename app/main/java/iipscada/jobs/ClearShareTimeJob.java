package iipscada.jobs;

import com.jfinal.plugin.activerecord.Db;
import iipscada.annotation.Job;
import iipscada.annotation.TimeJob;

/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :每天定时清除过期的分享组态
 */
@Job(name = "shareClear", interval = 1000 * 60 * 60 * 24, loop = true)
public class ClearShareTimeJob implements TimeJob {
    private static final String SQL = "delete from me_share where DATE(endtime) <= DATE(DATE_SUB(NOW(),INTERVAL 1 day));";

    @Override
    public void run() {
        System.out.println("开始清理过期分享!!!!");
        int count = Db.delete(SQL);
        System.out.println(String.format("已清理【%d】个过期分享组态", count));
    }
}
