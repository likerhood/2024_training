package com.example.week3test.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.week3test.BillAddActivity;
import com.example.week3test.utils.BillInfo;

import java.util.ArrayList;
import java.util.List;

public class BillDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "bill.db";
    //账单信息表
    private static final String TABLE_BILLS_INFO = "bill_info";
    private static final int DB_VERSION = 1;
    private static BillDBHelper mHelper = null;         // 单例模式
    // 数据库的读写需要基于锁来保证安全性，所以先创建两个实例
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;
    public BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context){
        if (mHelper == null){
            mHelper =new BillDBHelper(context);
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

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建账单信息表
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_BILLS_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " date VARCHAR NOT NULL," +
                " type INTEGER NOT NULL," +
                " amount DOUBLE NOT NULL," +
                " remark VARCHAR NOT NULL);";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 保存一条记录
    public long save(BillInfo bill){
        ContentValues cv = new ContentValues();
        cv.put("date", bill.date);
        cv.put("type", bill.type);
        cv.put("amount", bill.amount);
        cv.put("remark", bill.remark);
        return mWDB.insert(TABLE_BILLS_INFO,null,cv);
    }

    // 数据查询
    @SuppressLint("Range")
    public List<BillInfo> queryByMonth(String yearMonth){
        List<BillInfo> list = new ArrayList<>();
        // 模糊查询，使用通配符
        // select * from bill_info where date like '2035-08%'
        String sql = "select * from " + TABLE_BILLS_INFO+ " where date like '" + yearMonth + "%'";
        Log.d("ning", sql);
        Cursor cursor = mRDB.rawQuery(sql, null);

        // 循环查询
        while(cursor.moveToNext()) {
            BillInfo bill = new BillInfo();
            bill.id = cursor.getInt(cursor.getColumnIndex("_id"));
            bill.date = cursor.getString(cursor.getColumnIndex("date"));
            bill.type = cursor.getInt(cursor.getColumnIndex("type"));
            bill.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
            bill.remark = cursor.getString(cursor.getColumnIndex("remark"));

            list.add(bill);

        }
        return list;
    }

}
