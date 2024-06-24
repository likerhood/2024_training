package com.example.myaccount.entity;

import com.example.myaccount.R;

public class OutComeInfo {

    public int id;
    public double money;
    public String desc;
    public String category;
    public int mYear, mMonth, mDate;
    // 支出种类
    public static String[] mOutComeCatogryName = {
            "餐饮", "购物", "交通", "住宿", "学习", "日常","其他"
    };

    // 图标
    public static int[] mOutcomePic= {
            R.drawable.food,
            R.drawable.shopping,
            R.drawable.transport,
            R.drawable.hotel,
            R.drawable.study,
            R.drawable.daily,
            R.drawable.other,
    };

    public OutComeInfo() {
    }

    public OutComeInfo(double money, String desc,String category,int mYear, int mMonth, int mDate) {
        this.money = money;
        this.desc = desc;
        this.category = category;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return "OutComeInfo{" +
                "id=" + id +
                ", money=" + money +
                ", desc='" + desc + '\'' +
                ", category='" + category + '\'' +
                ", mYear=" + mYear +
                ", mMonth=" + mMonth +
                ", mDate=" + mDate +
                '}';
    }
}
