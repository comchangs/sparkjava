package com.sh.pojo.account.security.domain;

import com.sh.pojo.account.domain.Account;

import java.time.LocalDateTime;
import java.util.Objects;

public class User {
    private Long id;

    private Long accountId;

    private String name;

    private String token;

    private String sessionId;

    private LocalDateTime loginAt;

    private LocalDateTime logoutAt;

    private boolean isLogined;

    public User() {};

    // BoardDetailServlet 에서 seesionId 통해 UserDao 조회 - 허용된 접속자 정보 제공 위해
    public User(Long id, Long accountId, String nickname) {
        this.id = id;
        this.accountId = accountId;
        this.name = nickname;
    }

    // login 시 UserDao 에서
    public User(Long id, Long accountId, String token, String sessionId) {
        this.id = id;
        this.accountId = accountId;
        this.token = token;
        this.sessionId = sessionId;
    }

    // 회원가입시 - Account isAuthentication()에서
    public void createIsAuthenticatedUser(Account account, Authentication authentication) {
        this.accountId = account.getId();
        this.name = account.getNickname();
        this.token = authentication.getToken();
        this.sessionId = authentication.getSessionId();
        this.loginAt = LocalDateTime.now();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(LocalDateTime loginAt) {
        this.loginAt = loginAt;
    }

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean isLogined) {
        this.isLogined = isLogined;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateByLogin(Authentication authentication) {
        this.token = authentication.getToken();
        this.sessionId = authentication.getSessionId();
        this.isLogined = true;
        this.loginAt = LocalDateTime.now();

    }

    public void updateByLogout(Authentication authentication) {
        this.isLogined = false;
        this.logoutAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isLogined == user.isLogined && Objects.equals(id, user.id) && Objects.equals(accountId, user.accountId) && Objects.equals(name, user.name) && Objects.equals(token, user.token) && Objects.equals(sessionId, user.sessionId) && Objects.equals(loginAt, user.loginAt) && Objects.equals(logoutAt, user.logoutAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountId, name, token, sessionId, loginAt, logoutAt, isLogined);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", loginAt=" + loginAt +
                ", logoutAt=" + logoutAt +
                ", isLogined=" + isLogined +
                '}';
    }
}