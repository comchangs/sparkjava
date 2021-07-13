package com.sh.pojo.account.domain;

import com.sh.pojo.account.domain.form.request.AccountRequest;
import com.sh.pojo.config.PasswordHashing;

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

    private Account() {

    }

    // DB
    public Account(Long id, String nickname, String email, LocalDate joinedAt, LocalDate passwordUpdateDate, boolean alarmChangePassword, boolean receiveEmail) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.joinedAt = joinedAt;
        this.passwordUpdateDate = passwordUpdateDate;
        this.alarmChangePassword = alarmChangePassword;
        this.receiveEmail = receiveEmail;
    }

    public static Account createNewAccount(String nickname, String email, String password) {
        Account account = new Account();
        account.nickname = nickname;
        account.email = email;
        account.password = PasswordHashing.encode(password);
        account.joinedAt = LocalDate.now();
        account.passwordUpdateDate = LocalDate.now();
        account.receiveEmail = true;
        return account;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getJoinedAt() {
        return joinedAt;
    }

    public LocalDate getPasswordUpdateDate() {
        return passwordUpdateDate;
    }

    public boolean isAlarmChangePassword() {
        return alarmChangePassword;
    }

    public boolean isReceiveEmail() {
        return receiveEmail;
    }

    public void updatePassword(String password) {
        if(!Objects.isNull(password)) this.password = PasswordHashing.encode(password);
        this.passwordUpdateDate = LocalDate.now();
    }

    public void update(AccountRequest account) {
        if(!Objects.isNull(account.getNickname())) this.nickname = account.getNickname();
        if(!Objects.isNull(account.getEmail())) this.email = account.getEmail();
        this.receiveEmail = account.isReceiveEmail();
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
