package com.sh.pojo.account.domain.form.request;

public class PasswordForm {

    private Long id;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PasswordForm{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}
