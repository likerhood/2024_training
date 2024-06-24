package com.example.myaccount.entity;

public class UserInfo {
    public int id;
    public String username;
    public String phoneNumber;
    public String password;

    public UserInfo() {

    }

    // 构造方法
    public UserInfo(String username, String phoneNumber, String password) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

}
