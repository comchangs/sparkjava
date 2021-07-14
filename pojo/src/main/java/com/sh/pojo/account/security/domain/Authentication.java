package com.sh.pojo.account.security.domain;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.config.PasswordHashing;

import java.util.Objects;
import java.util.UUID;

public class Authentication {

    private Long userId;

    private String username;

    private String token;

    private String sessionId;

    private boolean authenticated;

    private boolean duplicatedLogin;

    private Authentication() {
    }

    public Authentication(User user) {
        this.userId = user.getAccountId();
        this.username = user.getName();
        this.setToken();
        this.setSessionId();
        this.setAuthenticated(true);
        user.newSessionId(this.getSessionId());
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getToken() {
        return token;
    }

    private void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public String getSessionId() {
        return sessionId;
    }

    private void setSessionId() {
        this.sessionId =  PasswordHashing.encode(this.username + this.token);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    private void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }


    public boolean isDuplicatedLogin() {
        return duplicatedLogin;
    }

    private void setDuplicatedLogin(boolean duplicatedLogin) {
        this.duplicatedLogin = duplicatedLogin;
    }

    public void changeToken(){
        this.setToken();
        this.setSessionId();
    }


    public void alarmDuplicatedLogin() {
        this.duplicatedLogin = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authentication that = (Authentication) o;
        return authenticated == that.authenticated && duplicatedLogin == that.duplicatedLogin && Objects.equals(username, that.username) && Objects.equals(token, that.token) && Objects.equals(sessionId, that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token, sessionId, authenticated, duplicatedLogin);
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "username='" + username + '\'' +
                ", authenticated=" + authenticated +
                ", duplicatedLogin=" + duplicatedLogin +
                '}';
    }

}
