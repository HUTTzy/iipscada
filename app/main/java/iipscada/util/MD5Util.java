package iipscada.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加密工具
 *
 * @Auther: mao
 * @Date: 18-10-18 12:50
 */
public class MD5Util {

    private static MessageDigest md5;
    private static BASE64Encoder base64en = new BASE64Encoder();

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
        }
    }

    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        //加密后的字符串
        String newstr = "";
        try {
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            System.err.println("加密失败");
        }
        return newstr;
    }

    /**
     * 获取随机6位数
     *
     * @return
     */
    public static String getInitPwd() {
        StringBuffer sb = new StringBuffer();
        Random rand = new Random();
        String sources = "0123456789";
        for (int j = 0; j < 6; j++) {
            sb.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return sb.toString();
    }
}
