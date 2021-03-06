package com.sh.pojo.account.domain.form.request;


public class AccountRequest {
    private Long id;

    private String nickname;

    private String email;

    private boolean alarmChangePassword;

    private boolean receiveEmail;

    public Long getId() {
        return id;
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
        return "AccountRequest{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", alarmChangePassword=" + alarmChangePassword +
                ", receiveEmail=" + receiveEmail +
                '}';
    }
}
