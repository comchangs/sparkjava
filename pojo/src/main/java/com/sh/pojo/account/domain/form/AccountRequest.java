package com.sh.pojo.account.domain.form;

import java.time.LocalDate;

public class AccountRequest {
    private Long id;

    private String nickname;

    private String email;

    private LocalDate joinedAt;

    private LocalDate passwordUpdateDate;

    private boolean alarmChangePassword;

    private boolean receiveEmail;
}
