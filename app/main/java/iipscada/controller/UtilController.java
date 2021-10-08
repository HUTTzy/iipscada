package iipscada.controller;

import com.jfinal.core.Controller;

import java.util.*;

/**
 * 工具箱控制器
 *
 * @Auther: mao
 * @Date: 18-11-30 23:37
 */
public class UtilController extends Controller {
    private static Random rn = new Random();

    public void kitBox() {
        render("kitbox.html");
    }

    public void selectData() {
        List<Map<String, Object>> selectData = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("value", i);
            item.put("text", "小猪-" + rn.nextInt(100) + 10);
            selectData.add(item);
        }
        renderJson(selectData);
    }
}
