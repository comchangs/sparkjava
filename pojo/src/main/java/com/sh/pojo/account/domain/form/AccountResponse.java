package com.sh.pojo.account.domain.form;

import com.sh.pojo.common.DateFormatter;
import com.sh.pojo.common.DatePattern;

import java.time.LocalDate;

public class AccountResponse {

    private Long id;

    private String nickname;

    private String email;

    private String joinedAt;

    private boolean alarmChangePassword;

    private boolean receiveEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate date) {
        this.joinedAt = new DatePattern(date).yearMonthDay();
    }

    public boolean isAlarmChangePassword() {
        return alarmChangePassword;
    }

    public void setAlarmChangePassword(boolean alarmChangePassword) {
        this.alarmChangePassword = alarmChangePassword;
    }

    public boolean isReceiveEmail() {
        return receiveEmail;
    }

    public void setReceiveEmail(boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    @Override
    public String toString() {
        return "AccountResponse{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", alarmChangePassword=" + alarmChangePassword +
                ", receiveEmail=" + receiveEmail +
                '}';
    }
}
