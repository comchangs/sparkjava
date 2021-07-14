package com.sh.pojo.account.security.domain;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.config.PasswordHashing;

import java.util.Objects;
import java.util.UUID;

public class Authentication {

    private String username;

    private String token;

    private String sessionId;

    private boolean authenticated;

    private boolean duplicatedLogin;

    public Authentication() {
        this.setAuthenticated(false);
    }

    public Authentication(Account account) {
        this.username = account.getNickname();
        this.setToken();
        this.setSessionId();
        this.setAuthenticated(true);
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
        this.sessionId =  PasswordHashing.encode(this.username+ this.token);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }


    public boolean isDuplicatedLogin() {
        return duplicatedLogin;
    }

    public void setDuplicatedLogin(boolean duplicatedLogin) {
        this.duplicatedLogin = duplicatedLogin;
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
