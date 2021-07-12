package com.sh.pojo.config.db.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnectionException extends RuntimeException {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public DbConnectionException() {
        super();
    }

    public DbConnectionException(String message) {
        super(message);
        log.error(message);
    }

    public DbConnectionException(String message, Throwable cause) {
        super(message, cause);
        log.error(String.valueOf(cause));
    }

    public DbConnectionException(Throwable cause) {
        super(cause);
        log.error(String.valueOf(cause));

    }

    protected DbConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(String.valueOf(cause));
    }
}
