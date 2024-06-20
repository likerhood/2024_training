package com.example.myapplication.enity;

public class LoginInfo {
    public int id;
    public String phone;
    public String password;
    public boolean remember = false;

    // 无参数构造方法
    public LoginInfo() { }

    // 有参数的构造方法
    public LoginInfo(String phone, String password, boolean remember) {
        this.phone = phone;
        this.password = password;
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", remember=" + remember +
                '}';
    }
}
