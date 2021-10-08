package iipscada.exception;

/**
 * 数据未找到异常
 *
 * @Auther: mao
 * @Date: 18-10-25 10:16
 */
public class DataNotFoundException extends DataProviderException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String msg) {
        super(msg);
    }

}
