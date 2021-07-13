package com.sh.pojo.account.domain.form.request;

public class LoginRequest {

    private String loginId;

    private String password;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
