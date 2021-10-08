import iipscada.util.MD5Util;
import org.junit.Test;

/**
 * 获取MD5密码
 *
 * @Auther: mao
 * @Date: 18-10-19 15:30
 */
public class MD5Test {

    @Test
    public void enCode() {
        System.out.println(MD5Util.encode("111111"));
    }

}
