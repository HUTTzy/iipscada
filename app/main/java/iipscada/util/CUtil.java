package iipscada.util;

import java.util.Objects;

/**
 * 通用工具
 *
 * @Auther: mao
 * @Date: 18-12-27 17:13
 */
public class CUtil {
    private CUtil() {
    }

    /**
     * 字符串join
     *
     * @param spliter
     * @param attrs
     * @return
     */
    public static String join(String spliter, String... attrs) {
        Objects.requireNonNull(spliter, "分隔符不能为null");
        Objects.requireNonNull(attrs, "目标字符串不能为null");

        StringBuilder stringBuilder = new StringBuilder();
        for (String attr : attrs) {
            stringBuilder.append(attr).append(spliter);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
