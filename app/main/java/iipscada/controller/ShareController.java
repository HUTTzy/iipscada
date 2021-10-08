package iipscada.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import iipscada.model.ScadaSharer;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * scada分享控制器
 *
 * @Auther: mao
 * @Date: 18-12-7 12:57
 */
public class ShareController extends Controller {
    public void delete(@Para("shareId") Long shareId) {

    }

    public void doAdd(@Para("") ScadaSharer ss, @Para("endtime") String endtime) {
        ss.set("endtime", endtime);
        boolean result = ss.save();
        if (result) {
            renderJson(new Record().set("url", APIController.API_URL + "/view/" + ss.get("key")));
        } else
            renderJson(false);
    }


    public void query() {

    }

    public void doQuery() {

    }
}
