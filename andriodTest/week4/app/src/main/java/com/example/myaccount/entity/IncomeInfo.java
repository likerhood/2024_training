package com.example.myaccount.entity;

import com.example.myaccount.R;

import java.util.ArrayList;

public class IncomeInfo {
    public int id;
    public double money;
    public String desc;
    public String category;
    public int mYear, mMonth, mDate;
    // 支出种类
    public static String[] mInComeCatogryName = {
            "职业收入", "投资汇报", "其他收入"
    };

    // 图标
    public static int[] mIncomePic= {
            R.drawable.workincome,
            R.drawable.invest,
            R.drawable.otherincome,
    };

    public IncomeInfo(double money, String desc,String category, int mYear, int mMonth, int mDate) {
        this.money = money;
        this.desc = desc;
        this.category = category;
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.mDate = mDate;
    }

    public IncomeInfo() {
    }



    @Override
    public String toString() {
        return "IncomeInfo{" +
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
