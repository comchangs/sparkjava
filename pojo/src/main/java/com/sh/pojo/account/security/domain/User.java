package com.sh.pojo.account.security.domain;

import com.sh.pojo.account.domain.Account;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private Long id;

    private Long accountId;

    private String name;

    private LocalDateTime loginAt;

    private LocalDateTime logoutAt;

    private boolean isLogined;

    private String sessionId;

    public User() {};

    // UserDao save
    public User(Long id, Long accountId, String name, LocalDateTime loginAt, LocalDateTime logoutAt, boolean isLogined) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.loginAt = loginAt;
        this.logoutAt = logoutAt;
        this.isLogined = isLogined;
    }

    public static User createUser(Account account) {
        User user = new User();
        user.setAccountId(account.getId());
        user.setName(account.getNickname());
        user.setLoginAt(LocalDateTime.now());
        user.setLogined(true);
        return user;
    }

    public Long getId() {
        return id;
    }

    public Long getAccountId() {
        return accountId;
    }

    private void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getLoginAt() {
        return loginAt;
    }

    private void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public boolean isLogined() {
        return isLogined;
    }

    private void setLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void newSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void updateByLogout() {
        this.isLogined = false;
        this.logoutAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isLogined == user.isLogined && Objects.equals(id, user.id) && Objects.equals(accountId, user.accountId) && Objects.equals(name, user.name) && Objects.equals(loginAt, user.loginAt) && Objects.equals(logoutAt, user.logoutAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, name,loginAt, logoutAt, isLogined);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", loginAt=" + loginAt +
                ", logoutAt=" + logoutAt +
                ", isLogined=" + isLogined +
                '}';
    }
}
