package com.example.myaccount;

import android.app.Application;
import android.view.View;
import android.widget.TextView;

import com.example.myaccount.DBHelper.BillDBHelper;
import com.example.myaccount.DBHelper.UserDBHelper;
import com.example.myaccount.entity.IncomeInfo;
import com.example.myaccount.entity.OutComeInfo;

import java.util.HashMap;
import java.util.Map;


public class MyApplication extends Application {

    private static MyApplication mApp;

    // 声明一个数据数据库对象
    public UserDBHelper mUserDBHelper;
    public BillDBHelper mBillDBHelper;

    // 购物车中的商品总数量
    public int goodsCount;

    // 全局类别
    public View currentSelectCategory1,currentSelectCategory2;
    public String cate1, cate2;

    // 定义收入和支出类别及其对应的图片资源映射
    public Map<String, Integer> outcomeCategoryMap;
    public Map<String, Integer> incomeCategoryMap;

    // 单例模式
    public static MyApplication getInstance(){
        return mApp;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        // 构建数据库实例
        mUserDBHelper = UserDBHelper.getInstance(this);
        mUserDBHelper.openReadLink();
        mUserDBHelper.openWriteLink();

        mBillDBHelper = BillDBHelper.getInstance(this);
        mBillDBHelper.openReadLink();
        mBillDBHelper.openWriteLink();

        // 初始化商品信息
        initGoodsInfo();

    }

    private void initGoodsInfo() {

        outcomeCategoryMap = new HashMap<>();
        incomeCategoryMap = new HashMap<>();

        for (int i = 0; i < OutComeInfo.mOutComeCatogryName.length; i++) {
            outcomeCategoryMap.put(OutComeInfo.mOutComeCatogryName[i], OutComeInfo.mOutcomePic[i]);
        }

        for (int i = 0; i < IncomeInfo.mInComeCatogryName.length; i++) {
            incomeCategoryMap.put(IncomeInfo.mInComeCatogryName[i], IncomeInfo.mIncomePic[i]);
        }

    }





}
