package iipscada.exception;

/**
 * 数据提供异常
 *
 * @Auther: mao
 * @Date: 18-10-25 10:15
 */
public class DataProviderException extends Exception {
    protected String msg = "";

    public DataProviderException() {
        super();
    }

    public String getMsg() {
        return msg;
    }

    public DataProviderException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
