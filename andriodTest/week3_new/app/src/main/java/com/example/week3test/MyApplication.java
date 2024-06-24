package com.example.week3test;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Room;

import com.example.week3test.dao.BookDao;
import com.example.week3test.database.BookDatebase;
import com.example.week3test.database.ShoppingDBHelper;
import com.example.week3test.enity.GoodsInfo;
import com.example.week3test.utils.FileUtil;
import com.example.week3test.utils.SharedUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {

    private static MyApplication mApp;
    // 声明一个公共的信息映射对象
    public HashMap<String, String> infoMap = new HashMap<>();

    // 声明一个数据数据库对象
    private BookDatebase bookDataBase;

    // 购物车中的商品总数量
    public int goodsCount;

    // 单例模式
    public static MyApplication getInstance(){
        return mApp;
    }

    // 全局的选中类别
//    public ImageView currentCategorySelected


    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        // 构建书籍数据库实例
        // 允许主线程中操作数据库（Room默认是不允许的）allowMainThreadQueries()
        // 允许迁移数据库（数据库变更时，Room默认删除原来数据库再创建数据库）.addMigrations()
        bookDataBase = Room.databaseBuilder(this, BookDatebase.class, "book")
                .addMigrations()
                .allowMainThreadQueries()
                .build();
        
        // 初始化商品信息
        initGoodsInfo();

    }

    private void initGoodsInfo() {
        // 获取共享参数保存的是否首次打开参数
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        // 获取当前app的私有下载路径
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        // 模拟网络请求图片下载
        if(isFirst){
            // 下载
            List<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo info: list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), info.pic);
                String path = directory+info.id+".jpg";
                // 外存中存储图片
                FileUtil.saveImage(path, bitmap);
                // 回收位图图像
                bitmap.recycle();
                info.picPath = path;
            }

            // 打开数据库
            ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfos(list);
            dbHelper.closeLink();
            //把是否是首次打开写入共享参数
            SharedUtil.getInstance(this).writeBoolean("first", false);
        }

    }

    // 配置改变的时候调用
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // 获取数据库实例
    public BookDatebase getBookDB(){
        return bookDataBase;
    }



}
