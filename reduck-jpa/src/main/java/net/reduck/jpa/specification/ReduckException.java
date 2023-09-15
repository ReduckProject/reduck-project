package net.reduck.jpa.specification;

/**
 * @author Gin
 * @since 2023/9/12 11:29
 */
public class ReduckException extends RuntimeException {

    public ReduckException() {
    }

    public ReduckException(String message) {
        super(message);
    }

    public ReduckException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReduckException(Throwable cause) {
        super(cause);
    }

    public ReduckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
