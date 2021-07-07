package com.sh.pojo.account.domain.form;

import java.time.LocalDate;

public class AccountResponse {

    private Long id;

    private String nickname;

    private String email;

    private LocalDate joinedAt;

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

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
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
                ", joinedAt=" + joinedAt +
                ", alarmChangePassword=" + alarmChangePassword +
                ", receiveEmail=" + receiveEmail +
                '}';
    }
}
