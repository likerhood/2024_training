package com.example.week3test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.week3test.enity.CartInfo;
import com.example.week3test.enity.GoodsInfo;
import com.example.week3test.enity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoppingDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "shoping.db";
    private static final String TABLE_GOODS_NAME = "goods_info";
    private static final String TABLE_CART_NAME = "cart_info";
    private static final int DB_VERSION = 1;
    private static ShoppingDBHelper mHelper = null;         // 单例模式
    // 数据库的读写需要基于锁来保证安全性，所以先创建两个实例
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // 利用单例模式获取数据库帮助器的唯一实例
    // 以后请补充上同步快处理，加锁，考虑并发问题
    public static ShoppingDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper =new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink(){
        if (mRDB == null || !mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink(){
        if (mWDB == null || !mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库连接
    public void closeLink(){
        if (mRDB != null && !mRDB.isOpen()){
            mRDB.close();
            mRDB = null;
        }

        if (mWDB != null && !mWDB.isOpen()){
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price FLOAT NOT NULL," +
                " pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);

        // 购物车信息表
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertGoodsInfos(List<GoodsInfo> list){
        // 插入多条记录
        try{
            mWDB.beginTransaction();
            for (GoodsInfo info:list) {
                ContentValues values = new ContentValues();
                values.put("name", info.name);
                values.put("description", info.description);
                values.put("price", info.price);
                values.put("pic_path", info.picPath);
                mWDB.insert(TABLE_GOODS_NAME, null, values);
            }
            mWDB.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }

    // 查询所有商品信息
    public List<GoodsInfo> queryAllGoodsInfo(){

        String sql = "select * from " + TABLE_GOODS_NAME;
        List<GoodsInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while (cursor.moveToNext()){
            GoodsInfo info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return list;

    }

    public void insertCartInfo(int goodsId) {
        // 不存在商品就添加
        CartInfo cartInfo = queryCartInfoByGoodsId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);
        if (cartInfo == null){
            values.put("count",1);
            mWDB.insert(TABLE_CART_NAME, null, values);
        }else{
            values.put("_id", cartInfo.id);
            values.put("count", ++cartInfo.count);
            mWDB.update(TABLE_CART_NAME, values, "_id=?", new String[]{String.valueOf(cartInfo.id)});
        }
        //有就增加数量

    }

    private CartInfo queryCartInfoByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART_NAME, null, "goods_id=?", new String[]{String.valueOf(goodsId)}, null, null,null);
        CartInfo info = null;
        if (cursor.moveToNext()){
            info = new CartInfo();
            info.id = cursor.getInt(0);
            info.goodsId = cursor.getInt(1);
            info.count = cursor.getInt(2);
        }
        cursor.close();
        return info;
    }

    public int countCartInfo() {
        int count = 0;
        String sql = "select sum(count) from " + TABLE_CART_NAME;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        return count;
    }

    public List<CartInfo> queryAllCartInfo() {
        List<CartInfo> list = new ArrayList<>();
        Cursor cursor = mRDB.query(TABLE_CART_NAME, null, null, null, null, null, null);
        CartInfo info = null;
        while (cursor.moveToNext()){
            info = new CartInfo();
            info.id = cursor.getInt(0);
            info.goodsId = cursor.getInt(1);
            info.count = cursor.getInt(2);
            list.add(info);
        }
        cursor.close();
        return list;

    }

    public GoodsInfo queryGoodsById(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_GOODS_NAME, null, "_id=?", new String[]{String.valueOf(goodsId)}, null, null,null);
        GoodsInfo info = null;
        if (cursor.moveToNext()){
            info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
        }
        cursor.close();
        return info;
    }

    // 根据商品名字删除购物车信息
    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_NAME, "goods_id=?", new String[]{String.valueOf(goodsId)});
    }

    // 清空购物车信息
    public void deleteAllCartInfo(){
        mWDB.delete(TABLE_CART_NAME, "1=1", null);
    }
}
