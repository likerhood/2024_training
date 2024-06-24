package com.example.myaccount.entity;

public class ListItem {

    public int id;
    public int pic;
    public String date;
    public String name;
    public String desc;
    public double account;

    public ListItem(int id,int pic, String name, String date, String desc, double account) {
        this.id = id;
        this.pic = pic;
        this.date = date;
        this.name = name;
        this.desc = desc;
        this.account = account;
    }
}
