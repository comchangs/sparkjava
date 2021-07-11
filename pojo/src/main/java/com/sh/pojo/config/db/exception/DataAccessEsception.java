package com.sh.pojo.config.db.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataAccessEsception extends RuntimeException {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public DataAccessEsception() {
        super();
    }

    public DataAccessEsception(String message) {
        super(message);
        log.error(message);
    }

    public DataAccessEsception(String message, Throwable cause) {
        super(message, cause);
        log.error(String.valueOf(cause));
    }

    public DataAccessEsception(Throwable cause) {
        super(cause);
        log.error(String.valueOf(cause));

    }

    protected DataAccessEsception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        log.error(String.valueOf(cause));
    }
}
