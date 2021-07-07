package com.sh.pojo.account.exception;

import java.util.logging.Logger;

public class AccountWrongPasswordException extends RuntimeException{

    private final Logger logger = Logger.getGlobal();

    public AccountWrongPasswordException() {
        logger.warning("입력 정보가 맞지 않습니다. id나 비밀번호를 다시 입력해 주세요.");
    }

    public AccountWrongPasswordException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
