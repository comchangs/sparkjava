package com.sh.pojo.config.db.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAccessException extends RuntimeException {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message) {
        super(message);
        log.error(message);
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
        log.error(String.valueOf(cause));
    }

    public DataAccessException(Throwable cause) {
        super(cause);
        log.error(String.valueOf(cause));

    }

    protected DataAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(String.valueOf(cause));
    }
}
