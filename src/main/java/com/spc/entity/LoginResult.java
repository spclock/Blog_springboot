package com.spc.entity;

public class LoginResult extends Result<User> {
    private boolean isLogin;


    public LoginResult(String status, String msg, boolean isLogin) {
        this(status, msg, isLogin, null);
    }

    public LoginResult(String status, String msg, User user) {
        super(status, msg, user);
    }

    public LoginResult(String status, String msg, boolean isLogin, User user) {
        super(status, msg, user);
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

}
