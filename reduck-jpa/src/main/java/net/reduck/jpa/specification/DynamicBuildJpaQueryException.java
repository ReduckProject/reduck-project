package net.reduck.jpa.specification;

/**
 * @author Gin
 * @since 2023/2/7 17:40
 */
public class DynamicBuildJpaQueryException extends RuntimeException {

    public DynamicBuildJpaQueryException() {
    }

    public DynamicBuildJpaQueryException(String message) {
        super(message);
    }

    public DynamicBuildJpaQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DynamicBuildJpaQueryException(Throwable cause) {
        super(cause);
    }

    public DynamicBuildJpaQueryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
