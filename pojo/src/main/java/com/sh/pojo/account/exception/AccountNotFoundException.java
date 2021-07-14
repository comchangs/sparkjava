package com.sh.pojo.account.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountNotFoundException extends RuntimeException{
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public AccountNotFoundException() {
        log.warn("존재하지 않는 정보 입니다.");
    }

    public AccountNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
