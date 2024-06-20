package com.example.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myapplication.enity.LoginInfo;

public class LoginDBHelper extends SQLiteOpenHelper {

    // 数据库名字
    private static final String DB_NAME = "login.db";
    // 表名
    private static final String TABLE_NAME = "login_info";
    // 数据库版本
    private static final int DB_VERSION = 1;
    // 数据库管理类实例
    private static  LoginDBHelper mHelper = null;
    // 数据库读写两个工具
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public LoginDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 单例模式
    public static LoginDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper = new LoginDBHelper(context);
        }
        return mHelper;
    }

    // 数据库读链接
    public SQLiteDatabase openReadLink(){
        if (mRDB == null || ! mRDB.isOpen()){
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 数据库写操作
    public SQLiteDatabase openWriteLink(){
        if (mWDB == null || ! mWDB.isOpen()){
            mWDB = mHelper.getWritableDatabase();
        }

        return mWDB;
    }

    // 关闭数据库链接
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


    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " phone VARCHAR NOT NULL," +
                " password VARCHAR NOT NULL," +
                " remember INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    // 更新数据库版本之后执行的操作
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(LoginInfo info){
        ContentValues values = new ContentValues();
        values.put("phone", info.phone);
        values.put("password", info.password);
        values.put("remember", info.remember);

        return mWDB.insert(TABLE_NAME, null, values);

    }

    public long delete(LoginInfo info){
        return mWDB.delete(TABLE_NAME, "phone=?", new String[]{info.phone});
    }

    public void save(LoginInfo info){
        // 如果存在，先删除再添加
        try{
            mWDB.beginTransaction();
            delete(info);
            insert(info);
            mWDB.setTransactionSuccessful();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }

    // 查询用户信息
    public LoginInfo queryTop(){
        LoginInfo info = null;
        String sql = "select * from " + TABLE_NAME + " where remember = 1 ORDER BY _id DESC limit 1";
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()){
            info = new LoginInfo();
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false :true;
        }

        return info;
    }

    // 根据号码查询
    public LoginInfo queryByPhone(String phone){
        LoginInfo info = null;
        String sql = "select * from " + TABLE_NAME;
        // 执行记录查询动作，该语句返回结果集游标
        Cursor cursor = mRDB.query(TABLE_NAME, null, "phone=? and remember=1", new String[]{phone}, null,null,null);
        if (cursor.moveToNext()){
            info = new LoginInfo();
            info.id = cursor.getInt(0);
            info.phone = cursor.getString(1);
            info.password = cursor.getString(2);
            info.remember = (cursor.getInt(3) == 0) ? false :true;
        }

        return info;
    }

}
