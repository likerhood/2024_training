package com.example.myaccount.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myaccount.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "user.db";
    private static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 1;
    private static UserDBHelper mHelper = null;         // 单例模式
    // 数据库的读写需要基于锁来保证安全性，所以先创建两个实例
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    // 以后请补充上同步快处理，加锁，考虑并发问题
    public static UserDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper =new UserDBHelper(context);
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
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " username VARCHAR NOT NULL," +
                " phoneNumber VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(UserInfo user){
        // ContentValue是键值对类型
        ContentValues values = new ContentValues();
        values.put("username", user.username);
        values.put("phoneNumber", user.phoneNumber);
        values.put("password", user.password);
        return mWDB.insert(TABLE_NAME, null, values);
    }

    public long deleteByName(String name){
        // 删除所有
        // mWDB.delete(TABLE_NAME, "1=1", null);
        return mWDB.delete(TABLE_NAME, "username=?", new String[]{name});
    }

    public long update(UserInfo user){
        ContentValues values = new ContentValues();
        values.put("username", user.username);
        values.put("phoneNumber", user.phoneNumber);
        values.put("password", user.password);
        return mWDB.update(TABLE_NAME, values, "username=?", new String[]{user.username});
    }


    public List<UserInfo> queryAll(){
        List<UserInfo> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, null, null, null, null,null);
        // 循环去除游标指向的记录
        while(cursor.moveToNext()){
            UserInfo user = new UserInfo();
//            user.id = cursor.getInt(0);
//            user.name = cursor.getString(1);
//            user.age = cursor.getInt(2);
//            user.height = cursor.getLong(3);
//            user.weight = cursor.getFloat(4);
//            user.married = (cursor.getInt(5) == 0) ? false : true;
            user.id = cursor.getInt(0);
            user.username = cursor.getString(1);
            user.phoneNumber = cursor.getString(2);
            user.password = cursor.getString(3);
            list.add(user);
        }

        return list;
    }

    public List<UserInfo> queryByName(String name){
        List<UserInfo> list = new ArrayList<>();
        // 执行记录查询动作，该语句返回结果集游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, "phoneNumber=?", new String[]{name}, null, null,null);
        // 循环去除游标指向的记录
        while(cursor.moveToNext()){
            UserInfo user = new UserInfo();
            user.id = cursor.getInt(0);
            user.username = cursor.getString(1);
            user.phoneNumber = cursor.getString(2);
            user.password = cursor.getString(3);
            list.add(user);
        }

        return list;
    }


}
