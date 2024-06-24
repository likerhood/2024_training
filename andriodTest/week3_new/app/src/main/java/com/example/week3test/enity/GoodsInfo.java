package com.example.week3test.enity;

import com.example.week3test.R;

import java.util.ArrayList;

public class GoodsInfo {

    public int id;
    public String name;
    public String description;
    public float price;
    public String picPath;
    public int pic;

    // 手机商品名称数组
    private static String[] mNameArray = {
            "iPhone", "Mate30", "小米10", "OPPO Reno3", "vivo X30", "荣耀30"
    };
    // 商品描述
    private static String[] mDescArray = {
            "Apple iPhone11 256GB 绿色 4G 全网通",
            "华为 HUAWEI Mate30 8GB+256GB 丹霞红 5G 全网通 全面屏手机",
            "小米 MIT10 8GB+128GB 钛晶黑 5G手机 游戏拍照手机",
            "OPPO Reno3 8GB+125GB 蓝色星夜 双模5G 拍照游戏智能手机",
            "vivo X30 8GB+125GB 5G芯片 自拍全面屏手机",
            "荣耀30 8GB+125GB 5G芯片 自拍全面屏手机"
    };

    //商品价格
    private  static int[] mPriceArray = {
            6299, 4999, 3999, 2999, 2998, 2399
    };

    // 手机大图数组
    private static int[] mPicArray= {
            R.drawable.iphone,
            R.drawable.huawei,
            R.drawable.xiaomi,
            R.drawable.oppo,
            R.drawable.vivo,
            R.drawable.rongao,
    };


    // 获取默认商品信息列表
    public static ArrayList<GoodsInfo> getDefaultList(){
        ArrayList<GoodsInfo> list = new ArrayList<>();
        for (int i = 0; i < mNameArray.length; i++){
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescArray[i];
            info.price = mPriceArray[i];
            info.pic = mPicArray[i];
            list.add(info);
        }
        return list;
    }


}
