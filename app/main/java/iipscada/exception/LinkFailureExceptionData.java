package iipscada.exception;

/**
 * 连接失败异常
 *
 * @Auther: mao
 * @Date: 18-10-25 10:32
 */
public class LinkFailureExceptionData extends DataProviderException {
    public LinkFailureExceptionData() {
        super();
    }

    public LinkFailureExceptionData(String msg) {
        super(msg);
    }

}
