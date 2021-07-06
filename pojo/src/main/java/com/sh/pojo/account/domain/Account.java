package com.sh.pojo.account.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Account {

    private Long id;

    private String nickname;

    private String email;

    private String password;

    private LocalDate joinedAt;

    private LocalDate passwordUpdateDate;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDate joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDate getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public void setPasswordUpdateDate(LocalDate passwordUpdateDate) {
        this.passwordUpdateDate = passwordUpdateDate;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return alarmChangePassword == account.alarmChangePassword && receiveEmail == account.receiveEmail && Objects.equals(id, account.id) && Objects.equals(nickname, account.nickname) && Objects.equals(email, account.email) && Objects.equals(password, account.password) && Objects.equals(joinedAt, account.joinedAt) && Objects.equals(passwordUpdateDate, account.passwordUpdateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, password, joinedAt, passwordUpdateDate, alarmChangePassword, receiveEmail);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", joinedAt=" + joinedAt +
                ", passwordUpdateDate=" + passwordUpdateDate +
                ", alarmChangePassword=" + alarmChangePassword +
                ", receiveEmail=" + receiveEmail +
                '}';
    }



}
