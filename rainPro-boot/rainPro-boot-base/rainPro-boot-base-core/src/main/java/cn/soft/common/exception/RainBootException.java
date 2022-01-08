package cn.soft.common.exception;

/**
 * 自定义的异常
 */
public class RainBootException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RainBootException(String message) {
        super(message);
    }

    public RainBootException(String message, Throwable cause) {
        super(message, cause);
    }

    public RainBootException(Throwable cause) {
        super(cause);
    }
}
