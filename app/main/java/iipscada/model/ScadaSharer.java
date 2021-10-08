package iipscada.model;

import iipscada.paginate.PaginateModel;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * 分享模型
 *
 * @Auther: mao
 * @Date: 18-12-7 13:01
 */
public class ScadaSharer extends PaginateModel<ScadaSharer> {
    static final public ScadaSharer dao = new ScadaSharer();

    public List<ScadaSharer> findByScadaId(long scadaId) {
        return find("select * from me_share where scadaid=?", scadaId);
    }

    public ScadaSharer findByScadaShareKey(String key) {
        return findFirst("select * from me_share where `key`=?", key);
    }

    /**
     * 保存分享
     *
     * @return
     */
    @Override
    public boolean save() {
        String endTime = getStr("endtime");
        set("key", System.currentTimeMillis());
        if (StringUtils.isEmpty(endTime))
            remove("endtime");
        else {
            LocalDate localDate = LocalDate.now();
            set("endtime", localDate.plusDays(Long.valueOf(endTime)).toString());
        }
        return super.save();
    }
}