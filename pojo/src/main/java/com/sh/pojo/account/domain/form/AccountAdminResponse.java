package com.sh.pojo.account.domain.form;

import com.sh.pojo.common.DatePattern;

import java.time.LocalDate;
import java.util.Objects;

public class AccountAdminResponse {

    private Long id;

    private String nickname;

    private String email;

    private String joinedAt;

    private String passwordUpdateDate;

    private Integer passwordUpdateDateMonth;

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

    public String getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public void setPasswordUpdateDate(LocalDate passwordUpdateDate) {
        if(!Objects.isNull(passwordUpdateDate)) countUpdatedPasswordMonth(passwordUpdateDate);
        this.passwordUpdateDate = new DatePattern(passwordUpdateDate).yearMonthDay();;
    }

    public Integer getPasswordUpdatedMonth() {
        return passwordUpdateDateMonth;
    }

    public void setPasswordUpdatedMonth(Integer passwordUpdatedMonthRange) {
        this.passwordUpdateDateMonth = passwordUpdatedMonthRange;
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


    public void countUpdatedPasswordMonth(LocalDate updated) {
        LocalDate today = LocalDate.now();
        this.setPasswordUpdatedMonth(updated.until(today).getMonths());
    }

    @Override
    public String toString() {
        return "AccountAdminResponse{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", joinedAt='" + joinedAt + '\'' +
                ", passwordUpdateDate=" + passwordUpdateDate +
                ", passwordUpdatedMonthRange=" + passwordUpdateDateMonth +
                ", alarmChangePassword=" + alarmChangePassword +
                ", receiveEmail=" + receiveEmail +
                '}';
    }
}
