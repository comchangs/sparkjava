package com.sh.pojo.account.exception;

import java.util.logging.Logger;

public class AccountNotFoundException extends RuntimeException{
    private final Logger logger = Logger.getGlobal();

    public AccountNotFoundException() {
        logger.warning("존재하지 않는 정보 입니다.");
    }

    public AccountNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
